package com.shopper.dto;

import com.shopper.dao.CartItem;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Builder
@Data
public class CartDTO {

    private String username;
    private List<CartItemDTO> cartItems;
}
