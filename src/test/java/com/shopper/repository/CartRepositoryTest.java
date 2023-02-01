package com.shopper.repository;

import com.shopper.dao.Cart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CartRepositoryTest {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;


    @Test
    public void test_cartRepository_findByUsername_notExists() {
        Cart cart = cartRepository.findByUsername(TestData.testUser);
        Assertions.assertTrue(cart == null);
    }


    @Test
    public void test_cartRepository_findByUsername_exists() {
        cartRepository.save(TestData.getTestCart());
        Cart cart = cartRepository.findByUsername(TestData.testUser);

        Assertions.assertTrue(cart != null);
    }

    @Test
    public void test_cartItemRepository_findByCartAndProductId_exits() {

    }


    static class TestData {
        public static String testUser = "prkharg";

        public static Cart getTestCart() {
            return Cart.builder().username(testUser).build();
        }
    }
}
