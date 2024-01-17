package com.fageniuscode.epargneplus.api.services.impl;

import com.fageniuscode.epargneplus.api.entities.dto.TransactionDTO;
import com.fageniuscode.epargneplus.api.entities.dto.WalletDTO;
import com.fageniuscode.epargneplus.api.entities.*;
import com.fageniuscode.epargneplus.api.exceptions.EntityNotFoundException;
import com.fageniuscode.epargneplus.api.exceptions.RequestException;
import com.fageniuscode.epargneplus.api.mappings.TransactionMapper;
import com.fageniuscode.epargneplus.api.mappings.WalletMapper;
import com.fageniuscode.epargneplus.api.repositories.WalletRepository;
import com.fageniuscode.epargneplus.api.services.TransactionService;
import com.fageniuscode.epargneplus.api.services.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;
    private final TransactionService transactionService;
    private final MessageSource messageSource;

    private final TransactionMapper transactionMapper;

    @Override
    @Transactional
    public WalletDTO saveWallet(WalletDTO walletDTO) {
        // Mapper le DTO en entité Wallet
        Wallet wallet = walletMapper.walletDTOToWallet(walletDTO);

        if (wallet == null) {
            throw new IllegalArgumentException("Le wallet est null");
        }

        if (wallet.getId() != null && walletRepository.existsById(wallet.getId())) {
            throw new RequestException(getMessage("wallet.duplicate", wallet.getId()), HttpStatus.BAD_REQUEST);
        }
        // Enregistrez le wallet
        wallet = walletRepository.save(wallet);

        // Mapper l'entité en DTO et le renvoyer
        return walletMapper.walletToWalletDTO(wallet);

    }
    @Transactional(readOnly = true)
    @Override
    public Page<WalletDTO> getAllWallet(Pageable pageable) {
        List<WalletDTO> walletDTOList = walletRepository.findAll(pageable)
                .stream()
                .map(walletMapper::walletToWalletDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(walletDTOList, pageable, walletDTOList.size());
    }

    @Override
    @Transactional
    public WalletDTO updateWallet(Long id, WalletDTO updatedWalletDTO) {
        // Vérifier si le wallet existe en fonction de son ID
        Optional<Wallet> optionalWallet = walletRepository.findById(id);

        if (optionalWallet.isPresent()) {
            Wallet existingWallet = optionalWallet.get();
            // Mettez à jour les propriétés d'un wallet existante avec les valeurs de updatedWalletDTO
            existingWallet.setWalletNumber(updatedWalletDTO.getWalletNumber());
            existingWallet.setAmount(updatedWalletDTO.getAmount());
            existingWallet.setCreateDate(updatedWalletDTO.getCreateDate());
            existingWallet.setLastUpdateDate(updatedWalletDTO.getLastUpdateDate());
            existingWallet.setUsername(updatedWalletDTO.getUsername());
            existingWallet.setLockingTime(updatedWalletDTO.getLockingTime());
            existingWallet.setWalletStatus(updatedWalletDTO.getWalletStatus());
            existingWallet.setInterestRate(new InterestRate(updatedWalletDTO.getInterestRate().getId()));
            // Enregistrez le wallet mis à jour
            Wallet updatedWallet = walletRepository.save(existingWallet);
            // Retournez la version mise à jour sous forme de DTO
            return walletMapper.walletToWalletDTO(updatedWallet);
        } else {
            // Le wallet avec l'ID spécifié n'a pas été trouvée, vous pouvez gérer cette situation
            // en lançant une exception ou en renvoyant un objet DTO avec un message d'erreur.
            throw new EntityNotFoundException("Le wallet avec l'ID: " + id +"n'a pas été trouvée.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WalletDTO> findById(Long id) {
        return walletRepository.findById(id)
                .map(walletMapper::walletToWalletDTO)
                .map(Optional::of)
                .orElseThrow(() -> new EntityNotFoundException(getMessage("wallet.notfound", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WalletDTO> findByCreateDateAfter(Date date, Pageable pageable) {
        List<WalletDTO> wallets = walletRepository.findByCreateDateAfter(date, pageable)
                .stream()
                .map(walletMapper::walletToWalletDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(wallets, pageable, wallets.size());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WalletDTO> findAllByDateBetween(Date startDate, Date endDate, Pageable pageable) {
        List<WalletDTO> wallets = walletRepository.findAllByCreateDateBetween(startDate, endDate, pageable)
                .stream()
                .map(walletMapper::walletToWalletDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(wallets, pageable, wallets.size());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WalletDTO> findByCreateDateBefore(Date date, Pageable pageable) {
        List<WalletDTO> wallets = walletRepository.findByCreateDateBefore(date, pageable)
                .stream()
                .map(walletMapper::walletToWalletDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(wallets, pageable, wallets.size());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WalletDTO> findByWalletNumber(String walletNumber) {
        return walletRepository.findByWalletNumber(walletNumber)
                .map(walletMapper::walletToWalletDTO)
                .map(Optional::of)
                .orElseThrow(() -> new EntityNotFoundException(getMessage("wallet.number.notfound", walletNumber)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<WalletDTO> findByUsername(String username) {
        List<Wallet> wallets = walletRepository.findByUsername(username);
        return wallets.stream().map(walletMapper::walletToWalletDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WalletDTO> findByAmountGreaterThan(double amount, Pageable pageable) {

        List<WalletDTO> wallets = walletRepository.findByAmountGreaterThan(amount,pageable)
                .stream()
                .map(walletMapper::walletToWalletDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(wallets, pageable, wallets.size());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            walletRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Impossible de supprimer le portefeuille. Il est utilisé ailleurs.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Double calculateTotalAmount(Long walletId) {
        return walletRepository.calculateTotalAmount(walletId);
    }

    @Override
    @Transactional
    public int addAmountToAmount(Long walletId, double amount) {
        return walletRepository.addAmountToAmount(walletId, amount);
    }

    @Override
    @Transactional
    public int deductAmountFromAmount(Long walletId, double amount) {
        return walletRepository.deductAmountFromAmount(walletId, amount);
    }

    @Override
    @Transactional
    public TransactionDTO addTransactionToWallet(Long walletId, TransactionDTO transactionDTO) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new EntityNotFoundException(getMessage("wallet.notfound", walletId)));

        // Ajoutez la logique de validation et de création de transaction ici

        TransactionDTO createdTransaction = transactionService.saveTransaction(transactionDTO);

        // Mise à jour du solde du portefeuille après l'ajout de la transaction
        wallet.setAmount(wallet.getAmount() + createdTransaction.getAmount());
        walletRepository.save(wallet);

        return createdTransaction;
    }

    @Override
    @Transactional
    public TransactionDTO updateTransactionInWallet(Long walletId, Long transactionId, TransactionDTO updatedTransactionDTO) {
        // Recherchez le portefeuille par son ID
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new EntityNotFoundException(getMessage("wallet.notfound", walletId)));

        // Recherchez la transaction spécifique par son ID
        Optional<TransactionDTO> optionalTransaction = transactionService.findById(transactionId);

        if (optionalTransaction.isPresent()) {
            // La transaction existe, vérifiez si elle appartient au portefeuille spécifié
            TransactionDTO existingTransaction = optionalTransaction.get();

            if (!existingTransaction.getWalletId().equals(walletId)) {
                throw new EntityNotFoundException("Transaction non trouvée dans le portefeuille spécifié.");
            }

            // Mettez à jour les détails de la transaction avec les valeurs de updatedTransactionDTO
            existingTransaction.setAmount(updatedTransactionDTO.getAmount());
            existingTransaction.setDescription(updatedTransactionDTO.getDescription());
            existingTransaction.setCreateDate(updatedTransactionDTO.getCreateDate());
            existingTransaction.setDevise(updatedTransactionDTO.getDevise());
            existingTransaction.setWalletId(wallet.getId()); // Assurez-vous que la transaction appartient toujours au même portefeuille

            // Enregistrez la transaction mise à jour
            TransactionDTO updatedTransaction = transactionService.saveTransaction(existingTransaction);

            // Mettez à jour le solde du portefeuille en conséquence (par exemple, si le montant a été modifié)
            double oldAmount = existingTransaction.getAmount();
            double newAmount = updatedTransaction.getAmount();

            // Mise à jour du solde du portefeuille
            wallet.setAmount(wallet.getAmount() - oldAmount + newAmount);
            walletRepository.save(wallet);

            // Retournez la transaction mise à jour sous forme de DTO
            return updatedTransaction;
        } else {
            // La transaction avec l'ID spécifié n'a pas été trouvée, vous pouvez gérer cette situation
            // en lançant une exception ou en renvoyant un objet DTO avec un message d'erreur.
            throw new EntityNotFoundException("Transaction not found with ID: " + transactionId);
        }
    }


    private String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, Locale.getDefault());
    }
}
