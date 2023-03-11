package com.ecommerce.repository;

import com.ecommerce.dto.OrderDTO;
import com.ecommerce.model.Orders;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    @Query(value = "select o.* from orders o join user u on o.user_id = u.user_id \n" +
            "where u.email = ?1", nativeQuery = true)
    List<Orders> findListOrdersByUsername(String name);

    @Query(value = "select o.* from orders o join user u on o.user_id = u.user_id \n" +
            "where u.email = ?1 and o.order_status = ?2", nativeQuery = true)
    List<Orders> findListOrdersByUsernameAndStatus(String name, int status);

    @Transactional
    @Modifying
    @Query(
            value = "update orders set order_status = ?1 where order_id = ?2", nativeQuery = true
    )
    void updateStatusOrders(int status, Long order_id);

    @Query
            (value = "select * from orders where order_id = ?1", nativeQuery = true)
    Orders findOrdersbyOrdersId(Long order_id);

    @Query(value = "select * from orders where ord_date >= ?1 and ord_date <= ?2", nativeQuery = true)
    List<Orders> findOrdersByDate(Date Date_from, Date Date_to);

    @Query(value = "select o.* from orders o join user u on o.user_id = u.user_id " +
            "            where ord_date >= ?1 and ord_date <= ?2 and u.email = ?3", nativeQuery = true)
    List<Orders> findCustomerOrdersByDate(Date Date_from, Date Date_to, String email);
}
