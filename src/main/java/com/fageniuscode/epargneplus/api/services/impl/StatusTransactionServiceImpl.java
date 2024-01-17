package com.fageniuscode.epargneplus.api.services.impl;

import com.fageniuscode.epargneplus.api.entities.dto.StatusTransactionDTO;
import com.fageniuscode.epargneplus.api.entities.StatusTransaction;
import com.fageniuscode.epargneplus.api.exceptions.EntityNotFoundException;
import com.fageniuscode.epargneplus.api.exceptions.RequestException;
import com.fageniuscode.epargneplus.api.mappings.StatusTransactionMapper;
import com.fageniuscode.epargneplus.api.repositories.StatusTransactionRepository;
import com.fageniuscode.epargneplus.api.services.StatusTransactionService;
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
public class StatusTransactionServiceImpl implements StatusTransactionService {

    private final StatusTransactionRepository statusTransactionRepository;
    private final StatusTransactionMapper statusTransactionMapper;
    private final MessageSource messageSource;

    @Override
    @Transactional
    public StatusTransactionDTO saveStatusTransaction(StatusTransactionDTO statusTransactionDTO) {
        StatusTransaction statusTransaction = statusTransactionMapper.statusTransactionDTOToStatusTransaction(statusTransactionDTO);

        if (statusTransaction == null) {
            throw new IllegalArgumentException("Le statut de la transaction est null");
        }

        if (statusTransaction.getId() != null && statusTransactionRepository.existsById(statusTransaction.getId())) {
            throw new RequestException(getMessage("statustransaction.duplicate", statusTransaction.getId()), HttpStatus.BAD_REQUEST);
        }
        // Enregistrer le taux d'intérêt
        statusTransaction = statusTransactionRepository.save(statusTransaction);

        // Mapper l'entité en DTO et le renvoyer
        return statusTransactionMapper.statusTransactionToStatusTransactionDTO(statusTransaction);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<StatusTransactionDTO> findById(Long id) {
        return statusTransactionRepository.findById(id)
                .map(statusTransactionMapper::statusTransactionToStatusTransactionDTO)
                .map(Optional::of)
                .orElseThrow(() -> new EntityNotFoundException(getMessage("statustransaction.notfound", id)));
    }



    @Transactional(readOnly = true)
    @Override
    public Optional<StatusTransactionDTO> findByName(String name) {
        return statusTransactionRepository.findByName(name)
                .map(statusTransactionMapper::statusTransactionToStatusTransactionDTO)
                .map(Optional::of)
                .orElseThrow(() -> new EntityNotFoundException(getMessage("statustransactionname.notfound", name)));
    }

    @Transactional(readOnly = true)
    @Override
    public List<StatusTransactionDTO> findAllOrderedByName() {
        return StreamSupport.stream(statusTransactionRepository.findAllByOrderByNameAsc().spliterator(), false)
                .map(statusTransactionMapper::statusTransactionToStatusTransactionDTO)
                .collect(Collectors.toList());
    }


    @Override
    public List<StatusTransactionDTO> findByCommentContaining(String keyword) {
        return StreamSupport.stream(statusTransactionRepository.findByCommentContaining(keyword).spliterator(), false)
                .map(statusTransactionMapper::statusTransactionToStatusTransactionDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public long count() {
        return statusTransactionRepository.count();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!statusTransactionRepository.existsById(id)) {
            throw new EntityNotFoundException(getMessage("status.transaction.notfound", id));
        }
        statusTransactionRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<StatusTransactionDTO> findStatusTransactionsByComment(String keyword) {
        return StreamSupport.stream(statusTransactionRepository.findStatusTransactionsByComment(keyword).spliterator(), false)
                .map(statusTransactionMapper::statusTransactionToStatusTransactionDTO)
                .collect(Collectors.toList());
    }
    @Transactional
    @Override
    public StatusTransactionDTO updateStatusTransaction(Long id, StatusTransactionDTO updatedStatusTransactionDTO) {
        // Vérifier si le statut de la transaction existe
        StatusTransaction existingStatusTransaction = statusTransactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(getMessage("statustransactionname.notfound", id)));

        // Mettre à jour les propriétés du statut de la transaction
        existingStatusTransaction.setName(updatedStatusTransactionDTO.getName());
        existingStatusTransaction.setComment(updatedStatusTransactionDTO.getComment());
        existingStatusTransaction.setLastUpdateDate(updatedStatusTransactionDTO.getLastUpdateDate());

        // Enregistrer les modifications
        existingStatusTransaction = statusTransactionRepository.save(existingStatusTransaction);

        // Mapper l'entité mise à jour en DTO et le renvoyer
        return statusTransactionMapper.statusTransactionToStatusTransactionDTO(existingStatusTransaction);
    }

    private String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, Locale.getDefault());
    }
}

