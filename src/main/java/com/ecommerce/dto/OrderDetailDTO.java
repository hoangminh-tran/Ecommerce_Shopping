package com.ecommerce.dto;

import com.ecommerce.model.Orders;
import com.ecommerce.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    private Long order_id;

    private Long product_id;

    private String product_name;

    private double price;

    private int quantity;
}
