package com.ecommerce.controller;

import com.ecommerce.model.User;
import com.ecommerce.repository.RoleRepository;
import com.ecommerce.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CustomerLoginController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    @GetMapping("/LoginCustomer")
    public String LoginCustomer()
    {
        return "LoginCustomer";
    }

    @GetMapping("/registrationCustomer")
    public String registrationCustomer(Model model)
    {
        model.addAttribute("customerDTO", new User());
        return "registrationCustomer";
    }

    @PostMapping("/register_customer")
    public String register_customer(@Valid @ModelAttribute("customerDTO") User customerDTO,
                                    BindingResult result,
                                    Model model)
    {
        try
        {
            if(result.hasErrors())
            {
                model.addAttribute("customerDTO", customerDTO);
                result.toString();
                return "registrationCustomer";
            }
            User customer = userRepository.findUserByEmail(customerDTO.getEmail());
            if(customer != null)
            {
                model.addAttribute("errorEmail", "Your email has been registered already!!!!!");
                model.addAttribute("customerDTO", customerDTO);
                return "registrationCustomer";
            }
            if(!customerDTO.getPassword().equals(customerDTO.getConfirmPassword()))
            {
                model.addAttribute("errorPassword", "Your password is not the same!!!!!");
                model.addAttribute("customerDTO", customerDTO);
                return "registrationCustomer";
            }
            else {
                model.addAttribute("success", "Register Successfully");
                model.addAttribute("customerDTO", customerDTO);
                userRepository.save(new User(customerDTO.getFirstName(), customerDTO.getLastName(), customerDTO.getPassword(), customerDTO.getConfirmPassword(),
                        customerDTO.getEmail(),
                        customerDTO.getPhone(), true, roleRepository.findById(Long.parseLong(2 + "")).get()));
                return "registrationCustomer";
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
            model.addAttribute("errors", "The server has been errored. Please enter again");
        }
        return "registrationCustomer";
    }

    @PostMapping("/login_customer")
    public String login_customer(@RequestParam("email") String email,
                                 @RequestParam("password") String password,
                                 RedirectAttributes attributes,
                                 Model model,
                                 HttpSession session)
    {
        User customer = userRepository.loginUser(email, password);
        if(customer == null){
            model.addAttribute("errors", "Invalid email or password!!!!!");
            return "LoginCustomer";
        }
        else {
            session.setAttribute("Email", customer.getEmail());
            model.addAttribute("success", "Login Successfully!!!!!");
            return "redirect:/homePage";
        }
    }
}
