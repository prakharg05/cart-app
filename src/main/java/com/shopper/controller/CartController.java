package com.shopper.controller;

import com.shopper.dto.CartDTO;
import com.shopper.dto.CartItemDTO;
import com.shopper.dto.request.CartItemRequest;
import com.shopper.dto.response.CartItemResponse;
import com.shopper.dto.response.CartResponse;
import com.shopper.exceptions.NotEnoughInventoryException;
import com.shopper.service.CartService;
import com.shopper.utils.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    /**
     * Fetch Cart for the logged in User
     * @param user
     * @return
     */
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public CartResponse getCartByUser(Principal user) {
        String username = user.getName();

        return adaptToResponse(cartService.getItems(username));
    }

    /**
     * Add item to cart for logged in user
     * @param item
     * @param user
     * @return
     */
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public CartResponse addItemToCart(@RequestBody CartItemRequest item, Principal user) {
        ValidationUtils.validateCartItemRequest(item);
        CartItemDTO cartItemDTO = CartItemDTO.builder()
                .productName(item.getProductName())
                .productId(item.getProductId())
                .cartQuantity(item.getCartQuantity()).build();

        return adaptToResponse(cartService.addItemToCart(cartItemDTO, user.getName()));
    }

    /**
     * Delete item from cart of the logged in user.
     * @param item
     * @param user
     * @return
     */
    @PostMapping("/delete")
    @PreAuthorize("hasRole('USER')")
    public CartResponse deleteItemFromCart(@RequestBody CartItemRequest item, Principal user) {
        ValidationUtils.validateCartItemRequest(item);
        CartItemDTO cartItemDTO = CartItemDTO.builder()
                .productName(item.getProductName())
                .productId(item.getProductId())
                .cartQuantity(item.getCartQuantity()).build();
        return adaptToResponse(cartService.deleteItemFromCart(cartItemDTO, user.getName()));

    }

    /**
     * Fetch all carts.
     * @return
     */
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<CartResponse> getAllCarts() {
        return cartService.getAllCarts().stream().map(this::adaptToResponse).collect(Collectors.toList());
    }
    private CartResponse adaptToResponse(CartDTO cartDTO) {

        List<CartItemResponse> cartItemResponses = new ArrayList<>();
        cartItemResponses.addAll(cartDTO.getCartItems()
                .stream().map(this::adaptToResponse)
                .collect(Collectors.toList()));
        return CartResponse.builder().username(cartDTO.getUsername()).cartItems(cartItemResponses).build();
    }

    private CartItemResponse adaptToResponse(CartItemDTO cartItemDTO) {
        return CartItemResponse.builder().cartQuantity(cartItemDTO.getCartQuantity())
                .productId(cartItemDTO.getProductId())
                .productName(cartItemDTO.getProductName())
                .productId(cartItemDTO.getProductId())
                .fulfillable(cartItemDTO.getInventoryQuantity() > cartItemDTO.getCartQuantity())
                .build();
    }

}
