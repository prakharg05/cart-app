package com.shopper.repository;

import com.shopper.dao.Inventory;
import com.shopper.dao.UserAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository to fetch user accounts.
 */
public interface UserRepository extends CrudRepository<UserAccount, Long> {
    UserAccount findByUsername(String username);
    boolean existsByUsername(String username);

    List<UserAccount> findAll();
}
