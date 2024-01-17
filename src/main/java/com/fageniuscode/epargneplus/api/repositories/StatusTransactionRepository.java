package com.fageniuscode.epargneplus.api.repositories;

import com.fageniuscode.epargneplus.api.entities.StatusTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatusTransactionRepository extends JpaRepository<StatusTransaction, Long> {

    // Rechercher un statut de transaction par son ID
    Optional<StatusTransaction> findById(Long id);

    // Rechercher un statut de transaction par son nom
    Optional<StatusTransaction> findByName(String name);

    // Rechercher tous les statuts de transaction triés par nom
    List<StatusTransaction> findAllByOrderByNameAsc();

    // Rechercher les statuts de transaction par commentaire
    List<StatusTransaction> findByCommentContaining(String keyword);

    // Compter le nombre total de statuts de transaction
    long count();

    // Supprimer un statut de transaction par son ID
    void deleteById(Long id);

    // Requête JPQL personnalisée pour trouver les statuts de transaction par commentaire
    @Query("SELECT st FROM StatusTransaction st WHERE st.comment LIKE %?1%")
    List<StatusTransaction> findStatusTransactionsByComment(String keyword);

}
