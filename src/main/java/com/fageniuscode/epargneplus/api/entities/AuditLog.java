package com.fageniuscode.epargneplus.api.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ep_audit_log")
@Data
@NoArgsConstructor
@Getter
@Setter
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String typeOfEvent;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String objectName;
    @Column(nullable = false)
    private LocalDateTime createDate;
    @Column(nullable = false)
    private String recordedMessage;

    public AuditLog(Long id, String typeOfEvent, String username, String objectName, LocalDateTime createDate, String recordedMessage) {
        this.id = id;
        this.typeOfEvent = typeOfEvent;
        this.username = username;
        this.objectName = objectName;
        this.createDate = createDate;
        this.recordedMessage = recordedMessage;
    }

    @Override
    public String toString() {
        return "AuditLog{" +
                "id=" + id +
                ", typeOfEvent='" + typeOfEvent + '\'' +
                ", username='" + username + '\'' +
                ", objectName='" + objectName + '\'' +
                ", createDate=" + createDate +
                ", recordedMessage='" + recordedMessage + '\'' +
                '}';
    }
}
