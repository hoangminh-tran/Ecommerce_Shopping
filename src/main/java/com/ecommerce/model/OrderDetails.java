package com.ecommerce.model;

import com.ecommerce.dto.OrderDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "OrderDetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderDetails_Id")
    private Long OrderDetails_Id;

    private int quantity;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "Order_Id", referencedColumnName = "Order_Id")
    private Orders orders;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "Product_Id", referencedColumnName = "Product_Id")
    private Product product;

    public OrderDetails(int quantity, Orders orders, Product product) {
        this.quantity = quantity;
        this.orders = orders;
        this.product = product;
    }
}
