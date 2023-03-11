package com.ecommerce.repository;

import com.ecommerce.dto.OrderDetailDTO;
import com.ecommerce.model.OrderDetails;
import com.ecommerce.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
    @Query(value = "select od.* from order_details od join orders o on o.order_id = od.order_id\n" +
            "where od.order_id = ?1", nativeQuery = true)
    List<OrderDetails> findListOrderDetailByOrderId(Long order_id);
}
