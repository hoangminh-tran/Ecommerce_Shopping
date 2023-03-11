package com.ecommerce.repository;

import com.ecommerce.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query
    (value = "select * from categories where cate_name like %?1%", nativeQuery = true)
    Category findCategoryName(String cate_Name);

    @Query(value = "select * from categories", nativeQuery = true)
    Page<Category> findCategoryPage(Pageable pageable);

    @Query(value = "select * from categories where cate_name like %?1%", nativeQuery = true)
    Page<Category> findCategoryPageByName(String name, Pageable pageable);

    @Query(value = "select * from categories where cate_name like %?1%", nativeQuery = true)
    List<Category> findListCategoryByName(String name);
}
