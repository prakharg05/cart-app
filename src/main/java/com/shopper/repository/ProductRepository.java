package com.shopper.repository;

import com.shopper.dao.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository for CRUD operations on Product Entity
 */
public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findAll();
}