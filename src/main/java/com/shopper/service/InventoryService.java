package com.shopper.service;

import com.shopper.dao.Inventory;
import com.shopper.dao.Product;
import com.shopper.dto.InventoryDTO;
import com.shopper.dto.ProductDTO;
import com.shopper.repository.InventoryRepository;
import com.shopper.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service(value = "inventoryService")
@Slf4j
public class InventoryService {

    @Autowired
    private ProductRepository productRepository;

    public List<InventoryDTO> getAllInventory() {
        return productRepository.findAll().stream().map(this::adaptToDTO).collect(Collectors.toList());
    }


    public List<ProductDTO> getAllAvailableProducts() {
        return productRepository.findAll().stream().filter(product -> product.getInventory().getQuantity()>0)
                .map(this::adaptToProductDTO).collect(Collectors.toList());
    }

    public InventoryDTO createInventory( InventoryDTO inventoryDTO) {

        Product product = productRepository.save(adaptToDAO(inventoryDTO));
        inventoryDTO.setProductId(product.getProductId());
        return inventoryDTO;
    }


    public InventoryDTO deleteInventory( InventoryDTO inventoryDTO) {
        productRepository.delete(adaptToDAO(inventoryDTO));
        return inventoryDTO;
    }

    private InventoryDTO adaptToDTO(Product product) {
        return InventoryDTO.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .price(product.getProductPrice())
                .quantity(product.getInventory().getQuantity())
                .build();
    }

    private ProductDTO adaptToProductDTO(Product product) {
        return ProductDTO.builder().productId(product.getProductId())
                .productPrice(product.getProductPrice())
                .productName(product.getProductName()).build();
    }
    private Product adaptToDAO(InventoryDTO inventoryDTO) {
        return Product.builder().productName(inventoryDTO.getProductName()).inventory(Inventory.builder()
                .quantity(inventoryDTO.getQuantity()).build()).productPrice(inventoryDTO.getPrice())
                .productId(inventoryDTO.getProductId()).build();

    }
}
