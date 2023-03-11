package com.ecommerce.controller;

import com.ecommerce.model.Admin;
import com.ecommerce.repository.AdminRepository;
import com.ecommerce.repository.RoleRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller()
public class AdminLoginController {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/registerAdmin")
    public String register(Model model)
    {
        model.addAttribute("title", "Register");
        model.addAttribute("adminDTO", new Admin());
        return "registerAdmin";
    }
    @PostMapping("/register_admin")
    public String register_admin(@Valid @ModelAttribute("adminDTO") Admin adminDTO,
                                 BindingResult result,
                                 Model model)
    {
        try
        {
            if(result.hasErrors())
            {
                model.addAttribute("adminDTO", adminDTO);
                result.toString();
                return "registerAdmin";
            }
            Admin admin = adminRepository.findAdminByEmail(adminDTO.getEmail());
            if(admin != null)
            {
                model.addAttribute("adminDTO", adminDTO);
                System.out.println("Admin Not Null");
                model.addAttribute("emailError", "Your Email has been registered!!!!!");
                return "registerAdmin";
            }
            if(!adminDTO.getPassword().equals(adminDTO.getRepeatPassword()))
            {
                model.addAttribute("adminDTO", adminDTO);
                System.out.println("Password is not the same");
                model.addAttribute("passwordError", "Your Password is not the same!!!!!");
                return "registerAdmin";
            }
            else {
                model.addAttribute("adminDTO", adminDTO);
                System.out.println("Register Successfully");
                model.addAttribute("success", "Register Successfully!!!!!");
                Admin add_admin = new Admin(adminDTO.getFirstName(), adminDTO.getLastName(), adminDTO.getPassword(),
                        adminDTO.getEmail(), adminDTO.getRepeatPassword(), true, roleRepository.findById(Long.parseLong(1 + "")).get());
                adminRepository.save(add_admin);
                return "registerAdmin";
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            model.addAttribute("errors", "The server has been wrong");
        }
        return "registerAdmin";
    }

    @PostMapping("/login_Admin")
    public String loginAdmin(Model model,
                        @RequestParam("email") String email,
                        @RequestParam("password") String password)
    {

        Admin admin = adminRepository.findAdminByEmailandPassword(email, password);
        if(admin != null)
        {
            return "redirect:/AdminHomePage";
        }
        model.addAttribute("errors", "Invalid email or password!!!!!");
        return "loginAdmin";
    }

    @GetMapping("/loginAdmin")
    public String login(Model model)
    {
        model.addAttribute("title", "Login Admin");
        return "loginAdmin";
    }

    @GetMapping("/forgot-passwordAdmin")
    public String forgot_password(Model model)
    {
        model.addAttribute("title", "Forgot Password Admin");
        return "forgot-passwordAdmin";
    }

    @GetMapping("/AdminHomePage")
    public String index(Model model)
    {
        model.addAttribute("title", "Admin Home Page");
        return "AdminHomePage";
    }
}
