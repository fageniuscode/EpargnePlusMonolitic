package com.fageniuscode.epargneplus.api.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ep_notification")
@Data
@NoArgsConstructor
@Getter
@Setter
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String notificationType;
    @Column(nullable = false)
    private LocalDateTime createDate;
    private boolean isReadNotification;
    private String notificationMessage;
    @Column(nullable = true)
    private String notificationUsername;
    @Column(nullable = true)
    private String notificationTransactionNumber;

    public Notification(Long id, String notificationType, LocalDateTime createDate, boolean isReadNotification, String notificationMessage, String notificationUsername, String notificationTransactionNumber) {
        this.id = id;
        this.notificationType = notificationType;
        this.createDate = createDate;
        this.isReadNotification = isReadNotification;
        this.notificationMessage = notificationMessage;
        this.notificationUsername = notificationUsername;
        this.notificationTransactionNumber = notificationTransactionNumber;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", notificationType='" + notificationType + '\'' +
                ", createDate=" + createDate +
                ", isReadNotification=" + isReadNotification +
                ", notificationMessage='" + notificationMessage + '\'' +
                ", notificationUsername='" + notificationUsername + '\'' +
                ", notificationTransactionNumber='" + notificationTransactionNumber + '\'' +
                '}';
    }
}
