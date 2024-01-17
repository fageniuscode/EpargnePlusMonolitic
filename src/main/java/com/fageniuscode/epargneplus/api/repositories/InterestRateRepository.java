package com.fageniuscode.epargneplus.api.repositories;

import com.fageniuscode.epargneplus.api.entities.InterestRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterestRateRepository extends JpaRepository<InterestRate, Long> {

    // Rechercher un taux d'intérêt par son ID
    Optional<InterestRate> findById(Long id);

    // Rechercher un taux d'intérêt par son taux
    Optional<InterestRate> findByRate(double rate);

    // Rechercher tous les taux d'intérêt triés par taux
    List<InterestRate> findAllByOrderByRateAsc();

    // Rechercher les taux d'intérêt supérieurs à un certain taux donné
    List<InterestRate> findByRateGreaterThan(double rate);

    // Rechercher les taux d'intérêt inférieurs à un certain taux donné
    List<InterestRate> findByRateLessThan(double rate);

    // Rechercher les taux d'intérêt compris entre deux valeurs données
    List<InterestRate> findByRateBetween(double minRate, double maxRate);

    // Rechercher les taux d'intérêt par description
    List<InterestRate> findByDescriptionContaining(String keyword);

    // Compter le nombre total de taux d'intérêt
    long count();

    // Supprimer un taux d'intérêt par son ID
    void deleteById(Long id);

    // Requête JPQL personnalisée pour trouver les taux d'intérêt par description
    @Query("SELECT ir FROM InterestRate ir WHERE ir.description LIKE %?1%")
    List<InterestRate> findInterestRatesByDescription(String keyword);

}
