package com.ecommerce.controller;

import com.ecommerce.dto.OrderDTO;
import com.ecommerce.model.User;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ManageCustomerController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    @GetMapping("/ManageCustomer")
    public String ManageCustomer(Model model)
    {
        List<User> list = userRepository.findAll();
        model.addAttribute("size", list.size());
        model.addAttribute("listUser", list);
        model.addAttribute("title", "Manage Customer");
        model.addAttribute("customerNew", new User());
        return "ManageCustomer";
    }

    @GetMapping("/search_customerEmail")
    public String search_customerEmail(@RequestParam(name = "name", required = false) String name,
                                    Model model)
    {
        List<User> list = userRepository.findAllUserByEmailLike(name);
        model.addAttribute("size", list != null ? 1 : 0);
        model.addAttribute("listUser", list);
        model.addAttribute("title", "Manage Customer");
        return "ManageCustomer";
    }

    @PostMapping("/add_customer")
    public String add_customer(@ModelAttribute("customerNew")User customerNew,
                               Model model,
                               RedirectAttributes attributes)
    {
        try
        {
            User user = userRepository.findUserByEmail(customerNew.getEmail());
            if(user != null)
            {
                attributes.addFlashAttribute("failed", "Failed to add because duplicated Email !");
                return "redirect:/ManageCustomer";
            }
            if(!customerNew.getConfirmPassword().equals(customerNew.getPassword()))
            {
                attributes.addFlashAttribute("failed", "The password is not the same!!");
                return "redirect:/ManageCustomer";
            }
            userRepository.save(new User(customerNew.getFirstName(), customerNew.getLastName(), customerNew.getPassword(),
                    customerNew.getConfirmPassword(), customerNew.getEmail(), customerNew.getPhone(), roleRepository.findRoleByRole_Name("CUSTOMER")));
            attributes.addFlashAttribute("success", "Create new Customer Successfully!!!!");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            attributes.addFlashAttribute("errors", "The server has been errored. Please enter again");
        }
        return "redirect:/ManageCustomer";
    }

    @PostMapping("/update_customer")
    public String update_customer(RedirectAttributes attributes,
                                  Model model,
                                  @RequestParam(name = "user_id") Long user_id,
                                  @RequestParam(name = "firstname") String firstname,
                                  @RequestParam(name = "lastname") String lastname,
                                  @RequestParam(name = "password") String password,
                                  @RequestParam(name = "phone") String phone)
    {
        try
        {
            User user = userRepository.findUserById(user_id);
            user.setPhone(phone);
            user.setPassword(password);
            user.setConfirmPassword(password);
            user.setFirstName(firstname);
            user.setLastName(lastname);
            userRepository.save(user);
            attributes.addFlashAttribute("success", "Update Customer Information Successufully");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            attributes.addFlashAttribute("errors", "The server has been errored. Please enter again");
        }
        return "redirect:/ManageCustomer";
    }

    @GetMapping("/delete_customer/{id}")
    public String delete_customer(@PathVariable("id") Long id,
                                  RedirectAttributes attributes,
                                  Model model)
    {
        try
        {
            User user = userRepository.findUserById(id);
            user.setAccountStatus(false);
            userRepository.save(user);
            attributes.addFlashAttribute("success", "Delete Customer Successufully");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to Delete Customer");
        }
        return "redirect:/ManageCustomer";
    }

    @GetMapping("/enable_customer/{id}")
    public String enable_customer(@PathVariable("id") Long id,
                                  RedirectAttributes attributes,
                                  Model model)
    {
        try
        {
            User user = userRepository.findUserById(id);
            user.setAccountStatus(true);
            userRepository.save(user);
            attributes.addFlashAttribute("success", "Enable Customer Successufully");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            attributes.addFlashAttribute("failed", "Failed to Enable Customer");
        }
        return "redirect:/ManageCustomer";
    }
}
