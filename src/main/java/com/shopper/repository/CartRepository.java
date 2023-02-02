package com.shopper.repository;

import com.shopper.dao.Cart;
import com.shopper.dao.Inventory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository for CRUD operations on Cart Entity
 */
public interface CartRepository  extends CrudRepository<Cart, Long> {

    public Cart findByUsername(String username);

    public boolean existsByUsername(String username);

    public List<Cart> findAll();
}
