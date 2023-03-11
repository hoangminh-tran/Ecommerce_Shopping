package com.ecommerce.repository;

import com.ecommerce.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {
    @Query
            (value = "select * from roles where role_name = ?1", nativeQuery = true)
    Roles findRoleByRole_Name(String name);
}
