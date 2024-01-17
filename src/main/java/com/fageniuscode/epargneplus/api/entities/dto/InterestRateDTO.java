package com.fageniuscode.epargneplus.api.entities.dto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Getter
@Setter
public class InterestRateDTO {
    private Long id;

    @NotNull(message = "Le taux d'intérêt ne peut pas être nul")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le taux d'intérêt doit être supérieur à 0")
    private Double rate;

    @NotBlank(message = "La description ne peut pas être vide")
    private String description;

    public InterestRateDTO(Double rate, String description) {
        this.rate = rate;
        this.description = description;
    }

    public InterestRateDTO(Long id, Double rate, String description) {
        this.id = id;
        this.rate = rate;
        this.description = description;
    }
}
