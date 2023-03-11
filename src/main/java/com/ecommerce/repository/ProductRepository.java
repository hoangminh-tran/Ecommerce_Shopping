package com.ecommerce.repository;

import com.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "select * from product", nativeQuery = true)
    Page<Product> findProduct(Pageable pageable);

    @Query(value = "select * from product where name like %?1%", nativeQuery = true)
    Page<Product> findProductByName(String name, Pageable pageable);

    @Query(value = "select p.* from product p join categories c " +
            "on p.cate_id = c.cate_id where c.cate_name = ?1", nativeQuery = true)
    List<Product> findProductByCategoryName(String cate_name);
    @Query(value = "select * from product where name like %?1%", nativeQuery = true)
    List<Product> findProductByProductName(String name);

    @Query(value = "select p.* from product p " +
            " where p.cate_id = ?1", nativeQuery = true)
    List<Product> findProductByCategoryId(Long cate_id);


}
