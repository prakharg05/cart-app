package com.shopper.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDTO {
    private Long productId;
    private String productName;

    private Integer productPrice;
}
