package com.shopper.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Builder
@Data
@AllArgsConstructor
@Jacksonized
public class InventoryRequest {
    private Long productId;
    private String productName;
    private Integer quantity;
    private Integer price;
}
