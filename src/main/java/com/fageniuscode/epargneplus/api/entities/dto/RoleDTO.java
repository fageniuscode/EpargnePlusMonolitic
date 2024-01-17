package com.fageniuscode.epargneplus.api.entities.dto;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoleDTO {
    private int id;
    @NotNull(message = "Le nom ne peut pas être vide")
    @Size(max = 100, message = "Le nom ne peut pas dépasser 100 caractères")
    private String name;
    @NotNull(message = "La description ne peut pas être vide")
    @Size(max = 250, message = "La description ne peut pas dépasser 250 caractères")
    private String description;
    @NotNull(message = "Le niveau d'accès ne peut pas être vide")
    @Size(max = 20, message = "Le niveau d'accès ne peut pas dépasser 20 caractères")
    private String accessLevel;
}
