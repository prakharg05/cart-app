package com.shopper.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class RequestValidationException extends RuntimeException {
    private String errorMessage;
}
