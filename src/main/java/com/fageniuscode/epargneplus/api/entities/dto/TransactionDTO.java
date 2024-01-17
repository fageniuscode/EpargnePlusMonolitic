package com.fageniuscode.epargneplus.api.entities.dto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

@Data
@NoArgsConstructor
@Getter
@Setter
public class TransactionDTO {

    private Long id;

    @NotNull(message = "Le montant ne peut pas être nul")
    @Positive(message = "Le montant doit être positif")
    private Double amount;

    @NotBlank(message = "La description ne peut pas être vide")
    private String description;

    @NotNull(message = "La date de création ne peut pas être nulle")
    private Date createDate;

    @NotBlank(message = "La devise ne peut pas être vide")
    private String devise;

    @NotNull(message = "L'identifiant du wallet ne peut pas être nul")
    private Long walletId;

    @NotNull(message = "L'identifiant du type de transaction ne peut pas être nul")
    private Long typeTransactionId;

    @NotNull(message = "L'identifiant du statut de transaction ne peut pas être nul")
    private Long statusTransactionId;

    public TransactionDTO(Double amount, String description, Date createDate, String devise,Long walletId, Long typeTransactionId, Long statusTransactionId) {
        this.amount = amount;
        this.description = description;
        this.createDate = createDate;
        this.devise = devise;
        this.walletId = walletId;
        this.typeTransactionId = typeTransactionId;
        this.statusTransactionId = statusTransactionId;
    }

}
