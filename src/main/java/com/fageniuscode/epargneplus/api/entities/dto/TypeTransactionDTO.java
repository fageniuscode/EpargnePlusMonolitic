package com.fageniuscode.epargneplus.api.entities.dto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@Getter
@Setter
public class TypeTransactionDTO {
    private Long id;

    @NotNull(message = "Le nom ne peut pas être vide")
    @Size(min = 2, max = 150, message = "Le nom doit avoir entre 2 et 150 caractères")
    private String name;

    @NotNull(message = "La description ne peut pas être vide")
    private String description;

    public TypeTransactionDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public TypeTransactionDTO(Long id) {
        this.id = id;
    }
}
