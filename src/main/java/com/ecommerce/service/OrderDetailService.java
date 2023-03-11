package com.ecommerce.service;

import com.ecommerce.dto.OrderDetailDTO;
import com.ecommerce.model.OrderDetails;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetails> findAll();

    OrderDetails save(OrderDetails dto);

    List<OrderDetailDTO> findListOrderDetailByOrderId(Long order_id);
}
