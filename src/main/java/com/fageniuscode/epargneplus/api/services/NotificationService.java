package com.fageniuscode.epargneplus.api.services;

import com.fageniuscode.epargneplus.api.entities.dto.NotificationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

public interface NotificationService {

    NotificationDTO saveNotification(NotificationDTO notificationDTO);

    Page<NotificationDTO> getAllNotifications(Pageable pageable);

    Optional<NotificationDTO> findById(Long id);

    Page<NotificationDTO> findByNotificationType(String notificationType, Pageable pageable);

    Page<NotificationDTO> findByIsReadNotification(boolean isReadNotification, Pageable pageable);

    Page<NotificationDTO> findByNotificationUsername(String notificationUsername, Pageable pageable);

    Page<NotificationDTO> findByNotificationTransactionNumber(String notificationTransactionNumber, Pageable pageable);

    Page<NotificationDTO> findByCreateDateAfter(LocalDateTime dateTime, Pageable pageable);

    Page<NotificationDTO> findByCreateDateBefore(LocalDateTime dateTime, Pageable pageable);

    Page<NotificationDTO> findAllByCreateDateBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);

    Page<NotificationDTO> findByNotificationUsernameAndCreateDate(String notificationUsername, LocalDateTime dateTime, Pageable pageable);

    Page<NotificationDTO> findByNotificationTransactionNumberAndCreateDate(String notificationTransactionNumber, LocalDateTime dateTime, Pageable pageable);

    Page<NotificationDTO> findByNotificationUsernameAndCreateDateBetween(String username, LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);

    Page<NotificationDTO> findByNotificationTransactionNumberAndCreateDateBetween(String notificationTransactionNumber, LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);

    void deleteById(Long id);

    NotificationDTO updateNotification(Long id, NotificationDTO updatedNotificationDTO);
}
