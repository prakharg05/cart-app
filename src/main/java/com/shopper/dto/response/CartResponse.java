package com.shopper.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Jacksonized
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
    private String username;

    private List<CartItemResponse> cartItems;
}
