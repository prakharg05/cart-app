package com.shopper.repository;

import com.shopper.dao.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository to fetch valid roles.
 */
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findRoleByName(String name);
}