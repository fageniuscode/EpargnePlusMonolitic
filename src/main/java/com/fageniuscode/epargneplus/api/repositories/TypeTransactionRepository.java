package com.fageniuscode.epargneplus.api.repositories;

import com.fageniuscode.epargneplus.api.entities.TypeTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TypeTransactionRepository extends JpaRepository<TypeTransaction, Long> {

    // Rechercher un type de transaction par son ID
    Optional<TypeTransaction> findById(Long id);

    // Rechercher un type de transaction par son nom
    Optional<TypeTransaction> findByName(String name);

    // Rechercher tous les types de transaction triés par nom
    List<TypeTransaction> findAllByOrderByNameAsc();

    // Rechercher les types de transaction par commentaire
    List<TypeTransaction> findByDescriptionContaining(String keyword);

    // Compter le nombre total de types de transaction
    long count();

    // Supprimer un type de transaction par son ID
    void deleteById(Long id);

    // Requête JPQL personnalisée pour trouver les types de transaction par commentaire
    @Query("SELECT tt FROM TypeTransaction tt WHERE tt.description LIKE %?1%")
    List<TypeTransaction> findTypeTransactionsByDescription(String keyword);


}

