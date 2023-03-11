package com.ecommerce.controller;

import com.ecommerce.model.Product;
import com.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ViewDetailController {
    @Autowired
    ProductService productService;
    @GetMapping("/viewDetail/{id}")
    public String viewDetail(@PathVariable("id") Long id,
                             RedirectAttributes attributes,
                             Model model)
    {
        model.addAttribute("title", "View Detail");
        Product product = productService.findProductById(id);
        List<Product> moreProduct = productService.findProductByCategoryId(product.getCategory().getId());
        model.addAttribute("ProductInfo", product);
        model.addAttribute("moreProduct", moreProduct);
        return "detail";
    }
}
