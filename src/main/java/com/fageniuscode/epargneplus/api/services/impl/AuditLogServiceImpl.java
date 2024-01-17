package com.fageniuscode.epargneplus.api.services.impl;

import com.fageniuscode.epargneplus.api.entities.dto.AuditLogDTO;
import com.fageniuscode.epargneplus.api.entities.AuditLog;
import com.fageniuscode.epargneplus.api.exceptions.APIExceptionHandler;
import com.fageniuscode.epargneplus.api.exceptions.EntityNotFoundException;
import com.fageniuscode.epargneplus.api.exceptions.RequestException;
import com.fageniuscode.epargneplus.api.mappings.AuditLogMapper;
import com.fageniuscode.epargneplus.api.repositories.AuditLogRepository;
import com.fageniuscode.epargneplus.api.services.AuditLogService;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuditLogServiceImpl implements AuditLogService{
    private final AuditLogRepository auditLogRepository;
    private final AuditLogMapper auditLogMapper;
    private final MessageSource messageSource;

    private final APIExceptionHandler apiExceptionHandler;

    @Transactional
    @Override
    public AuditLogDTO saveAuditLog(AuditLogDTO auditLogDTO) {
        // Valider les données avant de les enregistrer
        validateAuditLogDTO(auditLogDTO);

        AuditLog auditLog = auditLogMapper.auditLogDTOToAuditLog(auditLogDTO);

        // Utilisez saveAndFlush pour obtenir l'ID généré immédiatement
        auditLog = auditLogRepository.saveAndFlush(auditLog);

        return auditLogMapper.auditLogToAuditLogDTO(auditLog);
    }
    private void validateAuditLogDTO(AuditLogDTO auditLogDTO) {
        if (auditLogDTO == null) {
            apiExceptionHandler.handleRequestException(new RequestException("AuditLogDTO ne doit pas être null", HttpStatus.BAD_REQUEST));
        }
        if (auditLogDTO.getTypeOfEvent() == null || auditLogDTO.getTypeOfEvent().isEmpty()) {
            apiExceptionHandler.handleRequestException(new RequestException("Le champ TypeOfEvent est requis", HttpStatus.BAD_REQUEST));
        }

        if (auditLogDTO.getUsername() == null || auditLogDTO.getUsername().isEmpty()) {
            apiExceptionHandler.handleRequestException(new RequestException("Le champ Username est requis", HttpStatus.BAD_REQUEST));
        }

        if (auditLogDTO.getObjectName() == null || auditLogDTO.getObjectName().isEmpty()) {
            apiExceptionHandler.handleRequestException(new RequestException("Le champ ObjectName est requis", HttpStatus.BAD_REQUEST));
        }

        if (auditLogDTO.getCreateDate() == null) {
            apiExceptionHandler.handleRequestException(new RequestException("Le champ CreateDate est requis", HttpStatus.BAD_REQUEST));
        }

        if (auditLogDTO.getRecordedMessage() == null || auditLogDTO.getRecordedMessage().isEmpty()) {
            apiExceptionHandler.handleRequestException(new RequestException("Le champ RecordedMessage est requis", HttpStatus.BAD_REQUEST));
        }
    }
    @Override
    @Transactional
    public Optional<AuditLogDTO> findById(Long id) {
        return Optional.ofNullable(auditLogRepository.findById(id)
                .map(auditLogMapper::auditLogToAuditLogDTO)
                .orElseThrow(() -> new EntityNotFoundException(getMessage("auditlog.notfound", id))));
    }
    @Transactional
    @Override
    public Page<AuditLogDTO> getAllAuditLogs(Pageable pageable) {
        return auditLogRepository.findAll(pageable)
                .map(auditLogMapper::auditLogToAuditLogDTO);
    }
    @Transactional
    @Override
    public Page<AuditLogDTO> findByUsername(String username, Pageable pageable) {
        try{
            return auditLogRepository.findByUsername(username, pageable)
                    .map(auditLogMapper::auditLogToAuditLogDTO);
        }catch (DataIntegrityViolationException ex) {
            throw ex;
        }
    }
    @Override
    public Page<AuditLogDTO> findByObjectName(String objectName, Pageable pageable) {
        try{
            return auditLogRepository.findByObjectName(objectName, pageable)
                    .map(auditLogMapper::auditLogToAuditLogDTO);
        }catch (DataIntegrityViolationException ex) {
            throw ex;
        }
    }
    @Override
    public Page<AuditLogDTO> findByCreateDateAfter(LocalDateTime dateTime, Pageable pageable) {
        try{
            return auditLogRepository.findByCreateDateAfter(dateTime, pageable)
                    .map(auditLogMapper::auditLogToAuditLogDTO);
        }catch (DataIntegrityViolationException ex) {
            throw ex;
        }
    }
    @Override
    public Page<AuditLogDTO> findByCreateDateBefore(LocalDateTime dateTime, Pageable pageable) {
        try{
            return auditLogRepository.findByCreateDateBefore(dateTime, pageable)
                    .map(auditLogMapper::auditLogToAuditLogDTO);
        }catch (DataIntegrityViolationException ex) {
            throw ex;
        }
    }
    @Override
    public Page<AuditLogDTO> findAllByCreateDateBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable) {
        try{
            return auditLogRepository.findAllByCreateDateBetween(startDateTime, endDateTime, pageable)
                    .map(auditLogMapper::auditLogToAuditLogDTO);
        }catch (DataIntegrityViolationException ex) {
            throw ex;
        }
    }
    @Override
    public Page<AuditLogDTO> findByUsernameAndCreateDate(String username, LocalDateTime dateTime, Pageable pageable) {
        try{
            return auditLogRepository.findByUsernameAndCreateDate(username, dateTime, pageable)
                    .map(auditLogMapper::auditLogToAuditLogDTO);
        }catch (DataIntegrityViolationException ex) {
            throw ex;
        }
    }
    @Override
    public Page<AuditLogDTO> findByObjectNameAndCreateDate(String objectName, LocalDateTime dateTime, Pageable pageable) {
        try{
            return auditLogRepository.findByObjectNameAndCreateDate(objectName, dateTime, pageable)
                    .map(auditLogMapper::auditLogToAuditLogDTO);
        }catch (DataIntegrityViolationException ex) {
            throw ex;
        }
    }
    @Override
    public Page<AuditLogDTO> findByUsernameAndCreateDateBetween(String username, LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable) {
        try{
            return auditLogRepository.findByUsernameAndCreateDateBetween(username, startDateTime, endDateTime, pageable)
                    .map(auditLogMapper::auditLogToAuditLogDTO);
        }catch (DataIntegrityViolationException ex) {
            throw ex;
        }
    }
    @Override
    public Page<AuditLogDTO> findByObjectNameAndCreateDateBetween(String objectName, LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable) {
        try{
            return auditLogRepository.findByObjectNameAndCreateDateBetween(objectName, startDateTime, endDateTime, pageable)
                    .map(auditLogMapper::auditLogToAuditLogDTO);
        }catch (DataIntegrityViolationException ex) {
            throw ex;
        }
    }
    @Transactional
    @Override
    public void deleteById(Long id) {
        try {
            auditLogRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw ex;
        }
    }
    @Transactional
    @Override
    public AuditLogDTO updateAuditLog(Long id, AuditLogDTO updatedAuditLogDTO) {
        AuditLog existingAuditLog = auditLogRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(getMessage("auditlog.notfound", id)));
        // Mise à jour de les propriétés du journal d'audit
        /*existingAuditLog.setTypeOfEvent(updatedAuditLogDTO.getTypeOfEvent());
        existingAuditLog.setUsername(updatedAuditLogDTO.getUsername());
        existingAuditLog.setObjectName(updatedAuditLogDTO.getObjectName());
        existingAuditLog.setCreateDate(updatedAuditLogDTO.getCreateDate());*/
        existingAuditLog.setRecordedMessage(updatedAuditLogDTO.getRecordedMessage());

        validateAuditLogDTO(updatedAuditLogDTO);

        existingAuditLog = auditLogRepository.save(existingAuditLog);

        return auditLogMapper.auditLogToAuditLogDTO(existingAuditLog);
    }
    private String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, Locale.getDefault());
    }
}
