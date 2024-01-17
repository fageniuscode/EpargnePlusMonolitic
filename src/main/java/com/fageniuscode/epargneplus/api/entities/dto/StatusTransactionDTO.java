package com.fageniuscode.epargneplus.api.entities.dto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@Getter
@Setter
public class StatusTransactionDTO {
    private Long id;

    @NotNull(message = "Le nom ne peut pas être vide")
    @Size(min = 2, max = 150, message = "Le nom doit avoir entre 2 et 150 caractères")
    private String name;

    @NotNull(message = "Le commentaire ne peut pas être vide")
    @Size(min = 2, max = 150, message = "Le commentaire doit avoir entre 2 et 250 caractères")
    private String comment;

    private Date lastUpdateDate;

    public StatusTransactionDTO(Long id, String name, String comment, Date lastUpdateDate) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.lastUpdateDate = lastUpdateDate;
    }

    public StatusTransactionDTO(Long id) {
        this.id = id;
    }
}
