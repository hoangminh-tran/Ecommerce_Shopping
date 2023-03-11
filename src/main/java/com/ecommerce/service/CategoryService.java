package com.ecommerce.service;

import com.ecommerce.model.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();

    Category save(Category category);

    Category update(Category category);

    void deleteById(Long id);

    Category findById(Long id);

    void enableById(Long id);

    Category findCategoryByName(String name);

    List<Category> findAllActivatedCategory();

    Page<Category> pageCategory(int pageNo);

    Page<Category> searchCategory(int pageNo, String name);
}
