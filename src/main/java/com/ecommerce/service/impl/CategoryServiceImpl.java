package com.ecommerce.service.impl;

import com.ecommerce.model.Category;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(new Category(category.getName()));
    }

    @Override
    public Category update(Category dto) {
        Category category =  null;
        try
        {
            category = categoryRepository.findById(dto.getId()).get();
            category.setName(dto.getName());
            category.set_activated(dto.is_activated());
            category.set_deleted(dto.is_deleted());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).get();
    }

    @Override
    public void deleteById(Long id) {
        Category category = categoryRepository.findById(id).get();
        category.set_deleted(true);
        category.set_activated(false);
        categoryRepository.save(category);
    }

    @Override
    public void enableById(Long id) {
        Category category = categoryRepository.findById(id).get();
        category.set_deleted(false);
        category.set_activated(true);
        categoryRepository.save(category);
    }

    @Override
    public Category findCategoryByName(String name) {
        return categoryRepository.findCategoryName(name);
    }

    @Override
    public List<Category> findAllActivatedCategory() {
        List<Category> list = new ArrayList<>();
        List<Category> categories = categoryRepository.findAll();
        for(Category cate : categories)
        {
            if(cate.is_activated() == true)
            {
                list.add(cate);
            }
        }
        return list;
    }

    @Override
    public Page<Category> pageCategory(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 6);
        Page<Category> categoryPage = categoryRepository.findCategoryPage(pageable);
        return categoryPage;
    }

    @Override
    public Page<Category> searchCategory(int pageNo, String name) {
        Pageable pageable = PageRequest.of(pageNo, 6);
        Page<Category> categoryPage = categoryRepository.findCategoryPageByName(name, pageable);
        return categoryPage;
    }
}
