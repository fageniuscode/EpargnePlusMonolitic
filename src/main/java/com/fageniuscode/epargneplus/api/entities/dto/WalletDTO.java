package com.fageniuscode.epargneplus.api.entities.dto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@Getter
@Setter
public class WalletDTO {
    private Long id;

    @NotBlank(message = "Le numéro de portefeuille ne peut pas être vide")
    @Size(min = 1, max = 255, message = "Le numéro de portefeuille doit avoir entre 1 et 255 caractères")
    private String walletNumber;

    @NotNull(message = "Le montant ne peut pas être vide")
    private double amount;

    @NotNull(message = "La date de création ne peut pas être vide")
    private Date createDate;

    @NotNull(message = "La date de la dernière mise à jour ne peut pas être vide")
    private Date lastUpdateDate;

    @NotBlank(message = "Le nom d'utilisateur ne peut pas être vide")
    @Size(min = 1, max = 255, message = "Le nom d'utilisateur doit avoir entre 1 et 255 caractères")
    private String username;

    @NotNull(message = "La date de verrouillage ne peut pas être vide")
    private int lockingTime;

    @NotBlank(message = "Le statut du compte ne peut pas être vide")
    @Size(min = 1, max = 255, message = "Le statut du compte doit avoir entre 1 et 255 caractères")
    private String walletStatus;

    @NotNull(message = "L'identifiant du taux d'intérêt ne peut pas être nul")
    private InterestRateDTO interestRate;

    public WalletDTO(Long id, String walletNumber, double amount, Date createDate, Date lastUpdateDate, String username, int lockingTime, String walletStatus, InterestRateDTO interestRate) {
        this.id = id;
        this.walletNumber = walletNumber;
        this.amount = amount;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.username = username;
        this.lockingTime = lockingTime;
        this.walletStatus = walletStatus;
        this.interestRate = interestRate;
    }

    public WalletDTO(Long id) {
        this.id = id;
    }
}
