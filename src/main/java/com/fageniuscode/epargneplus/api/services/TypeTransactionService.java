package com.fageniuscode.epargneplus.api.services;

import com.fageniuscode.epargneplus.api.entities.dto.TypeTransactionDTO;

import java.util.List;
import java.util.Optional;

public interface TypeTransactionService {

    Optional<TypeTransactionDTO> findById(Long id);

    Optional<TypeTransactionDTO> findByName(String name);

    List<TypeTransactionDTO> findAllOrderedByName();

    List<TypeTransactionDTO> findByDescriptionContaining(String keyword);

    long count();

    void deleteById(Long id);

    List<TypeTransactionDTO> findTypeTransactionsByDescription(String keyword);

    // Nouvelle méthode pour ajouter un type de transaction
    TypeTransactionDTO addTypeTransaction(TypeTransactionDTO typeTransactionDTO);

    // Mettre à jour le type de transaction en utilisant un DTO
    TypeTransactionDTO updateTypeTransaction(Long id, TypeTransactionDTO updatedTypeTransactionDTO);
}

