package com.shopper.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model to hold the Authentication Token
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthToken {
    private String token;
}
