package com.fageniuscode.epargneplus.api.services.impl;

import com.fageniuscode.epargneplus.api.entities.dto.InterestRateDTO;
import com.fageniuscode.epargneplus.api.entities.InterestRate;
import com.fageniuscode.epargneplus.api.exceptions.EntityNotFoundException;
import com.fageniuscode.epargneplus.api.exceptions.RequestException;
import com.fageniuscode.epargneplus.api.mappings.InterestRateMapper;
import com.fageniuscode.epargneplus.api.repositories.InterestRateRepository;
import com.fageniuscode.epargneplus.api.services.InterestRateService;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class InterestRateServiceImpl implements InterestRateService {

    private final InterestRateRepository interestRateRepository;
    private final InterestRateMapper interestRateMapper;
    private final MessageSource messageSource;

    @Transactional
    @Override
    public InterestRateDTO saveInterestRate(InterestRateDTO interestRateDTO) {
        // Mapper le DTO en entité InterestRate
        InterestRate interestRate = interestRateMapper.interestRateDTOToInterestRate(interestRateDTO);

        if (interestRate == null) {
            throw new IllegalArgumentException("Le taux d'intérêt est null");
        }

        if (interestRate.getId() != null && interestRateRepository.existsById(interestRate.getId())) {
            throw new RequestException(getMessage("interestrate.duplicate", interestRate.getId()), HttpStatus.BAD_REQUEST);
        }
        // Enregistrer le taux d'intérêt
        interestRate = interestRateRepository.save(interestRate);

        // Mapper l'entité en DTO et le renvoyer
        return interestRateMapper.interestRateToInterestRateDTO(interestRate);
    }


    @Transactional(readOnly = true)
    @Override
    public Optional<InterestRateDTO> findInterestRateById(Long id) {
        return interestRateRepository.findById(id)
                .map(interestRateMapper::interestRateToInterestRateDTO)
                .map(Optional::of)
                .orElseThrow(() -> new EntityNotFoundException(getMessage("interestrate.notfound", id)));
    }


    @Transactional(readOnly = true)
    @Override
    public List<InterestRateDTO> getAllInterestRates() {
        return StreamSupport.stream(interestRateRepository.findAll().spliterator(), false)
                .map(interestRateMapper::interestRateToInterestRateDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public InterestRateDTO updateInterestRate(Long id, InterestRateDTO updatedInterestRateDTO) {
        // Vérifier si le taux d'intérêt existe
        InterestRate existingInterestRate = interestRateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(getMessage("interestrate.notfound", id)));

        // Mettre à jour les propriétés du taux d'intérêt
        existingInterestRate.setRate(updatedInterestRateDTO.getRate());
        existingInterestRate.setDescription(updatedInterestRateDTO.getDescription());

        // Enregistrer les modifications
        existingInterestRate = interestRateRepository.save(existingInterestRate);

        // Mapper l'entité mise à jour en DTO et le renvoyer
        return interestRateMapper.interestRateToInterestRateDTO(existingInterestRate);
    }


    @Override
    public void deleteInterestRateById(Long id) {
        // Vérifier si le taux d'intérêt existe
        InterestRate existingInterestRate = interestRateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(getMessage("interestrate.notfound", id)));

        // Supprimer le taux d'intérêt
        interestRateRepository.delete(existingInterestRate);
    }

    @Override
    public List<InterestRateDTO> findInterestRatesByRateGreaterThan(double rate) {
        return StreamSupport.stream(interestRateRepository.findByRateGreaterThan(rate).spliterator(), false)
                .map(interestRateMapper::interestRateToInterestRateDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<InterestRateDTO> findInterestRatesByRateLessThan(double rate) {
        return StreamSupport.stream(interestRateRepository.findByRateLessThan(rate).spliterator(), false)
                .map(interestRateMapper::interestRateToInterestRateDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<InterestRateDTO> findInterestRatesByRateBetween(double minRate, double maxRate) {
        return StreamSupport.stream(interestRateRepository.findByRateBetween(minRate, maxRate).spliterator(), false)
                .map(interestRateMapper::interestRateToInterestRateDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<InterestRateDTO> findInterestRatesByDescriptionContaining(String keyword) {
        return StreamSupport.stream(interestRateRepository.findByDescriptionContaining(keyword).spliterator(), false)
                .map(interestRateMapper::interestRateToInterestRateDTO)
                .collect(Collectors.toList());
    }

    @Override
    public long countInterestRates() {
        return interestRateRepository.count();
    }

    @Override
    public List<InterestRateDTO> findInterestRatesByCustomJPQLQuery(String description) {
        return StreamSupport.stream(interestRateRepository.findInterestRatesByDescription(description).spliterator(), false)
                .map(interestRateMapper::interestRateToInterestRateDTO)
                .collect(Collectors.toList());
    }

    private String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, Locale.getDefault());
    }
}


