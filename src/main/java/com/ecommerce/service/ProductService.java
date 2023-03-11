package com.ecommerce.service;

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<Product> findAll();

    Product save(ProductDTO dto, byte[] imageData);

    Product update(ProductDTO dto);

    Product delete(Long id);

    Product enable(Long id);

    Page<Product> pageProducts(int pageNo);

    List<Product>findProductByCategoryName(String cate_name);

    List<Product> findProductByProductName(String name);

    Page<Product> searchProducts(int pageNo, String name);

    Product findProductById(Long id);

    List<Product> findProductByCategoryId(Long cate_id);
}
