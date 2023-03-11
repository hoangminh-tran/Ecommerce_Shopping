package com.ecommerce.dto;

import com.ecommerce.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;

    private String name;

    private double price;

    private int quantity;

    private byte[] data;

    private String description;

    private Category category;

    public ProductDTO(Long id, String name, double price, int quantity, byte[] data, String description, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.data = data;
        this.description = description;
        this.category = category;
    }

    private boolean is_deleted;

    private boolean is_activated;

}
