package com.shopper.repository;

import com.shopper.dao.Inventory;
import com.shopper.dao.UserAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<UserAccount, Long> {
    UserAccount findByUsername(String username);

    List<UserAccount> findAll();
}
