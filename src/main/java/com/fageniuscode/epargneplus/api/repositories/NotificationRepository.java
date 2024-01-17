package com.fageniuscode.epargneplus.api.repositories;

import com.fageniuscode.epargneplus.api.entities.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Optional<Notification> findById(Long id);

    Page<Notification> findByNotificationType(String notificationType, Pageable pageable);

    Page<Notification> findByIsReadNotification(boolean isReadNotification, Pageable pageable);

    Page<Notification> findByNotificationUsername(String notificationUsername, Pageable pageable);

    Page<Notification> findByNotificationTransactionNumber(String notificationTransactionNumber, Pageable pageable);

    Page<Notification> findByCreateDateAfter(LocalDateTime dateTime, Pageable pageable);

    Page<Notification> findByCreateDateBefore(LocalDateTime dateTime, Pageable pageable);

    Page<Notification> findAllByCreateDateBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);

    Page<Notification> findByNotificationUsernameAndCreateDate(String notificationUsername, LocalDateTime dateTime, Pageable pageable);

    Page<Notification> findByNotificationTransactionNumberAndCreateDate(String notificationTransactionNumber, LocalDateTime dateTime, Pageable pageable);

    Page<Notification> findByNotificationUsernameAndCreateDateBetween(String username, LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);

    Page<Notification> findByNotificationTransactionNumberAndCreateDateBetween(String notificationTransactionNumber, LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);
}
