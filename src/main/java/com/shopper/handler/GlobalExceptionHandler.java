package com.shopper.handler;

import com.shopper.dto.response.ApiError;
import com.shopper.exceptions.CartItemNotFoundException;
import com.shopper.exceptions.InvalidInputException;
import com.shopper.exceptions.InvalidProductException;
import com.shopper.exceptions.NotEnoughInventoryException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidProductException.class)
    public ResponseEntity<ApiError> handleInvalidProductException(InvalidProductException e, WebRequest request) {
        ApiError apiError = ApiError.builder()
                .errorMessage(String.format("Invalid product id %s ", e.getProductId()))
                .errorCode(4001)
                .build();
        return ResponseEntity.badRequest().body(apiError);

    }
    @ExceptionHandler(NotEnoughInventoryException.class)
    public ResponseEntity<ApiError> handleNotEnoughInventoryException(NotEnoughInventoryException e, WebRequest request) {
        ApiError apiError = ApiError.builder()
                .errorMessage(String.format("Requested quantity = %s, maximum quantity  = %s ",
                                e.getRequestedQuantity(), e.getMaximumQuantity()))
                .errorCode(4001)
                .build();
        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(CartItemNotFoundException.class)
    public ResponseEntity<ApiError> handleCartItemNotFoundException(CartItemNotFoundException e, WebRequest request) {
        ApiError apiError = ApiError.builder()
                .errorMessage(String.format("Product ID = %s requested for deletion is absent from cart", e.getProductId()))
                .errorCode(4001)
                .build();
        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ApiError> handleInvalidInputException(InvalidInputException e, WebRequest request) {
        ApiError apiError = ApiError.builder()
                .errorMessage(e.getMessage())
                .errorCode(4001)
                .build();
        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception e, WebRequest request) {
        ApiError apiError = ApiError.builder()
                .errorMessage(String.format("Something went wrong %s", e.getMessage()))
                .errorCode(4001)
                .build();
        return ResponseEntity.internalServerError().body(apiError);
    }



}
