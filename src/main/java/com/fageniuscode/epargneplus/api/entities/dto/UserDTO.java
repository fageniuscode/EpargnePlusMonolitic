package com.fageniuscode.epargneplus.api.entities.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Data
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private Long id;
    @NotNull(message = "Le nom ne peut pas être vide")
    @Size(min = 2, max = 150, message = "Le nom doit avoir entre 2 et 150 caractères")
    private String lastName;
    @NotNull(message = "Le prénom ne peut pas être vide")
    @Size(min = 2, max = 200, message = "Le prénom doit avoir entre 2 et 200 caractères")
    private String firstName;
    @NotNull(message = "L'email ne peut pas être vide")
    @Size(max = 100, message = "L'email ne peut pas dépasser 100 caractères")
    @Email(message = "L'email doit être une adresse email valide")
    private String email;
    @NotNull(message = "Le mot de passe ne peut pas être vide")
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String password;
    @NotNull(message = "La date de naissance ne peut pas être vide")
    private Date dateNaiss;
    @NotNull(message = "La nationalité ne peut pas être vide")
    @Size(max = 150, message = "La nationalité ne peut pas dépasser 150 caractères")
    private String nationality;
    @NotNull(message = "Le genre ne peut pas être vide")
    @Size(max = 40, message = "Le genre ne peut pas dépasser 40 caractères")
    private String gender;
    @NotNull(message = "Le username ne peut pas être vide")
    @Size(max = 40, message = "Le username ne peut pas dépasser 40 caractères")
    private String username;
    @NotNull(message = "Le numéro de téléphone ne peut pas être vide")
    @Size(max = 20, message = "Le numéro de téléphone ne peut pas dépasser 20 caractères")
    private String phoneNumber;
    private LocalDateTime createdDate;
    private LocalDateTime lastLoginDate;
    private LocalDateTime lastLoginDateDisplay;
    @NotNull(message = "Le statut du compte ne peut pas être vide")
    private boolean isActive;
    private boolean isNotLocked;
    private List<RoleDTO> rolesDTO;
    public UserDTO(Long id, String lastName, String firstName, String email, String password, Date dateNaiss, String nationality, String gender, String username, String phoneNumber, boolean isActive, boolean isNotLocked) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.dateNaiss = dateNaiss;
        this.nationality = nationality;
        this.gender = gender;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.isActive = isActive;
        this.isNotLocked = isNotLocked;
    }
}
