package com.fageniuscode.epargneplus.api.commons;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterestRate {
    private Long id;
    private double rate;
    private String description;
}
