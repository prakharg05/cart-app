package com.shopper.service;


import com.shopper.dao.Cart;
import com.shopper.dao.CartItem;
import com.shopper.dao.Inventory;
import com.shopper.dao.Product;
import com.shopper.dto.CartDTO;
import com.shopper.dto.CartItemDTO;
import com.shopper.exceptions.InvalidProductException;
import com.shopper.exceptions.NotEnoughInventoryException;
import com.shopper.repository.CartItemRepository;
import com.shopper.repository.CartRepository;
import com.shopper.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTests {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartService cartService;

    @Test
    public void test_getItems_noCart() {
        doReturn(false)
                .when(cartRepository).existsByUsername(TestData.testUser);
        doReturn(TestData.getTestEmptyCart()).when(cartRepository).save(any());
        CartDTO cart = cartService.getItems(TestData.testUser);
        verify(cartRepository, times(1)).save(any());
        Assertions.assertTrue(cart.getCartItems() == null || cart.getCartItems().isEmpty()); // Empty cart is initialized
    }

    @Test
    public void test_getItems_preExistingCart() {
        doReturn(true)
                .when(cartRepository).existsByUsername(TestData.testUser);
        doReturn(TestData.getTestEmptyCart()).when(cartRepository).findByUsername(TestData.testUser);
        CartDTO cartDTO = cartService.getItems(TestData.testUser);
        verify(cartRepository, times(0)).save(any()); // Verify we are not creating a new cart, if it already exists
        Assertions.assertTrue(cartDTO.getCartItems().isEmpty());
    }


    @Test
    public void test_addItemsToCart_happy() {

        doReturn(true)
                .when(cartRepository).existsByUsername(TestData.testUser);
        Cart mockCart = TestData.getTestEmptyCart();
        doReturn(mockCart).when(cartRepository).findByUsername(TestData.testUser);

        doReturn(Optional.of(TestData.testProduct))
                .when(productRepository)
                .findById(TestData.getTestCartItemDTO().getProductId());
        doReturn(Optional.of(TestData.testCartItem)).when(cartItemRepository).findById(any());


        doReturn(null).when(cartItemRepository).findByCartAndProductId(any(), any());
        doReturn(TestData.testCartItem).when(cartItemRepository).save(any());
        doReturn(mockCart).when(cartRepository).save(mockCart);

        cartService.addItemToCart(TestData.getTestCartItemDTO(), TestData.testUser);

        verify(cartItemRepository, times(1)).save(any());
        verify(cartRepository, times(1)).save(any());
    }

    @Test
    public void test_addItemsToCart_InvalidProduct() {
        doReturn(Optional.empty()).when(productRepository).findById(any());

        Assertions.assertThrows(InvalidProductException.class, ()-> cartService.addItemToCart(TestData
                .getTestCartItemDTO(), TestData.testUser));
    }


    @Test
    public void test_addItemsToCart_massiveQuantity() {
        doReturn(Optional.of(TestData.testProduct))
                .when(productRepository)
                .findById(TestData.getTestMaxQuantityCartItemDTO().getProductId());

    Assertions.assertThrows(NotEnoughInventoryException.class, () -> cartService
            .addItemToCart(TestData.getTestMaxQuantityCartItemDTO(), TestData.testUser));
    }

    @Test
    public void test_deleteItemFromCart_happy() {
        doReturn(true)
                .when(cartRepository).existsByUsername(TestData.testUser);
        doReturn(Optional.of(TestData.testProduct)).when(productRepository).findById(any());
        Cart mockCart = TestData.getTestCart();
        CartItem cartItem = TestData.testCartItem;
        doReturn(mockCart).when(cartRepository).findByUsername(TestData.testUser);
        doReturn(cartItem).when(cartItemRepository).findByCartAndProductId(any(), any());

        doReturn(mockCart).when(cartRepository).save(any());


        cartService.deleteItemFromCart(TestData.getTestCartItemDTO(), TestData.testUser);

        verify(cartRepository, times(1)).save(any());
        verify(cartItemRepository, times(1)).delete(any());
        verify(cartItemRepository, times(1)).findByCartAndProductId(any(), any());
    }

    @Test
    public void test_deleteItemFromCart_InvalidProduct() {

        doReturn(Optional.empty()).when(productRepository).findById(any());

        Assertions.assertThrows(InvalidProductException.class, ()-> cartService.deleteItemFromCart(TestData
                .getTestCartItemDTO(), TestData.testUser));
    }

    static class TestData {
        private static String testUser = "prkharg";
        private static Product testProduct = Product.builder().productPrice(10).productId(12l)
                .productName("Brush")
                .inventory(Inventory.builder().quantity(10).build())
                .build();

        private static CartItem testCartItem = CartItem.builder().productId(testProduct.getProductId()).build();

        private static Cart testCart = Cart.builder().username(testUser).build();
        private static Cart getTestEmptyCart() {
            return Cart.builder().username(TestData.testUser).items(new HashSet<>()).build();
        }


        public static CartItemDTO getTestCartItemDTO() {
            return CartItemDTO.builder()
                    .cartQuantity(4)
                    .productId(testProduct.getProductId())
                    .productName(testProduct.getProductName())
                    .build();
        }

        public static CartItemDTO getTestMaxQuantityCartItemDTO() {
            return CartItemDTO.builder()
                    .cartQuantity(400)
                    .productId(testProduct.getProductId())
                    .productName(testProduct.getProductName())
                    .build();
        }

        public static Cart getTestCart() {
            Set<CartItem> cartItems = new HashSet<>();
            CartItem item = CartItem.builder().productId(testProduct.getProductId()).quantity(4).build();
            cartItems.add(item);
            return Cart.builder().username(testUser).items(cartItems).build();
        }


    }
}
