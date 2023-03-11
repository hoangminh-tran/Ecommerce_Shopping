package com.ecommerce.repository;

import com.ecommerce.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    @Query
    (
            value = "select * from admin where email = ?1", nativeQuery = true
    )
    Admin findAdminByEmail(String email);


    @Query
    (
            value = "select * from admin where email = ?1 and password = ?2", nativeQuery = true
    )
    Admin findAdminByEmailandPassword(String email, String password);
}
