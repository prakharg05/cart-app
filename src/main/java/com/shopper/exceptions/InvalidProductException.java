package com.shopper.exceptions;

import lombok.Builder;
import lombok.Data;

/**
 * Exception to denote erroneous product id in user input.
 */
@Data
@Builder
public class InvalidProductException extends RuntimeException{
    private Long productId;
}
