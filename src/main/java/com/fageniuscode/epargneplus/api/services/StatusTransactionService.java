package com.fageniuscode.epargneplus.api.services;
import com.fageniuscode.epargneplus.api.entities.dto.StatusTransactionDTO;

import java.util.List;
import java.util.Optional;

public interface StatusTransactionService {

    StatusTransactionDTO saveStatusTransaction(StatusTransactionDTO statusTransactionDTO);

    Optional<StatusTransactionDTO> findById(Long id);

    Optional<StatusTransactionDTO> findByName(String name);

    List<StatusTransactionDTO> findAllOrderedByName();

    List<StatusTransactionDTO> findByCommentContaining(String keyword);

    long count();

    void deleteById(Long id);

    List<StatusTransactionDTO> findStatusTransactionsByComment(String keyword);

    // Mettre à jour le status de la transaction en utilisant un DTO
    StatusTransactionDTO updateStatusTransaction(Long id, StatusTransactionDTO updatedStatusTransactionDTO);

}

