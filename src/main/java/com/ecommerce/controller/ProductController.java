package com.ecommerce.controller;

import com.ecommerce.dto.ProductDTO;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.CategoryService;
import com.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.*;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/ManageProduct")
    public String ManageProduct(Model model)
    {
        List<Product> productList = productService.findAll();
        model.addAttribute("title", "Manage Product");
        model.addAttribute("size", productList.size());
        model.addAttribute("products", productList);
        return "ManageProduct";
    }

    @GetMapping("/search_product/{pageNo}")
    public String search_product(@PathVariable("pageNo") int pageNo,
                                 Model model,
                                 @RequestParam("name") String name)
    {
        Page<Product>products = productService.searchProducts(pageNo, name);
        model.addAttribute("title", "Search Product");
        model.addAttribute("size", products.getSize());
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("products", products);
        return "search_product";
    }
    @GetMapping("/ManageProduct/{pageNo}")
    public String productsPage(@PathVariable("pageNo") int pageNo, Model model)
    {
        Page<Product>products = productService.pageProducts(pageNo);
        model.addAttribute("title", "Manage Product");
        model.addAttribute("size", products.getSize());
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("products", products);
        List<Category> categoryList = categoryService.findAllActivatedCategory();
        model.addAttribute("productNew", new ProductDTO());
        model.addAttribute("categories", categoryList);
        return "ManageProduct";
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) {
        Product image = productRepository.findById(id).orElse(null);

        if (image != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentLength(image.getData().length);
            return new ResponseEntity<>(image.getData(), headers, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add_product")
    public String add_product(@ModelAttribute("product")ProductDTO productDto,
                              @RequestParam("imageProduct")MultipartFile file,
                              RedirectAttributes attributes){
        try
        {
            byte[] imageData = file.getBytes();
            productService.save(productDto, imageData);
            attributes.addFlashAttribute("success", "Add successfully!");
        }
        catch(DataIntegrityViolationException e)
        {
            attributes.addFlashAttribute("failed", "Failed to add!");
            e.printStackTrace();
        }
        catch (Exception e){
            attributes.addFlashAttribute("error", "Server error!");
            e.printStackTrace();
        }
        return "redirect:/ManageProduct/0";
    }

    @PostMapping("/update_product")
    public String update_product(RedirectAttributes attributes,
                                 @RequestParam("imageProduct")MultipartFile file,
                                 @RequestParam("id")Long id,
                                 @RequestParam("name")String name,
                                 @RequestParam("price")String price,
                                 @RequestParam("quantity")int quantity,
                                 @RequestParam("description")String description,
                                 @RequestParam("category")Category category)
    {
        try
        {
            byte[] imageData = file.getBytes();
            ProductDTO dto = new ProductDTO(id, name, Double.parseDouble(price), quantity, imageData, description, category);
            productService.update(dto);
            attributes.addFlashAttribute("success",  "Update Product Successfully");
        }
        catch(DataIntegrityViolationException e)
        {
            attributes.addFlashAttribute("failed", "Failed to update!");
            e.printStackTrace();
        }
        catch (Exception e){
            attributes.addFlashAttribute("error", "Server error!");
            e.printStackTrace();
        }
        return "redirect:/ManageProduct/0";
    }

    @GetMapping("/delete_product/{id}")
    public String delete_product(@PathVariable("id") Long id,
                                 RedirectAttributes attributes)
    {
        try
        {
            productService.delete(id);
            attributes.addFlashAttribute("success",  "Delete Product Successfully");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to delete!");
        }
        return "redirect:/ManageProduct";
    }

    @GetMapping("/enable_product/{id}")
    public String enable_product(@PathVariable("id") Long id,
                                 RedirectAttributes attributes)
    {
        try
        {
            productService.enable(id);
            attributes.addFlashAttribute("success",  "Enable Product Successfully");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to enable!");
        }
        return "redirect:/ManageProduct";
    }
}
