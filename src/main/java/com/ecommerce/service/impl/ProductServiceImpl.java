package com.ecommerce.service.impl;

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product save(ProductDTO dto, byte[] imageData) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.ENGLISH));
        double price = Double.parseDouble(decimalFormat.format(dto.getPrice()));

        Product product = new Product(dto.getName(), price, imageData, dto.getQuantity(),
                dto.getDescription(), dto.getCategory());
        return productRepository.save(product);
    }

    @Override
    public Product update(ProductDTO dto) {
        Product product = productRepository.findById(dto.getId()).orElse(null);
        DecimalFormat decimalFormat = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.ENGLISH));
        double price = Double.parseDouble(decimalFormat.format(dto.getPrice()));

        product.setData(dto.getData());
        product.setDescription(dto.getDescription());
        product.setName(dto.getName());
        product.setPrice(price);
        product.setCategory(dto.getCategory());
        product.setQuantity(dto.getQuantity());
        return productRepository.save(product);
    }

    @Override
    public Product delete(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        product.set_deleted(true);
        product.set_activated(false);
        return productRepository.save(product);
    }

    @Override
    public Product enable(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        product.set_deleted(false);
        product.set_activated(true);
        return productRepository.save(product);
    }

    private List<ProductDTO> tranferToProductDTO(List<Product> productList)
    {
        List<ProductDTO> list = new ArrayList<>();
        for(Product product : productList)
        {
            list.add(new ProductDTO(product.getId(), product.getName(), product.getPrice(), product.getQuantity(), product.getData(),
                    product.getDescription(), product.getCategory(), product.is_deleted(), product.is_activated()));
        }
        return list;
    }

    @Override
    public Page<Product> pageProducts(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        Page<Product>listPage = productRepository.findProduct(pageable);
        return listPage;
    }

    @Override
    public Page<Product> searchProducts(int pageNo, String name) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        Page<Product>listPage = productRepository.findProductByName(name, pageable);
        return listPage;
    }

    @Override
    public List<Product> findProductByCategoryName(String cate_name) {
        return productRepository.findProductByCategoryName(cate_name);
    }

    @Override
    public List<Product> findProductByProductName(String name) {
        return productRepository.findProductByProductName(name);
    }

    @Override
    public Product findProductById(Long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public List<Product> findProductByCategoryId(Long cate_id) {
        return productRepository.findProductByCategoryId(cate_id);
    }
}

