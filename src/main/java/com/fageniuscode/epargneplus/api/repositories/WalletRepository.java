package com.fageniuscode.epargneplus.api.repositories;
import com.fageniuscode.epargneplus.api.entities.Wallet;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    // Rechercher un portefeuille par son ID
    Optional<Wallet> findById(Long id);

    // Rechercher tout les Wallets créées avant une date spécifique
    List<Wallet> findByCreateDateAfter(Date date, Pageable pageable);

    // Rechercher tout les Wallets créées avant une date spécifique
    List<Wallet> findByCreateDateBefore(Date date, Pageable pageable);

    List<Wallet> findAllByCreateDateBetween(Date startDate, Date endDate, Pageable pageable);

    // Rechercher un portefeuille par son numéro
    Optional<Wallet> findByWalletNumber(String walletNumber);

    // Trouver tous les portefeuilles appartenant à un utilisateur spécifique
    List<Wallet> findByUsername(String username);

    // Trouver tous les portefeuilles avec un solde supérieur à un certain montant
    List<Wallet> findByAmountGreaterThan(double amount, Pageable pageable);

    // Supprimer un portefeuille par son ID
    void deleteById(Long id);

    // Calculer le solde total d'un portefeuille
    @Query("SELECT SUM(w.amount) FROM Wallet w WHERE w.id = ?1")
    Double calculateTotalAmount(Long walletId);

    // Ajouter un montant au solde d'un portefeuille
    @Modifying
    @Query("UPDATE Wallet w SET w.amount = w.amount + ?2 WHERE w.id = ?1")
    int addAmountToAmount(Long walletId, double amount);

    // Déduire un montant du solde d'un portefeuille
    @Modifying
    @Query("UPDATE Wallet w SET w.amount = w.amount - ?2 WHERE w.id = ?1")
    int deductAmountFromAmount(Long walletId, double amount);

}

