package com.ecommerce.service;

import com.ecommerce.dto.OrderDTO;
import com.ecommerce.model.Orders;
import jakarta.persistence.criteria.Order;

import java.util.Date;
import java.util.List;

public interface OrderService {
   List<Orders> findAll();

    Orders save(Orders dto);

    List<Orders> findListOrdersByUsername(String username);

    List<Orders> findListOrdersByUsernameAndStatus(String name, int status);

    void updateStatusOrders(int status, Long order_id);

    List<Orders> findOrdersByDate(Date Date_from, Date Date_to);

    List<OrderDTO> findAllOrderToManage();

    List<OrderDTO> searchOrderByName(String name);

    List<OrderDTO> searchOrdersByDate(Date Date_from, Date Date_to);

 List<Orders> findCustomerOrdersByDate(Date Date_from, Date Date_to, String email);

}
