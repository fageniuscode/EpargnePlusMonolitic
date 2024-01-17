package com.fageniuscode.epargneplus.api.services.impl;

import com.fageniuscode.epargneplus.api.entities.dto.TypeTransactionDTO;
import com.fageniuscode.epargneplus.api.entities.TypeTransaction;
import com.fageniuscode.epargneplus.api.exceptions.EntityNotFoundException;
import com.fageniuscode.epargneplus.api.exceptions.RequestException;
import com.fageniuscode.epargneplus.api.mappings.TypeTransactionMapper;
import com.fageniuscode.epargneplus.api.repositories.TypeTransactionRepository;
import com.fageniuscode.epargneplus.api.services.TypeTransactionService;
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
public class TypeTransactionServiceImpl implements TypeTransactionService {

    private final TypeTransactionRepository typeTransactionRepository;
    private final TypeTransactionMapper typeTransactionMapper;
    private final MessageSource messageSource;

    @Transactional(readOnly = true)
    @Override
    public Optional<TypeTransactionDTO> findById(Long id) {
        return typeTransactionRepository.findById(id)
                .map(typeTransactionMapper::typeTransactionToTypeTransactionDTO)
                .map(Optional::of)
                .orElseThrow(() -> new EntityNotFoundException(getMessage("typetransaction.notfound", id)));
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<TypeTransactionDTO> findByName(String name) {
        return typeTransactionRepository.findByName(name)
                .map(typeTransactionMapper::typeTransactionToTypeTransactionDTO)
                .map(Optional::of)
                .orElseThrow(() -> new EntityNotFoundException(getMessage("typetransactionname.notfound", name)));
    }

    @Transactional(readOnly = true)
    @Override
    public List<TypeTransactionDTO> findAllOrderedByName() {
        return StreamSupport.stream(typeTransactionRepository.findAllByOrderByNameAsc().spliterator(), false)
                .map(typeTransactionMapper::typeTransactionToTypeTransactionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TypeTransactionDTO> findByDescriptionContaining(String keyword) {
        return StreamSupport.stream(typeTransactionRepository.findByDescriptionContaining(keyword).spliterator(), false)
                .map(typeTransactionMapper::typeTransactionToTypeTransactionDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public long count() {
        return typeTransactionRepository.count();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!typeTransactionRepository.existsById(id)) {
            throw new EntityNotFoundException(getMessage("typetransaction.notfound", id));
        }
        typeTransactionRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<TypeTransactionDTO> findTypeTransactionsByDescription(String keyword) {
        return StreamSupport.stream(typeTransactionRepository.findTypeTransactionsByDescription(keyword).spliterator(), false)
                .map(typeTransactionMapper::typeTransactionToTypeTransactionDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public TypeTransactionDTO addTypeTransaction(TypeTransactionDTO typeTransactionDTO) {
        if (typeTransactionDTO == null) {
            throw new IllegalArgumentException("Le type de transaction est null");
        }

        if (typeTransactionDTO.getId() != null && typeTransactionRepository.existsById(typeTransactionDTO.getId())) {
            throw new RequestException(getMessage("typetransaction.duplicate", typeTransactionDTO.getId()), HttpStatus.BAD_REQUEST);
        }

        TypeTransaction typeTransaction = typeTransactionMapper.typeTransactionDTOToTypeTransaction(typeTransactionDTO);
        typeTransaction = typeTransactionRepository.save(typeTransaction);

        return typeTransactionMapper.typeTransactionToTypeTransactionDTO(typeTransaction);
    }

    @Transactional
    @Override
    public TypeTransactionDTO updateTypeTransaction(Long id, TypeTransactionDTO updatedTypeTransactionDTO) {
        // Vérifier si le type de transaction existe
        TypeTransaction existingTypeTransaction = typeTransactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(getMessage("typetransaction.notfound", id)));

        // Mettre à jour les propriétés du type de transaction
        existingTypeTransaction.setName(updatedTypeTransactionDTO.getName());
        existingTypeTransaction.setDescription(updatedTypeTransactionDTO.getDescription());

        // Enregistrer les modifications
        existingTypeTransaction = typeTransactionRepository.save(existingTypeTransaction);

        // Mapper l'entité mise à jour en DTO et le renvoyer
        return typeTransactionMapper.typeTransactionToTypeTransactionDTO(existingTypeTransaction);
    }

    private String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, Locale.getDefault());
    }
}
