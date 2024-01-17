package com.fageniuscode.epargneplus.api.commons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Wallet {
    private Long id;
    private String walletNumber;
    private double amount;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private String username;
    private int lockingTime;
    private String walletStatus;
    private InterestRate interestRate;
}
