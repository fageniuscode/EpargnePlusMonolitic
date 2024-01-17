package com.fageniuscode.epargneplus.api.services;

import com.fageniuscode.epargneplus.api.entities.dto.TransactionDTO;
import com.fageniuscode.epargneplus.api.entities.dto.WalletDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface WalletService {

    WalletDTO saveWallet(WalletDTO walletDTO);

    Page<WalletDTO> getAllWallet(Pageable pageable);
    WalletDTO updateWallet(Long id, WalletDTO updatedWalletDTO);
    Optional<WalletDTO> findById(Long id);
    Page<WalletDTO> findByCreateDateAfter(Date date, Pageable pageable);
    Page<WalletDTO> findAllByDateBetween(Date startDate, Date endDate, Pageable pageable);

    Page<WalletDTO> findByCreateDateBefore(Date date, Pageable pageable);

    Optional<WalletDTO> findByWalletNumber(String walletNumber);

    List<WalletDTO> findByUsername(String username);

    Page<WalletDTO> findByAmountGreaterThan(double amount, Pageable pageable);

    void deleteById(Long id);

    Double calculateTotalAmount(Long walletId);

    int addAmountToAmount(Long walletId, double amount);

    int deductAmountFromAmount(Long walletId, double amount);

    TransactionDTO addTransactionToWallet(Long walletId, TransactionDTO transactionDTO);

    TransactionDTO updateTransactionInWallet(Long walletId, Long transactionId, TransactionDTO updatedTransactionDTO);

}

