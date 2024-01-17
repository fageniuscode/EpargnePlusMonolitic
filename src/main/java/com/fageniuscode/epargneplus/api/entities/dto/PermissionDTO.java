package com.fageniuscode.epargneplus.api.entities.dto;
import com.fageniuscode.epargneplus.api.enumeration.PermissionType;
import com.fageniuscode.epargneplus.api.enumeration.SecurityAction;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PermissionDTO {
    private Long id;
    @NotNull(message = "Le nom ne peut pas être vide")
    @Size(max = 100, message = "Le nom ne peut pas dépasser 100 caractères")
    private String name;
    @NotNull(message = "L'action de sécurité ne peut pas être vide")
    private SecurityAction securityAction;
    @NotNull(message = "Le nom de l'entité ne peut pas être vide")
    private String entityName;
    @NotNull(message = "Le type de permission ne peut pas être vide")
    PermissionType permissionType;
}
