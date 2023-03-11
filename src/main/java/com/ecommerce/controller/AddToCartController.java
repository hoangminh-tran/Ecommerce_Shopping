package com.ecommerce.controller;

import com.ecommerce.dto.CateDTO;
import com.ecommerce.dto.OrderDTO;
import com.ecommerce.dto.OrderDetailDTO;
import com.ecommerce.model.*;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.OrdersRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.CategoryService;
import com.ecommerce.service.OrderDetailService;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class AddToCartController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    OrderDetailService orderDetailService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    OrderService orderService;
    @GetMapping("/AddToCart")
    public String AddToCart(Model model, HttpSession session)
    {
        List<Category> categoryList = categoryService.findAll();
        List<CateDTO> cate_name_list = new ArrayList<>();
        for (Category category : categoryList)
        {
            cate_name_list.add(new CateDTO(category.getName()));
        }

        Map<Product, Integer> map = (Map<Product, Integer>) session.getAttribute("Carts");

        if(map == null) map = new HashMap<>();

        double total = 0;
        for(Product p : map.keySet())
        {
            total += p.getPrice() * map.get(p);
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.ENGLISH));
        total = Double.parseDouble(decimalFormat.format(total));

        model.addAttribute("Total", total);
        model.addAttribute("Carts", map);
        model.addAttribute("title", "Add To Cart");
        model.addAttribute("categoryNameList", cate_name_list);
        return "AddToCart";
    }

    @GetMapping("/AddProductToCart/{id}")
    public String AddProductToCart(@PathVariable("id") Long id,
                                   RedirectAttributes attributes,
                                   Model model,
                                   HttpSession session)
    {
        Map<Product, Integer> map = (Map<Product, Integer>) session.getAttribute("Carts");

        if(map == null) map = new HashMap<>();

        Product product = productService.findProductById(id);
        map.put(product, map.getOrDefault(product, 0) + 1);
        session.setAttribute("Carts", map);
        attributes.addAttribute("cartSize", map.size());
        return "redirect:/homePage";
    }

    @PostMapping("/updateCart")
    public String update_quantity(@RequestParam("id") Long id,
                                  @RequestParam("number") int number,
                                  HttpSession session,
                                  Model model,
                                  RedirectAttributes attributes,
                                  @RequestParam(name = "update", required = false) String update,
                                  @RequestParam(name = "delete", required = false) String delete)
    {
        Map<Product, Integer> map = (Map<Product, Integer>) session.getAttribute("Carts");

        if(map == null) map = new HashMap<>();

        Product product = productService.findProductById(id);

        if(update != null) map.put(product, number);
        else if(delete != null) map.remove(product);

        double total = 0;
        for(Product p : map.keySet())
        {
            total += p.getPrice() * map.get(p);
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.ENGLISH));
        total = Double.parseDouble(decimalFormat.format(total));

        session.setAttribute("Carts", map);
        model.addAttribute("title", "Add To Cart");
        model.addAttribute("Total", total);
        model.addAttribute("Carts", map);
        return "AddToCart";
    }

    @PostMapping("/checkout")
    public String checkout(Model model,
                           HttpSession session,
                           RedirectAttributes attributes,
                           @RequestParam(name = "checkout", required = false) String checkout)
    {
        String email = (String) session.getAttribute("Email");

        if(email ==  null)
        {
            attributes.addFlashAttribute("failed", "Failed to Check out!!!");
            return "redirect:/AddToCart";
        }
        if(checkout != null && email != null)
        {
            Map<Product, Integer> map = (Map<Product, Integer>) session.getAttribute("Carts");

            if(map == null)
            {
                attributes.addFlashAttribute("failed", "Failed to Checkout because Empty Cart!!!!");
                return "redirect:/AddToCart";
            }

            Date date = new Date();

            User customer = userRepository.findUserByEmail(email);

            Orders orders = new Orders(date, date, 1, customer);

            ordersRepository.save(orders);

            for (Product product : map.keySet())
            {
                Long id = product.getId();
                OrderDetails orderDetails = new OrderDetails(map.get(product), orders, productService.findProductById(id));
                orderDetailService.save(orderDetails);
            }
            attributes.addFlashAttribute("success", "Check out successfully");
            session.removeAttribute("Carts");
            model.addAttribute("title", "Add To Cart");
        }
        return "redirect:/AddToCart";
    }
}
