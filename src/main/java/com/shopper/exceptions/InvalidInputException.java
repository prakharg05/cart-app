package com.shopper.exceptions;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class InvalidInputException extends RuntimeException {
    private String message;
}
