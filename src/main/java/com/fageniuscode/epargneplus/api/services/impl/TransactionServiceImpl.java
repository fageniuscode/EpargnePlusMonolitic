package com.fageniuscode.epargneplus.api.services.impl;

import com.fageniuscode.epargneplus.api.dto.*;
import com.fageniuscode.epargneplus.api.entities.*;
import com.fageniuscode.epargneplus.api.entities.dto.StatusTransactionDTO;
import com.fageniuscode.epargneplus.api.entities.dto.TransactionDTO;
import com.fageniuscode.epargneplus.api.entities.dto.TypeTransactionDTO;
import com.fageniuscode.epargneplus.api.entities.dto.WalletDTO;
import com.fageniuscode.epargneplus.api.exceptions.EntityNotFoundException;
import com.fageniuscode.epargneplus.api.exceptions.RequestException;
import com.fageniuscode.epargneplus.api.mappings.TransactionMapper;
import com.fageniuscode.epargneplus.api.repositories.StatusTransactionRepository;
import com.fageniuscode.epargneplus.api.repositories.TransactionRepository;
import com.fageniuscode.epargneplus.api.repositories.TypeTransactionRepository;
import com.fageniuscode.epargneplus.api.repositories.WalletRepository;
import com.fageniuscode.epargneplus.api.services.TransactionService;
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
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final MessageSource messageSource;
    private final WalletRepository walletRepository;
    private final TypeTransactionRepository typeTransactionRepository;
    private final StatusTransactionRepository statusTransactionRepository;

    /*@Override
    @Transactional(readOnly = true)
    public Optional<TransactionDTO> findById(Long id) {
        return transactionRepository.findById(id)
                .map(transactionMapper::transactionToTransactionDTO)
                .map(Optional::of)
                .orElseThrow(() -> new EntityNotFoundException(getMessage("transaction.notfound", id)));
    }*/

    @Override
    @Transactional(readOnly = true)
    public Optional<TransactionDTO> findById(Long id) {
        return Optional.ofNullable(transactionRepository.findById(id)
                .map(transactionMapper::transactionToTransactionDTO)
                .orElseThrow(() -> new EntityNotFoundException(getMessage("transaction.notfound", id))));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionDTO> findByWallet(WalletDTO walletDTO, Pageable pageable) {
        List<TransactionDTO> transactions = transactionRepository.findByWallet(new Wallet(walletDTO.getId()))
                .stream()
                .map(transactionMapper::transactionToTransactionDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(transactions, pageable, transactions.size());
    }

    @Transactional(readOnly = true)
    @Override
    public Page<TransactionDTO> getAllTransactions(Pageable pageable) {
        List<TransactionDTO> transactionsList = transactionRepository.findAll(pageable)
                .stream()
                .map(transactionMapper::transactionToTransactionDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(transactionsList, pageable, transactionsList.size());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionDTO> findByTypeTransaction(TypeTransactionDTO typeTransactionDTO, Pageable pageable) {
        List<TransactionDTO> transactions = transactionRepository.findByTypeTransaction(new TypeTransaction(typeTransactionDTO.getId()))
                .stream()
                .map(transactionMapper::transactionToTransactionDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(transactions, pageable, transactions.size());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionDTO> findByStatusTransaction(StatusTransactionDTO statusTransactionDTO, Pageable pageable) {
        List<TransactionDTO> transactions = transactionRepository.findByStatusTransaction(new StatusTransaction(statusTransactionDTO.getId()))
                .stream()
                .map(transactionMapper::transactionToTransactionDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(transactions, pageable, transactions.size());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionDTO> findByCreateDateAfter(Date date, Pageable pageable) {
        List<TransactionDTO> transactions = transactionRepository.findByCreateDateAfter(date)
                .stream()
                .map(transactionMapper::transactionToTransactionDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(transactions, pageable, transactions.size());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionDTO> findByCreateDateBefore(Date date, Pageable pageable) {
        List<TransactionDTO> transactions = transactionRepository.findByCreateDateBefore(date)
                .stream()
                .map(transactionMapper::transactionToTransactionDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(transactions, pageable, transactions.size());
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return transactionRepository.count();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            transactionRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Impossible de supprimer la transaction. Elle est utilisée ailleurs.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionDTO> findTransactionsByAmountRange(double minAmount, double maxAmount, Pageable pageable) {
        List<TransactionDTO> transactions = transactionRepository.findTransactionsByAmountRange(minAmount, maxAmount)
                .stream()
                .map(transactionMapper::transactionToTransactionDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(transactions, pageable, transactions.size());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionDTO> findAllByDateBetween(Date startDate, Date endDate, Pageable pageable) {
        List<TransactionDTO> transactions = transactionRepository.findAllByCreateDateBetween(startDate, endDate)
                .stream()
                .map(transactionMapper::transactionToTransactionDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(transactions, pageable, transactions.size());
    }

    @Transactional(readOnly = true)
    @Override
    public Page<TransactionDTO> findAllByStatusTransactionAndTypeTransaction(StatusTransactionDTO statusTransaction, TypeTransactionDTO typeTransaction, Pageable pageable) {
        List<TransactionDTO> transactions = transactionRepository.findAllByStatusTransactionAndTypeTransaction(new StatusTransaction(statusTransaction.getId()), new TypeTransaction(typeTransaction.getId()))
                .stream()
                .map(transactionMapper::transactionToTransactionDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(transactions, pageable, transactions.size());
    }

    @Override
    @Transactional
    public TransactionDTO saveTransaction(TransactionDTO transactionDTO) {
        // Mapper le DTO en entité Transaction
        Transaction transaction = transactionMapper.transactionDTOToTransaction(transactionDTO);

        // Chargez les objets associés à partir de la base de données
        Wallet wallet = walletRepository.findById(transactionDTO.getWalletId())
                .orElseThrow(() -> new EntityNotFoundException("Wallet not found with ID: " + transactionDTO.getWalletId()));
        TypeTransaction typeTransaction = typeTransactionRepository.findById(transactionDTO.getTypeTransactionId())
                .orElseThrow(() -> new EntityNotFoundException("TypeTransaction not found with ID: " + transactionDTO.getTypeTransactionId()));
        StatusTransaction statusTransaction = statusTransactionRepository.findById(transactionDTO.getStatusTransactionId())
                .orElseThrow(() -> new EntityNotFoundException("StatusTransaction not found with ID: " + transactionDTO.getStatusTransactionId()));
        transaction.setWallet(wallet);
        transaction.setTypeTransaction(typeTransaction);
        transaction.setStatusTransaction(statusTransaction);

        if (transaction == null) {
            throw new IllegalArgumentException("La transaction est null");
        }

        if (transaction.getId() != null && transactionRepository.existsById(transaction.getId())) {
            throw new RequestException(getMessage("transaction.duplicate", transaction.getId()), HttpStatus.BAD_REQUEST);
        }
        // Enregistrez la transaction
        transaction = transactionRepository.save(transaction);

        // Mapper l'entité en DTO et le renvoyer
        return transactionMapper.transactionToTransactionDTO(transaction);

    }

    @Override
    @Transactional
    public TransactionDTO updateTransaction(Long id, TransactionDTO updatedTransactionDTO) {
        // Vérifier si la transaction existe en fonction de son ID
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);

        if (optionalTransaction.isPresent()) {
            // La transaction existe, mettez à jour ses propriétés avec les valeurs de updatedTransactionDTO
            Transaction existingTransaction = optionalTransaction.get();
            // Mettez à jour les propriétés de la transaction existante avec les valeurs de updatedTransactionDTO
            existingTransaction.setAmount(updatedTransactionDTO.getAmount());
            existingTransaction.setDescription(updatedTransactionDTO.getDescription());
            existingTransaction.setCreateDate(updatedTransactionDTO.getCreateDate());
            existingTransaction.setDevise(updatedTransactionDTO.getDevise());
            // Chargez les objets associés à partir de la base de données
            Wallet wallet = walletRepository.findById(updatedTransactionDTO.getWalletId())
                    .orElseThrow(() -> new EntityNotFoundException("Wallet not found with ID: " + updatedTransactionDTO.getWalletId()));
            TypeTransaction typeTransaction = typeTransactionRepository.findById(updatedTransactionDTO.getTypeTransactionId())
                    .orElseThrow(() -> new EntityNotFoundException("TypeTransaction not found with ID: " + updatedTransactionDTO.getTypeTransactionId()));
            StatusTransaction statusTransaction = statusTransactionRepository.findById(updatedTransactionDTO.getStatusTransactionId())
                    .orElseThrow(() -> new EntityNotFoundException("StatusTransaction not found with ID: " + updatedTransactionDTO.getStatusTransactionId()));
            existingTransaction.setWallet(wallet);
            existingTransaction.setTypeTransaction(typeTransaction);
            existingTransaction.setStatusTransaction(statusTransaction);
            System.out.println(existingTransaction);
            // Enregistrez la transaction mise à jour
            Transaction updatedTransaction = transactionRepository.save(existingTransaction);
            // Retournez la version mise à jour sous forme de DTO
            return transactionMapper.transactionToTransactionDTO(updatedTransaction);
        } else {
            // La transaction avec l'ID spécifié n'a pas été trouvée, vous pouvez gérer cette situation
            // en lançant une exception ou en renvoyant un objet DTO avec un message d'erreur.
            throw new EntityNotFoundException("Transaction not found with ID: " + id);
        }
    }

    private String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, Locale.getDefault());
    }
}
