package com.shopper.dto.request;

import com.shopper.dto.response.CartItemResponse;
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
public class CartRequest {
    private String username;

    private List<CartItemResponse> cartItems;
}
