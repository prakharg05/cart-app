package com.shopper.exceptions;

import lombok.Builder;
import lombok.Data;

/**
 * Exception when item in the request is missing from user cart.
 */
@Data
@Builder
public class CartItemNotFoundException extends RuntimeException{

    private Long productId;
}
