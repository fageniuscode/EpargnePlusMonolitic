package com.fageniuscode.epargneplus.api.services;
import com.fageniuscode.epargneplus.api.dto.*;
import com.fageniuscode.epargneplus.api.entities.dto.StatusTransactionDTO;
import com.fageniuscode.epargneplus.api.entities.dto.TransactionDTO;
import com.fageniuscode.epargneplus.api.entities.dto.TypeTransactionDTO;
import com.fageniuscode.epargneplus.api.entities.dto.WalletDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.Optional;

public interface TransactionService {

    Optional<TransactionDTO> findById(Long id);

    Page<TransactionDTO> findByWallet(WalletDTO walletDTO, Pageable pageable);

    Page<TransactionDTO> getAllTransactions(Pageable pageable);
    Page<TransactionDTO> findByTypeTransaction(TypeTransactionDTO typeTransactionDTO, Pageable pageable);

    Page<TransactionDTO> findByStatusTransaction(StatusTransactionDTO statusTransactionDTO, Pageable pageable);

    Page<TransactionDTO> findByCreateDateAfter(Date date, Pageable pageable);

    Page<TransactionDTO> findByCreateDateBefore(Date date, Pageable pageable);

    long count();

    void deleteById(Long id);

    Page<TransactionDTO> findTransactionsByAmountRange(double minAmount, double maxAmount, Pageable pageable);

    Page<TransactionDTO> findAllByDateBetween(Date startDate, Date endDate, Pageable pageable);

    Page<TransactionDTO> findAllByStatusTransactionAndTypeTransaction(StatusTransactionDTO status, TypeTransactionDTO typeTransaction, Pageable pageable);

    TransactionDTO saveTransaction(TransactionDTO transaction);

    TransactionDTO updateTransaction(Long id, TransactionDTO updatedTransactionDTO);
}
