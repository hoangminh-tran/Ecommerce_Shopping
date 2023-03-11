 package com.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

 @Entity
@Table(name = "product", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Product_Id")
    private Long id;

    private String name;

    private double price;

    @Lob
    @Column(columnDefinition = "mediumblob")
    private byte[] data;

    private int quantity;

    private String description;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "Cate_Id", referencedColumnName = "Cate_Id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<OrderDetails>orderDetailsList;

    private boolean is_deleted;

    private boolean is_activated;

    public Product(String name, double price, byte[] data, int quantity, String description, Category category) {
        this.name = name;
        this.price = price;
        this.data = data;
        this.quantity = quantity;
        this.description = description;
        this.category = category;
        is_activated = true;
        is_deleted = false;
    }

     @Override
     public boolean equals(Object o) {
         if (this == o) return true;
         if (!(o instanceof Product product)) return false;
         Product product1 = (Product) o;
         return Objects.equals(id, product1.id);
     }

     @Override
     public int hashCode() {
         return Objects.hash(id);
     }
 }
