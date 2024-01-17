package com.fageniuscode.epargneplus.api.entities.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class NotificationDTO {
    private Long id;
    @NotBlank(message = "Le type de notification ne peut pas être vide")
    private String notificationType;
    @NotNull(message = "La date de création ne peut pas être nulle")
    private LocalDateTime createDate;
    private boolean isReadNotification;
    @NotBlank(message = "Le message ne peut pas être vide")
    private String notificationMessage;
    private String notificationUsername;
    private String notificationTransactionNumber;

    public NotificationDTO(Long id, String notificationType, LocalDateTime createDate, boolean isReadNotification, String notificationMessage, String notificationUsername, String notificationTransactionNumber) {
        this.id = id;
        this.notificationType = notificationType;
        this.createDate = createDate;
        this.isReadNotification = isReadNotification;
        this.notificationMessage = notificationMessage;
        this.notificationUsername = notificationUsername;
        this.notificationTransactionNumber = notificationTransactionNumber;
    }

    public Long getId() {
        return id;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public boolean isReadNotification() {
        return isReadNotification;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public String getNotificationUsername() {
        return notificationUsername;
    }

    public String getNotificationTransactionNumber() {
        return notificationTransactionNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public void setReadNotification(boolean readNotification) {
        isReadNotification = readNotification;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public void setNotificationUsername(String notificationUsername) {
        this.notificationUsername = notificationUsername;
    }

    public void setNotificationTransactionNumber(String notificationTransactionNumber) {
        this.notificationTransactionNumber = notificationTransactionNumber;
    }

}
