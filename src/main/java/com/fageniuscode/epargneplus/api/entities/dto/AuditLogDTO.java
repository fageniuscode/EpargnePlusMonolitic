package com.fageniuscode.epargneplus.api.entities.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Getter
@Setter
public class AuditLogDTO {
    private Long id;
    @NotBlank(message = "Le type ne peut pas être vide")
    private String typeOfEvent;
    @NotBlank(message = "L'identifiant de l'utilisateur ne peut pas être vide")
    private String username;

    @NotBlank(message = "Le nom de l'objet ne doit pas être vide")
    private String objectName;
    @NotNull(message = "La date de création ne peut pas être nulle")
    private LocalDateTime createDate;
    @NotBlank(message = "Le message ne peut pas être vide")
    private String recordedMessage;

    public AuditLogDTO(Long id, String typeOfEvent, String username, String objectName, LocalDateTime createDate, String recordedMessage) {
        this.id = id;
        this.typeOfEvent = typeOfEvent;
        this.username = username;
        this.objectName = objectName;
        this.createDate = createDate;
        this.recordedMessage = recordedMessage;
    }
}
