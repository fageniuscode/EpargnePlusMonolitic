package com.fageniuscode.epargneplus.api.services;

import com.fageniuscode.epargneplus.api.entities.dto.InterestRateDTO;

import java.util.List;
import java.util.Optional;

public interface InterestRateService {

    // Enregistrer un taux d'intérêt en utilisant un DTO
    InterestRateDTO saveInterestRate(InterestRateDTO interestRateDTO);

    // Rechercher un taux d'intérêt par son ID et retourner un DTO
    Optional<InterestRateDTO> findInterestRateById(Long id);

    // Lister tous les taux d'intérêt et retourner une liste de DTO
    List<InterestRateDTO> getAllInterestRates();

    // Mettre à jour un taux d'intérêt en utilisant un DTO
    InterestRateDTO updateInterestRate(Long id, InterestRateDTO updatedInterestRateDTO);

    // Supprimer un taux d'intérêt par son ID
    void deleteInterestRateById(Long id);

    // Rechercher les taux d'intérêt supérieurs à un certain taux donné et retourner une liste de DTO
    List<InterestRateDTO> findInterestRatesByRateGreaterThan(double rate);

    // Rechercher les taux d'intérêt inférieurs à un certain taux donné et retourner une liste de DTO
    List<InterestRateDTO> findInterestRatesByRateLessThan(double rate);

    // Rechercher les taux d'intérêt compris entre deux valeurs données et retourner une liste de DTO
    List<InterestRateDTO> findInterestRatesByRateBetween(double minRate, double maxRate);

    // Rechercher les taux d'intérêt par description et retourner une liste de DTO
    List<InterestRateDTO> findInterestRatesByDescriptionContaining(String keyword);

    // Compter le nombre total de taux d'intérêt
    long countInterestRates();

    // Requête JPQL personnalisée pour trouver les taux d'intérêt par description et retourner une liste de DTO
    List<InterestRateDTO> findInterestRatesByCustomJPQLQuery(String description);


}
