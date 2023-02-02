package com.shopper.repository;

import com.shopper.dao.Inventory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for CRUD operations on Inventory Entity
 */
@Repository
public interface InventoryRepository extends CrudRepository<Inventory, Long> {
    List<Inventory> findAll();
}
