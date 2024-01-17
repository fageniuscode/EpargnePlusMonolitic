package com.fageniuscode.epargneplus.api.services.impl;

import com.fageniuscode.epargneplus.api.entities.dto.NotificationDTO;
import com.fageniuscode.epargneplus.api.entities.Notification;
import com.fageniuscode.epargneplus.api.exceptions.EntityNotFoundException;
import com.fageniuscode.epargneplus.api.mappings.NotificationMapper;
import com.fageniuscode.epargneplus.api.repositories.NotificationRepository;
import com.fageniuscode.epargneplus.api.services.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final MessageSource messageSource;

    @Transactional
    @Override
    public NotificationDTO saveNotification(NotificationDTO notificationDTO) {
        validateNotificationDTO( notificationDTO);

        Notification notification = notificationMapper.notificationDTOToNotification(notificationDTO);

        notification = notificationRepository.saveAndFlush(notification);

        return notificationMapper.notificationToNotificationDTO(notification);
    }

    private void validateNotificationDTO(NotificationDTO notificationDTO) {
        if (notificationDTO == null) {
            throw new IllegalArgumentException("NotificationDTO ne doit pas être null");
        }
        // Validez les autres champs ici et lancez des exceptions si nécessaire.
    }

    @Transactional
    @Override
    public Page<NotificationDTO> getAllNotifications(Pageable pageable) {
        return notificationRepository.findAll(pageable)
                .map(notificationMapper::notificationToNotificationDTO);
    }

    @Override
    @Transactional
    public Optional<NotificationDTO> findById(Long id) {
        return Optional.ofNullable(notificationRepository.findById(id)
                .map(notificationMapper::notificationToNotificationDTO)
                .orElseThrow(() -> new EntityNotFoundException(getMessage("notification.notfound", id))));
    }

    @Transactional
    @Override
    public Page<NotificationDTO> findByNotificationType(String notificationType, Pageable pageable) {
        return notificationRepository.findByNotificationType(notificationType, pageable)
                .map(notificationMapper::notificationToNotificationDTO);
    }

    @Transactional
    @Override
    public Page<NotificationDTO> findByIsReadNotification(boolean isReadNotification, Pageable pageable) {
        return notificationRepository.findByIsReadNotification(isReadNotification, pageable)
                .map(notificationMapper::notificationToNotificationDTO);
    }

    @Transactional
    @Override
    public Page<NotificationDTO> findByNotificationUsername(String notificationUsername, Pageable pageable) {
        return notificationRepository.findByNotificationUsername(notificationUsername, pageable)
                .map(notificationMapper::notificationToNotificationDTO);
    }

    @Transactional
    @Override
    public Page<NotificationDTO> findByNotificationTransactionNumber(String notificationTransactionNumber, Pageable pageable) {
        return notificationRepository.findByNotificationTransactionNumber(notificationTransactionNumber, pageable)
                .map(notificationMapper::notificationToNotificationDTO);
    }

    @Transactional
    @Override
    public Page<NotificationDTO> findByCreateDateAfter(LocalDateTime dateTime, Pageable pageable) {
        return notificationRepository.findByCreateDateAfter(dateTime, pageable)
                .map(notificationMapper::notificationToNotificationDTO);
    }

    @Transactional
    @Override
    public Page<NotificationDTO> findByCreateDateBefore(LocalDateTime dateTime, Pageable pageable) {
        return notificationRepository.findByCreateDateBefore(dateTime, pageable)
                .map(notificationMapper::notificationToNotificationDTO);
    }

    @Transactional
    @Override
    public Page<NotificationDTO> findAllByCreateDateBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable) {
        return notificationRepository.findAllByCreateDateBetween(startDateTime, endDateTime, pageable)
                .map(notificationMapper::notificationToNotificationDTO);
    }

    @Transactional
    @Override
    public Page<NotificationDTO> findByNotificationUsernameAndCreateDate(String notificationUsername, LocalDateTime dateTime, Pageable pageable) {
        return notificationRepository.findByNotificationUsernameAndCreateDate(notificationUsername, dateTime, pageable)
                .map(notificationMapper::notificationToNotificationDTO);
    }

    @Transactional
    @Override
    public Page<NotificationDTO> findByNotificationTransactionNumberAndCreateDate(String notificationTransactionNumber, LocalDateTime dateTime, Pageable pageable) {
        return notificationRepository.findByNotificationTransactionNumberAndCreateDate(notificationTransactionNumber, dateTime, pageable)
                .map(notificationMapper::notificationToNotificationDTO);
    }

    @Transactional
    @Override
    public Page<NotificationDTO> findByNotificationUsernameAndCreateDateBetween(String username, LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable) {
        return notificationRepository.findByNotificationUsernameAndCreateDateBetween(username, startDateTime, endDateTime, pageable)
                .map(notificationMapper::notificationToNotificationDTO);
    }

    @Transactional
    @Override
    public Page<NotificationDTO> findByNotificationTransactionNumberAndCreateDateBetween(String notificationTransactionNumber, LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable) {
        return notificationRepository.findByNotificationTransactionNumberAndCreateDateBetween(notificationTransactionNumber, startDateTime, endDateTime, pageable)
                .map(notificationMapper::notificationToNotificationDTO);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        try {
            notificationRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Impossible de supprimer la notiifcation. Elle est utilisée ailleurs.");
        }
    }

    @Transactional
    @Override
    public NotificationDTO updateNotification(Long id, NotificationDTO updatedNotification) {
        Notification existingNotification = notificationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(getMessage("notification.notfound", id)));
        // Mise à jour de les propriétés du journal d'audit
        existingNotification.setReadNotification(false);
        existingNotification.setNotificationMessage(updatedNotification.getNotificationMessage());

        existingNotification = notificationRepository.save(existingNotification);

        return notificationMapper.notificationToNotificationDTO(existingNotification);
    }

    private String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, Locale.getDefault());
    }
}
