package com.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "categories", uniqueConstraints = @UniqueConstraint(columnNames = "Cate_Name"))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Cate_Id")
    private Long id;

    @Column(name = "Cate_Name")
    private String name;

    private boolean is_deleted;

    private boolean is_activated;

    @OneToMany(mappedBy = "category")
    private List<Product>listProduct;

    public Category(String cate_Name) {
        name = cate_Name;
        is_activated = true;
        is_deleted = false;
    }

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
        is_activated = true;
        is_deleted = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category category)) return false;
        return is_deleted == category.is_deleted && is_activated == category.is_activated && Objects.equals(getId(), category.getId()) && Objects.equals(getName(), category.getName()) && Objects.equals(getListProduct(), category.getListProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), is_deleted, is_activated, getListProduct());
    }
}
