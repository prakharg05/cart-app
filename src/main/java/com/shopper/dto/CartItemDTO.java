package com.shopper.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {

    private String username;
    private Long productId;
    private String productName;

    private Integer cartQuantity;

    private Integer inventoryQuantity;

}
