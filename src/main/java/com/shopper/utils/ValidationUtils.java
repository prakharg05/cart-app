package com.shopper.utils;

import com.shopper.dto.InventoryDTO;
import com.shopper.dto.request.CartItemRequest;
import com.shopper.dto.request.InventoryRequest;
import com.shopper.exceptions.RequestValidationException;
import org.springframework.security.core.parameters.P;

import java.util.Objects;
import java.util.regex.Pattern;


public class ValidationUtils {

    private static String EMAIL_REGEX  = "^(.+)@(\\S+)$";
    public static boolean isValidEmail(String email) {
        if(Objects.isNull(email)) {
            return false;
        }
        return Pattern.compile(EMAIL_REGEX)
                .matcher(email)
                .matches();
    }


    public static void validateInventoryRequestForCreation(InventoryRequest inventoryRequest) {
        if(Objects.isNull(inventoryRequest)) {
            throwRequestValidation("Null request");
        }
        if(Objects.isNull(inventoryRequest.getQuantity())
                || Objects.isNull(inventoryRequest.getProductName())
                || Objects.isNull(inventoryRequest.getPrice())) {
            throwRequestValidation("Product Name, Price and Quantity must be non null");
        }
    }


    public static void validateInventoryRequestForDeletion(InventoryRequest inventoryRequest) {
        if(Objects.isNull(inventoryRequest)) {
            throwRequestValidation("Null request");
        }
        if(Objects.isNull(inventoryRequest.getProductId())) {
            throwRequestValidation("Product ID must be non null");
        }
    }


    public static void validateCartItemRequest(CartItemRequest cartItemRequest) {
        if(Objects.isNull(cartItemRequest)) {
            throwRequestValidation("Null request");
        }

        if(Objects.isNull(cartItemRequest.getProductId())
                || Objects.isNull(cartItemRequest.getCartQuantity())) {
            throwRequestValidation("Product ID and Quantity attributes must be NON NULL");
        }
    }


    public static void validateCartItemRequestForDeletion(CartItemRequest cartItemRequest) {
        if(Objects.isNull(cartItemRequest)) {
            throwRequestValidation("Null request");
        }

        if(Objects.isNull(cartItemRequest.getProductId())) {
            throwRequestValidation("Product ID must be NON NULL");
        }
    }

    public static void throwRequestValidation(String message) {
        throw RequestValidationException.builder().errorMessage(message).build();
    }


}
