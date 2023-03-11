package com.ecommerce.controller;

import com.ecommerce.dto.CateDTO;
import com.ecommerce.model.Category;
import com.ecommerce.model.Orders;
import com.ecommerce.model.Product;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.OrdersRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.CategoryService;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    OrderService orderService;
    @GetMapping("/MyOrders")
    public String MyOrders(Model model, HttpSession session)
    {
        List<Category> categoryList = categoryService.findAll();
        List<CateDTO> cate_name_list = new ArrayList<>();
        for (Category category : categoryList)
        {
            cate_name_list.add(new CateDTO(category.getName()));
        }
        String email = (String) session.getAttribute("Email");
        if(email != null)
        {
            model.addAttribute("Email", email);
            session.setAttribute("Email", email);
            List<Orders> orders = orderService.findListOrdersByUsername(email);
            model.addAttribute("MyOrder", orders);
            model.addAttribute("size", orders.size());
        }
        else model.addAttribute("failed", "Please login to view Orders");
        model.addAttribute("categoryNameList", cate_name_list);
        model.addAttribute("title", "My Orders");
        return "OrderPages";
    }

    @GetMapping("/Orders/{status}")
    public String Orders(Model model,
                         HttpSession session,
                         @PathVariable("status") int status)
    {
        List<Category> categoryList = categoryService.findAll();
        List<CateDTO> cate_name_list = new ArrayList<>();
        for (Category category : categoryList)
        {
            cate_name_list.add(new CateDTO(category.getName()));
        }
        String email = (String) session.getAttribute("Email");
        if(email != null)
        {
            model.addAttribute("Email", email);
            session.setAttribute("Email", email);
            List<Orders> orders = orderService.findListOrdersByUsernameAndStatus(email, status);
            model.addAttribute("MyOrder", orders);
            model.addAttribute("size", orders.size());
        }
        else model.addAttribute("failed", "Please login to view Orders");
        model.addAttribute("categoryNameList", cate_name_list);
        model.addAttribute("title", "My Orders");
        return "OrderPages";
    }

    @PostMapping("/updateStatusOrder")
    public String Processed_Orders(@RequestParam(name = "cancel_order", required = false) String cancel_order,
                                   @RequestParam(name = "process_order", required = false)String process_order,
                                   @RequestParam(name = "id")Long id,
                                   Model model,
                                   HttpSession session,
                                   RedirectAttributes attributes)
    {
        List<Category> categoryList = categoryService.findAll();
        List<CateDTO> cate_name_list = new ArrayList<>();
        for (Category category : categoryList)
        {
            cate_name_list.add(new CateDTO(category.getName()));
        }
        String email = (String) session.getAttribute("Email");
        if(email != null)
        {
            model.addAttribute("Email", email);
            session.setAttribute("Email", email);
            if(cancel_order != null)
            {
                ordersRepository.updateStatusOrders(3, id);
            }
            else if(process_order != null)
            {
                ordersRepository.updateStatusOrders(2, id);
            }
        }
        else model.addAttribute("failed", "Please login to view Orders");
        model.addAttribute("categoryNameList", cate_name_list);
        model.addAttribute("title", "My Orders");
        return "redirect:/MyOrders";
    }


    @PostMapping("/filterDate")
    public String filterDate(@RequestParam(name = "Date_from", required = false) String Date_from,
                             @RequestParam(name = "Date_to", required = false) String Date_to,
                             Model model,
                             HttpSession session,
                             RedirectAttributes attributes) throws Exception
    {
        List<Category> categoryList = categoryService.findAll();
        List<CateDTO> cate_name_list = new ArrayList<>();
        for (Category category : categoryList)
        {
            cate_name_list.add(new CateDTO(category.getName()));
        }
        String email = (String) session.getAttribute("Email");
        if(email != null)
        {
            model.addAttribute("Email", email);
            session.setAttribute("Email", email);
            if(Date_from != null && Date_to != null)
            {
                System.out.println(Date_from + " " + Date_to);
                Date from = new SimpleDateFormat("yyyy-MM-dd").parse(Date_from);
                Date to = new SimpleDateFormat("yyyy-MM-dd").parse(Date_to);
                List<Orders>ordersList = orderService.findCustomerOrdersByDate(from, to, email);
                model.addAttribute("MyOrder", ordersList);
                model.addAttribute("size", ordersList.size());
            }
            else model.addAttribute("failed", "Invalid input type Date");
        }
        else model.addAttribute("failed", "Please login to view Orders");
        model.addAttribute("title", "My Orders");
        return "OrderPages";
    }
}
