package com.fageniuscode.epargneplus.api.repositories;

import com.fageniuscode.epargneplus.api.entities.StatusTransaction;
import com.fageniuscode.epargneplus.api.entities.Transaction;
import com.fageniuscode.epargneplus.api.entities.TypeTransaction;
import com.fageniuscode.epargneplus.api.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Rechercher une transaction par son ID
    Optional<Transaction> findById(Long id);

    // Rechercher toutes les transactions pour un portefeuille spécifique
    List<Transaction> findByWallet(Wallet wallet);

    // Rechercher toutes les transactions pour un type de transaction spécifique
    List<Transaction> findByTypeTransaction(TypeTransaction typeTransaction);

    // Rechercher toutes les transactions pour un statut de transaction spécifique
    List<Transaction> findByStatusTransaction(StatusTransaction statusTransaction);

    // Rechercher toutes les transactions créées après une date spécifique
    List<Transaction> findByCreateDateAfter(Date date);

    // Rechercher toutes les transactions créées avant une date spécifique
    List<Transaction> findByCreateDateBefore(Date date);

    // Compter le nombre total de transactions
    long count();

    // Supprimer une transaction par son ID
    void deleteById(Long id);

    // Requête JPQL personnalisée pour trouver les transactions par montant
    @Query("SELECT t FROM Transaction t WHERE t.amount >= ?1 AND t.amount <= ?2")
    List<Transaction> findTransactionsByAmountRange(double minAmount, double maxAmount);

    // Méthode pour trouver toutes les transactions entre deux dates données
    List<Transaction> findAllByCreateDateBetween(Date startDate, Date endDate);

    // Méthode pour trouver toutes les transactions en attente d'approbation
    List<Transaction> findAllByStatusTransactionAndTypeTransaction(StatusTransaction statusTransaction, TypeTransaction typeTransaction);

}

