package com.shopper.handler;

import com.shopper.dto.response.ApiError;
import com.shopper.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import static com.shopper.config.Config.ErrorCode.*;


/**
 * Global exception handler, to handler all exception from controller, and wrap with appropriate error codes
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidProductException.class)
    public ResponseEntity<ApiError> handleInvalidProductException(InvalidProductException e, WebRequest request) {
        ApiError apiError = ApiError.builder()
                .errorMessage(String.format("Invalid product id %s ", e.getProductId()))
                .errorCode(ERROR_INVALID_PRODUCT_ID)
                .build();
        log.error("Controller encountered an Error", e);

        return ResponseEntity.badRequest().body(apiError);

    }
    @ExceptionHandler(NotEnoughInventoryException.class)
    public ResponseEntity<ApiError> handleNotEnoughInventoryException(NotEnoughInventoryException e, WebRequest request) {
        ApiError apiError = ApiError.builder()
                .errorMessage(String.format("Requested quantity = %s, maximum quantity  = %s ",
                                e.getRequestedQuantity(), e.getMaximumQuantity()))
                .errorCode(ERROR_NOT_ENOUGH_INVENTORY)
                .build();
        log.error("Controller encountered an Error", e);

        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(CartItemNotFoundException.class)
    public ResponseEntity<ApiError> handleCartItemNotFoundException(CartItemNotFoundException e, WebRequest request) {
        ApiError apiError = ApiError.builder()
                .errorMessage(String.format("Product ID = %s requested for deletion is absent from cart", e.getProductId()))
                .errorCode(ERROR_CART_ITEM_MISSING)
                .build();
        log.error("Controller encountered an Error", e);
        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ApiError> handleInvalidInputException(InvalidInputException e, WebRequest request) {
        ApiError apiError = ApiError.builder()
                .errorMessage(e.getMessage())
                .errorCode(ERROR_INVALID_INPUT)
                .build();
        log.error("Controller encountered an Error", e);

        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(RequestValidationException.class)
    public ResponseEntity<ApiError> handleValidationFailure(RequestValidationException e, WebRequest request) {
        ApiError apiError = ApiError.builder()
                .errorMessage(e.getErrorMessage())
                .errorCode(ERROR_INVALID_INPUT)
                .build();
        log.error("Controller encountered an Error", e);

        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(UsernameTakenException.class)
    public ResponseEntity<ApiError> handleUsernameTaken(UsernameTakenException e, WebRequest request) {
        ApiError apiError = ApiError.builder()
                .errorMessage("User name already taken")
                .errorCode(ERROR_INVALID_INPUT)
                .build();
        log.error("Controller encountered an Error", e);

        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception e, WebRequest request) {
        ApiError apiError = ApiError.builder()
                .errorMessage(String.format("Something went wrong %s", e.getMessage()))
                .errorCode(ERROR_INTERNAL_SERVER_FAILURE)
                .build();
        log.error("Controller encountered an Error", e);

        return ResponseEntity.internalServerError().body(apiError);
    }



}
