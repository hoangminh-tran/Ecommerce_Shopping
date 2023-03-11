package com.ecommerce.repository;

import com.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query
    (
            value = "select * from user where email = ?1", nativeQuery = true
    )
    User findUserByEmail(String email);

    @Query
    (
            value = "select * from user where email like %?1%", nativeQuery = true
    )
    User findUserByEmailLike(String email);

    @Query
    (
            value = "select * from user where email like %?1%", nativeQuery = true
    )
    List<User> findAllUserByEmailLike(String email);


    @Query
    (
            value = "select * from user where email = ?1 and password = ?2", nativeQuery = true
    )
    User loginUser(String email, String password);

    @Query
            (value = "select * from user where user_id = ?1", nativeQuery = true)
    User findUserById(Long user_id);
}
