package com.shopper.service;

import com.shopper.dao.Cart;
import com.shopper.dao.CartItem;
import com.shopper.dao.Product;
//import com.shopper.repository.CartItemRepository;
import com.shopper.dto.request.CartItemRequest;
import com.shopper.exceptions.CartItemNotFoundException;
import com.shopper.exceptions.InvalidProductException;
import com.shopper.exceptions.NotEnoughInventoryException;
import com.shopper.repository.CartItemRepository;
import com.shopper.repository.ProductRepository;
import com.shopper.dto.CartDTO;
import com.shopper.dto.CartItemDTO;
import com.shopper.repository.CartRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CartService {


    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    public CartDTO getItems(String username) {
        if(cartRepository.existsByUsername(username)) {
            return adaptToDTO(cartRepository.findByUsername(username));
        }
        return adaptToDTO(initializeCartForUser(username));
    }

    public List<CartDTO> getAllCarts() {
        return cartRepository.findAll().stream().map(this :: adaptToDTO).collect(Collectors.toList());
    }

    private Cart initializeCartForUser(String username) {
        if(cartRepository.existsByUsername(username)) {
            return cartRepository.findByUsername(username);
        }
        return cartRepository.save(Cart.builder().username(username).items(new HashSet<>()).build());
    }

    public CartDTO addItemToCart(CartItemDTO cartItemDTO, String username) {
        log.info("User = {} , requested to add item {} to cart", username, cartItemDTO);
        Cart cart = initializeCartForUser(username);
        Optional<Product> optionalProduct = productRepository.findById(cartItemDTO.getProductId());
        if(optionalProduct.isEmpty()) {
            log.info("Invalid cart item Product requested : {}", cartItemDTO);
            throw InvalidProductException.builder().productId(cartItemDTO.getProductId()).build();
        }
        Product product = optionalProduct.get();
        Integer inventoryQuantity = product.getInventory().getQuantity();
        Integer cartQuantity = cartItemDTO.getCartQuantity();

        if(inventoryQuantity < cartQuantity) {
            log.error("Not enough inventory for user {} to add item to cart {}",username, cartItemDTO);
            throw NotEnoughInventoryException.builder().requestedQuantity(cartQuantity)
                    .maximumQuantity(inventoryQuantity).build();
        }

        CartItem cartItem = cartItemRepository.findByCartAndProductId(cart, product.getProductId());
        if(cartItem == null) {
            cartItem = CartItem.builder().productId(product.getProductId()).cart(cart).build();
        }

        // set quantity and save item
        cartItem.setQuantity(cartQuantity);
        CartItem savedItem = cartItemRepository.save(cartItem);
        Optional<CartItem> optionalCartItem = cartItemRepository.findById(savedItem.getCartItemId());
        if(optionalCartItem.isEmpty()) {
            log.error("Cart was not saved successfully. Throwing...");
            throw new RuntimeException();
        }
        savedItem = optionalCartItem.get();
        // Add updated item to cart
        cart.getItems().add(savedItem);
        cartRepository.save(cart);

        Cart postSaveCart = cartRepository.findByUsername(username);
        log.info("Cart after saving {}  ", postSaveCart);
        return adaptToDTO(postSaveCart);

    }

    /**
     *  Method to delete a product from cart, for a user.
     * @param cartItemDTO
     * @param username
     * @throws InvalidProductException
     * @throws CartItemNotFoundException
     */
    public CartDTO deleteItemFromCart(@NonNull CartItemDTO cartItemDTO, @NonNull String username) {

        log.info("User = {} , requested to delete item {} to cart", username, cartItemDTO);
        Cart cart = initializeCartForUser(username);

        Optional<Product> optionalProduct = productRepository.findById(cartItemDTO.getProductId());
        if(optionalProduct.isEmpty()) {
            log.error("Item requested for deletion is invalid ProductId = {}", cartItemDTO.getProductId());
            throw InvalidProductException.builder().productId(cartItemDTO.getProductId()).build();
        }

        Long productId = optionalProduct.get().getProductId();
        CartItem cartItem = cartItemRepository.findByCartAndProductId(cart, productId);
        if(cartItem == null) {
            log.error("Item requested for deletion is absent in cart. Cart =  {}, ProductId = {}", cart, productId );
            throw CartItemNotFoundException.builder().productId(productId).build();
        }

        // Product is valid and present in cart.
        Set<CartItem> items = Optional.ofNullable(cart.getItems()).orElse(new HashSet<>()).stream()
                .filter(item-> item.getProductId() != productId).collect(Collectors.toSet());

        cart.setItems(items);
        // Remove From Cart
        Cart postDeleteCart = cartRepository.save(cart);

        // Remove as a cart item.
        cartItemRepository.delete(cartItem);
        return adaptToDTO(postDeleteCart);
    }



    /**
     * Utility method to transform objects
     * @param cart
     * @return
     */
    public CartDTO adaptToDTO(Cart cart) {
        if(Objects.isNull(cart)) {
            return CartDTO.builder().build();
        }
        List<CartItemDTO> items = new ArrayList<>();
        if((cart.getItems()!= null) && !cart.getItems().isEmpty()) {
            for (CartItem item : cart.getItems()) {
                Long productId = item.getProductId();
                Integer quantity = item.getQuantity();
                Product product = productRepository.findById(productId).get();
                items.add(CartItemDTO.builder().cartQuantity(quantity).productId(productId)
                        .productName(product.getProductName()).inventoryQuantity(product.getInventory().getQuantity()).build());
            }
        }
        return CartDTO.builder().username(cart.getUsername()).cartItems(items).build();
    }
}

