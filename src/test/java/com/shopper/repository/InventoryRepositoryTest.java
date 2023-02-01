package com.shopper.repository;


import com.shopper.dao.Inventory;
import com.shopper.dao.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class InventoryRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void test_productRepository_findAll() {
        productRepository.saveAll(TestData.getDummyInventory()); // Inserts 2 products to inventory.
        List<Product> inventory = productRepository.findAll();
        Assertions.assertTrue(inventory.size() == 2);

    }


    static class TestData {
        public static List<Product> getDummyInventory() {
            Product p1 = Product.builder().productName("BRUSH").productPrice(10)
                    .inventory(Inventory.builder().quantity(10).build()).build();

            Product p2 = Product.builder().productName("BRUSH").productPrice(10)
                    .inventory(Inventory.builder().quantity(10).build()).build();

            return Arrays.asList(p1, p2);

        }
    }

}
