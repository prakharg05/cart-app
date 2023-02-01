package com.shopper.repository;

import com.shopper.dao.Cart;
import com.shopper.dao.CartItem;
import com.shopper.dao.Inventory;
import org.springframework.data.repository.CrudRepository;

public interface CartItemRepository extends CrudRepository<CartItem, Long> {
    CartItem findByCartAndProductId(Cart cart, Long productId);
}
