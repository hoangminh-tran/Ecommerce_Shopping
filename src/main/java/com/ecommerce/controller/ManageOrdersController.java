package com.ecommerce.controller;

import com.ecommerce.dto.CateDTO;
import com.ecommerce.dto.OrderDTO;
import com.ecommerce.model.Category;
import com.ecommerce.model.Orders;
import com.ecommerce.service.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
public class ManageOrdersController {

    @Autowired
    OrderService orderService;

    @GetMapping("/ManageOrders")
    public String ManageOrders(Model model)
    {
        List<OrderDTO> list = orderService.findAllOrderToManage();
        model.addAttribute("size", list.size());
        model.addAttribute("listOrder", list);
        model.addAttribute("title", "Manage Orders");
        return "ManageOrders";
    }

    @GetMapping("/search_order")
    public String search_category(@RequestParam(name = "name", required = false) String name,
                                  Model model)
    {
        List<OrderDTO> list = orderService.searchOrderByName(name);
        model.addAttribute("size", list.size());
        model.addAttribute("listOrder", list);
        model.addAttribute("title", "Manage Orders");
        return "ManageOrders";
    }

    @PostMapping("/searchDate")
    public String searchDate(@RequestParam(name = "Date_from", required = false) String Date_from,
                             @RequestParam(name = "Date_to", required = false) String Date_to,
                             Model model,
                             RedirectAttributes attributes) throws Exception
    {
        List<OrderDTO> list = null;
        if(Date_from != null && Date_to != null)
        {
            System.out.println(Date_from + " " + Date_to);
            Date from = new SimpleDateFormat("yyyy-MM-dd").parse(Date_from);
            Date to = new SimpleDateFormat("yyyy-MM-dd").parse(Date_to);
            list = orderService.searchOrdersByDate(from, to);
        }
        else model.addAttribute("failed", "Please login to view Orders");
        model.addAttribute("size", list.size());
        model.addAttribute("listOrder", list);
        model.addAttribute("title", "Manage Orders");
        return "ManageOrders";
    }
}
