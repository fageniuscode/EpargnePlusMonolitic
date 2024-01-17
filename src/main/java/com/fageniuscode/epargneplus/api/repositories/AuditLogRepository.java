package com.fageniuscode.epargneplus.api.repositories;

import com.fageniuscode.epargneplus.api.entities.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    Optional<AuditLog> findById(Long id);

    Page<AuditLog> findByUsername(String username, Pageable pageable);

    Page<AuditLog> findByObjectName(String objectName, Pageable pageable);

    Page<AuditLog> findByCreateDateAfter(LocalDateTime dateTime, Pageable pageable);

    Page<AuditLog> findByCreateDateBefore(LocalDateTime dateTime, Pageable pageable);

    Page<AuditLog> findAllByCreateDateBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);

    Page<AuditLog> findByUsernameAndCreateDate(String username, LocalDateTime dateTime, Pageable pageable);

    Page<AuditLog> findByObjectNameAndCreateDate(String objectName, LocalDateTime dateTime, Pageable pageable);

    Page<AuditLog> findByUsernameAndCreateDateBetween(String username, LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);

    Page<AuditLog> findByObjectNameAndCreateDateBetween(String objectName, LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);
}

