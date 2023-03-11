package com.ecommerce.controller;

import com.ecommerce.dto.CateDTO;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.CategoryService;
import com.ecommerce.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomePageController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/homePage")
    public String homePage(Model model, HttpSession session)
    {
        List<Category> categoryList = categoryService.findAll();
        List<CateDTO> cate_name_list = new ArrayList<>();
        for (Category category : categoryList)
        {
            cate_name_list.add(new CateDTO(category.getName()));
        }
        List<Product> productList = productService.findAll();
        String email = (String) session.getAttribute("Email");
        if(email != null)
        {
            model.addAttribute("Email", email);
            session.setAttribute("Email", email);
        }
        model.addAttribute("title", "Laptop Ecommerce");
        model.addAttribute("categoryNameList", cate_name_list);
        model.addAttribute("productList", productList);
        return "homePage";
    }

    @GetMapping("/searchByCategoryName/{name}")
    public String searchByCategoryName(@PathVariable("name") String name,
                                       Model model,
                                       RedirectAttributes attributes)
    {
        List<Category> categoryList = categoryService.findAll();
        List<CateDTO> cate_name_list = new ArrayList<>();
        for (Category category : categoryList)
        {
            cate_name_list.add(new CateDTO(category.getName()));
        }
        List<Product>productList = productService.findProductByCategoryName(name);
        model.addAttribute("title", "Laptop Ecommerce");
        model.addAttribute("categoryNameList", cate_name_list);
        model.addAttribute("productList", productList);
        return "homePage";
    }

    @PostMapping("/searchProductByProductName")
    public String searchProductByProductName(@RequestParam("name") String name,
                                       Model model)
    {
        List<Category> categoryList = categoryService.findAll();
        List<CateDTO> cate_name_list = new ArrayList<>();
        for (Category category : categoryList)
        {
            cate_name_list.add(new CateDTO(category.getName()));
        }
        List<Product>productList = productService.findProductByProductName(name);
        model.addAttribute("title", "Laptop Ecommerce");
        model.addAttribute("categoryNameList", cate_name_list);
        model.addAttribute("productList", productList);
        return "homePage";
    }

    @GetMapping("/addFavoriteProduct/{id}")
    public String addFavoriteProduct(@PathVariable("id") Long id,
                                     HttpSession session,
                                     Model model, RedirectAttributes attributes)
    {
        List<Category> categoryList = categoryService.findAll();
        List<CateDTO> cate_name_list = new ArrayList<>();
        for (Category category : categoryList)
        {
            cate_name_list.add(new CateDTO(category.getName()));
        }
        List<Product> productList = productService.findAll();
        List<Product>favoriteItem;
        if(session.getAttribute("favoriteItem") != null)
        {
           favoriteItem = (List<Product>) session.getAttribute("favoriteItem");
        }
        else favoriteItem =  new ArrayList<>();
        favoriteItem.add(productRepository.findById(id).get());
        attributes.addAttribute("Favorsize", favoriteItem.size());
        session.setAttribute("favoriteItem", favoriteItem);
        model.addAttribute("title", "Laptop Ecommerce");
        model.addAttribute("productList", productList);
        model.addAttribute("categoryNameList", cate_name_list);
        return "redirect:/homePage";
    }

    @GetMapping("/favoriteProduct")
    public String favoriteProductPage(HttpSession session,
                                     Model model)
    {
        List<Category> categoryList = categoryService.findAll();
        List<CateDTO> cate_name_list = new ArrayList<>();
        for (Category category : categoryList)
        {
            cate_name_list.add(new CateDTO(category.getName()));
        }
        List<Product>favoriteItem;
        if(session.getAttribute("favoriteItem") != null)
        {
            favoriteItem = (List<Product>) session.getAttribute("favoriteItem");
        }
        else favoriteItem =  new ArrayList<>();
        model.addAttribute("productList", favoriteItem);
        model.addAttribute("title", "Laptop Ecommerce");
        model.addAttribute("categoryNameList", cate_name_list);
        return "favoriteProduct";
    }

    @GetMapping("/AscendingByPrice")
    public String AscendingByPrice(Model model)
    {
        List<Category> categoryList = categoryService.findAll();
        List<CateDTO> cate_name_list = new ArrayList<>();
        for (Category category : categoryList)
        {
            cate_name_list.add(new CateDTO(category.getName()));
        }
        List<Product> sortedList = productRepository.findAll().stream().sorted(Comparator.comparing(Product::getPrice)).collect(Collectors.toList());
        model.addAttribute("title", "Laptop Ecommerce");
        model.addAttribute("categoryNameList", cate_name_list);
        model.addAttribute("productList", sortedList);
        return "homePage";
    }


    @GetMapping("/DescendingByPrice")
    public String DescendingByPrice(Model model)
    {
        List<Category> categoryList = categoryService.findAll();
        List<CateDTO> cate_name_list = new ArrayList<>();
        for (Category category : categoryList)
        {
            cate_name_list.add(new CateDTO(category.getName()));
        }
        List<Product>sortedList = productRepository.findAll().stream().sorted(Comparator.comparing(Product::getPrice).reversed()).collect(Collectors.toList());
        model.addAttribute("title", "Laptop Ecommerce");
        model.addAttribute("categoryNameList", cate_name_list);
        model.addAttribute("productList", sortedList);
        return "homePage";
    }


    @GetMapping("/AscendingAlphabet")
    public String AscendingAlphabet(Model model)
    {
        List<Category> categoryList = categoryService.findAll();
        List<CateDTO> cate_name_list = new ArrayList<>();
        for (Category category : categoryList)
        {
            cate_name_list.add(new CateDTO(category.getName()));
        }
        List<Product>sortedList = productRepository.findAll().stream().sorted(Comparator.comparing(Product::getName)).collect(Collectors.toList());
        model.addAttribute("title", "Laptop Ecommerce");
        model.addAttribute("categoryNameList", cate_name_list);
        model.addAttribute("productList", sortedList);
        return "homePage";
    }

    @GetMapping("/DescendingAlphabet")
    public String DescendingAlphabet(Model model)
    {
        List<Category> categoryList = categoryService.findAll();
        List<CateDTO> cate_name_list = new ArrayList<>();
        for (Category category : categoryList)
        {
            cate_name_list.add(new CateDTO(category.getName()));
        }
        List<Product>sortedList = productRepository.findAll().stream().sorted(Comparator.comparing(Product::getName).reversed()).collect(Collectors.toList());
        model.addAttribute("title", "Laptop Ecommerce");
        model.addAttribute("categoryNameList", cate_name_list);
        model.addAttribute("productList", sortedList);
        return "homePage";
    }

    @GetMapping("/logout")
    public String logout(RedirectAttributes attributes, HttpSession session)
    {
        session.invalidate();
        return "redirect:/homePage";
    }

}
