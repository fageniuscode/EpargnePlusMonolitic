package com.fageniuscode.epargneplus.api.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {
    private Long userId;
    private String message;

    // Constructeurs, getters et setters
}
