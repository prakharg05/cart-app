package com.shopper.controller;


import com.shopper.dao.Inventory;
import com.shopper.dto.InventoryDTO;
import com.shopper.dto.ProductDTO;
import com.shopper.repository.InventoryRepository;
import com.shopper.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/product")
public class InventoryController {


    private InventoryService inventoryService;

    public InventoryController( InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }


    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<InventoryDTO> getAllInventory() {
    return inventoryService.getAllInventory();
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<ProductDTO> getAllAvailableProducts() {
        return inventoryService.getAllAvailableProducts();
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public InventoryDTO createInventory( @RequestBody InventoryDTO inventoryDTO) {
         return inventoryService.createInventory(inventoryDTO);
    }


    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public InventoryDTO updateInventory(@RequestBody InventoryDTO inventoryDTO) {
        return inventoryService.updateInventory(inventoryDTO);
    }


    @PostMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public InventoryDTO deleteInventory(@RequestBody InventoryDTO inventoryDTO) {
        return inventoryService.deleteInventory(inventoryDTO);
    }
}
