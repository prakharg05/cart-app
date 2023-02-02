package com.shopper.service;

import com.shopper.dao.Inventory;
import com.shopper.dao.Product;
import com.shopper.dto.InventoryDTO;
import com.shopper.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private InventoryService inventoryService;

    @Test
    public void test_getAllInventory_happy() {
        doReturn(Arrays.asList(TestData.testProduct)).when(productRepository).findAll();
        Assertions.assertTrue(inventoryService.getAllInventory().size() == 1);
    }

    @Test
    public void test_getAllAvailableProducts_happy() {
        doReturn(Arrays.asList(TestData.testProduct)).when(productRepository).findAll();
        Assertions.assertTrue(inventoryService.getAllAvailableProducts().size() == 1);
    }

    @Test
    public void test_getAllAvailableProducts_outOfStock() {
        doReturn(Arrays.asList(TestData.outOfStockProduct)).when(productRepository).findAll();
        Assertions.assertTrue(inventoryService.getAllAvailableProducts().size() == 0);
    }

    @Test
    public void test_createInventory_happy() {
        doReturn(TestData.testProduct).when(productRepository).save(any());
        inventoryService.createInventory(TestData.inventoryDTO);
        verify(productRepository, times(1)).save(any());
    }

    static class TestData {
        private static String testUser = "prkharg";
        private static Product testProduct = Product.builder().productPrice(10).productId(12l)
                .productName("Brush")
                .inventory(Inventory.builder().quantity(10).build())
                .build();

        private static Product outOfStockProduct = Product.builder().productPrice(10).productId(1l)
                .productName("Tea")
                .inventory(Inventory.builder().quantity(0).build())
                .build();

        private static InventoryDTO inventoryDTO = InventoryDTO.builder()
                .productId(testProduct.getProductId())
                .productName(testProduct.getProductName())
                .quantity(testProduct.getInventory().getQuantity())
                .build();

    }
}
