package com.ecommerce.controller;

import com.ecommerce.dto.CateDTO;
import com.ecommerce.dto.OrderDetailDTO;
import com.ecommerce.model.Category;
import com.ecommerce.model.OrderDetails;
import com.ecommerce.model.Orders;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class OrderDetailController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    OrderDetailService orderDetailService;
    @GetMapping("/viewOrderDetail/{id}")
    public String viewOrderDetail(Model model,
                                  HttpSession session,
                                  @PathVariable("id") Long id)
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
            List<OrderDetailDTO> orderDetails = orderDetailService.findListOrderDetailByOrderId(id);
            double total = 0;
            for (OrderDetailDTO orderDetailDTO : orderDetails)
            {
                total += orderDetailDTO.getPrice() * orderDetailDTO.getQuantity();
            }
            DecimalFormat decimalFormat = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.ENGLISH));
            total = Double.parseDouble(decimalFormat.format(total));
            model.addAttribute("Total", total);
            model.addAttribute("OrderDetail", orderDetails);
            model.addAttribute("size", orderDetails.size());
        }
        else model.addAttribute("failed", "Please login to view Order Detail");
        model.addAttribute("categoryNameList", cate_name_list);
        model.addAttribute("title", "Order Details");
        return "viewOrderDetail";
    }
}
