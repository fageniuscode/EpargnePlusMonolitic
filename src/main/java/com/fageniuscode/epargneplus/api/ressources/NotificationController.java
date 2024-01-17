package com.fageniuscode.epargneplus.api.ressources;

import com.fageniuscode.epargneplus.api.entities.dto.NotificationDTO;
import com.fageniuscode.epargneplus.api.exceptions.EntityNotFoundException;
import com.fageniuscode.epargneplus.api.exceptions.RequestException;
import com.fageniuscode.epargneplus.api.services.NotificationService;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.Locale;

@RestController
@RequestMapping("/api/notification/epnotification")
public class NotificationController {
    private final NotificationService notificationService;
    private final MessageSource messageSource;

    public NotificationController(NotificationService notificationService, MessageSource messageSource) {
        this.notificationService = notificationService;
        this.messageSource = messageSource;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<NotificationDTO> createNotification(@Valid @RequestBody NotificationDTO notificationDTO) {
        try {
            NotificationDTO createNotification = notificationService.saveNotification(notificationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createNotification);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<NotificationDTO>> getAllNotifications(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<NotificationDTO> notifications = notificationService.getAllNotifications(pageable);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationDTO> getNotificationById(@PathVariable Long id) {
        try {
            NotificationDTO notification = notificationService.findById(id).orElseThrow(() -> new EntityNotFoundException(getMessage("notification.notfound", id)));
            return ResponseEntity.ok(notification);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-notificationtype")
    public ResponseEntity<Page<NotificationDTO>> findByNotificationType(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam String notificationType){
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<NotificationDTO> notifications = notificationService.findByNotificationType(notificationType, pageable);
            return new ResponseEntity<>(notifications, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/by-notificationread")
    public ResponseEntity<Page<NotificationDTO>> findByIsReadNotification(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam boolean isReadNotification){
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<NotificationDTO> notifications = notificationService.findByIsReadNotification(isReadNotification, pageable);
            return new ResponseEntity<>(notifications, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/by-notificationusername")
    public ResponseEntity<Page<NotificationDTO>> findByNotificationUsername(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam String notificationUsername){
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<NotificationDTO> notifications = notificationService.findByNotificationUsername(notificationUsername, pageable);
            return new ResponseEntity<>(notifications, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/by-notification-transaction-number")
    public ResponseEntity<Page<NotificationDTO>> findByNotificationTransactionNumber(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam String notificationTransactionNumber){
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<NotificationDTO> notifications = notificationService.findByNotificationTransactionNumber(notificationTransactionNumber, pageable);
            return new ResponseEntity<>(notifications, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/by-notification-create-date-after")
    public ResponseEntity<Page<NotificationDTO>> findByCreateDateAfter(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(value = "localDateTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDateTime){
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<NotificationDTO> auditLogs = notificationService.findByCreateDateAfter(localDateTime, pageable);
            return new ResponseEntity<>(auditLogs, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/by-notification-create-date-before")
    public ResponseEntity<Page<NotificationDTO>> findByCreateDateBefore(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(value = "localDateTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime localDateTime){
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<NotificationDTO> auditLogs = notificationService.findByCreateDateBefore(localDateTime, pageable);
            return new ResponseEntity<>(auditLogs, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/by-notification-create-date-between")
    public ResponseEntity<Page<NotificationDTO>> findAllByCreateDateBetween(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(value = "startDateTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime, @RequestParam(value = "endDateTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime ){
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<NotificationDTO> auditLogs = notificationService.findAllByCreateDateBetween(startDateTime, endDateTime, pageable);
            return new ResponseEntity<>(auditLogs, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/by-notification-username-and-create-date")
    public ResponseEntity<Page<NotificationDTO>> findByNotificationUsernameAndCreateDate(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam String notificationUsername, @RequestParam(value = "dateTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  LocalDateTime dateTime){
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<NotificationDTO> auditLogs = notificationService.findByNotificationUsernameAndCreateDate(notificationUsername, dateTime, pageable);
            return new ResponseEntity<>(auditLogs, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/by-notification-transction-number-and-create-date")
    public ResponseEntity<Page<NotificationDTO>> findByNotificationTransactionNumberAndCreateDate(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam String notificationTransactionNumber, @RequestParam(value = "dateTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  LocalDateTime dateTime){
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<NotificationDTO> auditLogs = notificationService.findByNotificationTransactionNumberAndCreateDate(notificationTransactionNumber, dateTime, pageable);
            return new ResponseEntity<>(auditLogs, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/by-notification-username-and-create-date-between")
    public ResponseEntity<Page<NotificationDTO>> findByNotificationUsernameAndCreateDateBetween(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam String username, @RequestParam(value = "startDateTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  LocalDateTime startDateTime, @RequestParam(value = "endDateTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  LocalDateTime endDateTime){
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<NotificationDTO> auditLogs = notificationService.findByNotificationUsernameAndCreateDateBetween(username, startDateTime, endDateTime, pageable);
            return new ResponseEntity<>(auditLogs, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/by-notification-transaction-number-and-create-date-between")
    public ResponseEntity<Page<NotificationDTO>> findByNotificationTransactionNumberAndCreateDateBetween(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam String notificationTransactionNumber, @RequestParam(value = "startDateTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  LocalDateTime startDateTime, @RequestParam(value = "endDateTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  LocalDateTime endDateTime){
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<NotificationDTO> auditLogs = notificationService.findByNotificationTransactionNumberAndCreateDateBetween(notificationTransactionNumber, startDateTime, endDateTime, pageable);
            return new ResponseEntity<>(auditLogs, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/deletenotif/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        try {
            notificationService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{notificationId}")
    public ResponseEntity<NotificationDTO> updateAuditLog(@PathVariable @Min(1) Long notificationId, @RequestBody @Valid NotificationDTO updatedNotificationDTO) {
        try {
            NotificationDTO updateNotification = notificationService.updateNotification(notificationId, updatedNotificationDTO);
            return ResponseEntity.ok(updateNotification);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (RequestException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    private String getMessage(String key, Object... args) {
        return messageSource.getMessage(key, args, Locale.getDefault());
    }
}
