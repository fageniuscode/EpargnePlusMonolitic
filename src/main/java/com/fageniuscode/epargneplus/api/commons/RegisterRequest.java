package com.fageniuscode.epargneplus.api.commons;

import com.fageniuscode.epargneplus.api.entities.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private UserDTO user;
    private Wallet wallet;
}
