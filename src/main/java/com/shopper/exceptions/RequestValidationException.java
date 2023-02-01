package com.shopper.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RequestValidationException extends RuntimeException {
    private String message;
}
