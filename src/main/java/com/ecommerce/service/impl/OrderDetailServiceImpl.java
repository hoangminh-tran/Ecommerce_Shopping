package com.ecommerce.service.impl;

import com.ecommerce.dto.OrderDetailDTO;
import com.ecommerce.model.OrderDetails;
import com.ecommerce.model.Orders;
import com.ecommerce.model.Product;
import com.ecommerce.repository.OrderDetailsRepository;
import com.ecommerce.repository.OrdersRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.OrderDetailService;
import com.ecommerce.service.OrderService;
import jakarta.persistence.criteria.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    OrderDetailsRepository orderDetailsRepository;

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<OrderDetails> findAll() {
        return orderDetailsRepository.findAll();
    }

    @Override
    public OrderDetails save(OrderDetails dto) {
        return orderDetailsRepository.save(new OrderDetails(dto.getQuantity(), dto.getOrders(), dto.getProduct()));
    }

    @Override
    public List<OrderDetailDTO> findListOrderDetailByOrderId(Long order_id) {
        List<OrderDetails>orderDetails = orderDetailsRepository.findListOrderDetailByOrderId(order_id);
        List<OrderDetailDTO> list = new ArrayList<>();
        for (OrderDetails od : orderDetails)
        {
            Orders order = ordersRepository.findById(od.getOrders().getOrder_Id()).get();
            Product product = productRepository.findById(od.getProduct().getId()).get();
            list.add(new OrderDetailDTO(order.getOrder_Id(), product.getId(),
                    product.getName(), product.getPrice(), od.getQuantity()));
        }
        return list;
    }
}
