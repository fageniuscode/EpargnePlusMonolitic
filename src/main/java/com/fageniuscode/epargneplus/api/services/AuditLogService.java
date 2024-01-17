package com.fageniuscode.epargneplus.api.services;
import com.fageniuscode.epargneplus.api.entities.dto.AuditLogDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AuditLogService {

    AuditLogDTO saveAuditLog(AuditLogDTO auditLogDTO);
    Optional<AuditLogDTO> findById(Long id);

    Page<AuditLogDTO> getAllAuditLogs(Pageable pageable);

    Page<AuditLogDTO> findByUsername(String username, Pageable pageable);

    Page<AuditLogDTO> findByObjectName(String objectName, Pageable pageable);

    Page<AuditLogDTO> findByCreateDateAfter(LocalDateTime dateTime, Pageable pageable);

    Page<AuditLogDTO> findByCreateDateBefore(LocalDateTime dateTime, Pageable pageable);

    Page<AuditLogDTO> findAllByCreateDateBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);

    Page<AuditLogDTO> findByUsernameAndCreateDate(String username, LocalDateTime dateTime, Pageable pageable);

    Page<AuditLogDTO> findByObjectNameAndCreateDate(String objectName, LocalDateTime dateTime, Pageable pageable);

    Page<AuditLogDTO> findByUsernameAndCreateDateBetween(String username, LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);

    Page<AuditLogDTO> findByObjectNameAndCreateDateBetween(String objectName, LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);

    void deleteById(Long id);

    AuditLogDTO updateAuditLog(Long id, AuditLogDTO updatedAuditLogDTO);
}
