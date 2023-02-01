package com.shopper.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Poyload for Authentication
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginPayload {
    private String username;
    private String password;
}
