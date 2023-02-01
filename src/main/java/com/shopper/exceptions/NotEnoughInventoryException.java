package com.shopper.exceptions;


import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@Builder
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Not Enough Inventory")  // 400
public class NotEnoughInventoryException extends  RuntimeException{
    private Integer requestedQuantity;
    private Integer maximumQuantity;
}
