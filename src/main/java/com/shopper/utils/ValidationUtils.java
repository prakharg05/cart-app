package com.shopper.utils;

import com.shopper.dto.InventoryDTO;
import com.shopper.dto.request.CartItemRequest;
import com.shopper.exceptions.RequestValidationException;

import java.util.Objects;
import java.util.regex.Pattern;


public class ValidationUtils {

    private static String EMAIL_REGEX  = "^(.+)@(\\S+)$";
    public static boolean isValidEmail(String email) {
        return Pattern.compile(EMAIL_REGEX)
                .matcher(email)
                .matches();
    }


    public static void validateInventoryDTO(InventoryDTO inventoryDTO) {
        if(Objects.isNull(inventoryDTO)) {
            throwRequestValidation("Null request");
        }

        if(Objects.isNull(inventoryDTO.getProductId())
                || Objects.isNull(inventoryDTO.getQuantity())
                || Objects.isNull(inventoryDTO.getProductName())) {
            throwRequestValidation("All attributes must be NON NULL");
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

    public static void throwRequestValidation(String message) {
        throw new RequestValidationException(message);
    }


}
