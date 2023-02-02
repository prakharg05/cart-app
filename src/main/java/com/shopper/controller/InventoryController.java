package com.shopper.controller;


import com.shopper.dao.Inventory;
import com.shopper.dto.InventoryDTO;
import com.shopper.dto.ProductDTO;
import com.shopper.dto.request.InventoryRequest;
import com.shopper.dto.response.InventoryResponse;
import com.shopper.repository.InventoryRepository;
import com.shopper.service.InventoryService;
import com.shopper.utils.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller to route Inventory related requests
 */
@RestController
@Slf4j
@RequestMapping("/api/product")
public class InventoryController {


    private InventoryService inventoryService;

    @Autowired
    public InventoryController( InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }


    /**
     * List all inventory, including inventory quantity
     * @return
     */
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<InventoryResponse> getAllInventory() {
        return inventoryService.getAllInventory().stream().map(this::adaptToResponse).collect(Collectors.toList());
    }

    private InventoryResponse adaptToResponse(InventoryDTO inventoryDTO) {
        return InventoryResponse.builder()
                .productName(inventoryDTO.getProductName())
                .quantity(inventoryDTO.getQuantity())
                .productId(inventoryDTO.getProductId())
                .price(inventoryDTO.getPrice())
                .build();
    }


    /**
     * List all in stock items, without inventory quantity
     * @return
     */
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<ProductDTO> getAllAvailableProducts() {
        return inventoryService.getAllAvailableProducts();
    }

    /**
     * Create or update inventory, adds product name, price and quantity.
     * @param inventoryRequest
     * @return
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public InventoryResponse createInventory( @RequestBody InventoryRequest inventoryRequest) {

        log.info("Create Inventory Request {}", inventoryRequest);
        ValidationUtils.validateInventoryRequestForCreation(inventoryRequest);
        InventoryDTO inventoryDTO = adaptToDTO(inventoryRequest);
         return adaptToResponse(inventoryService.createInventory(inventoryDTO));
    }

    public InventoryDTO adaptToDTO(InventoryRequest inventoryRequest) {
        return  InventoryDTO.builder()
                .price(inventoryRequest.getPrice())
                .quantity(inventoryRequest.getQuantity())
                .productId(inventoryRequest.getProductId())
                .productName(inventoryRequest.getProductName())
                .build();
    }


    /**
     * Delete product from exisitng inventory
     * @param inventoryRequest
     * @return
     */
    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public InventoryResponse deleteInventory(@RequestBody InventoryRequest inventoryRequest) {
        log.info("Delete Inventory Request {}", inventoryRequest);
        ValidationUtils.validateInventoryRequestForDeletion(inventoryRequest);
        InventoryDTO inventoryDTO = adaptToDTO(inventoryRequest);
        return adaptToResponse(inventoryService.deleteInventory(inventoryDTO));
    }
}
