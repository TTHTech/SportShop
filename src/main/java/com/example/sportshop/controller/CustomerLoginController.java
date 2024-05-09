package com.example.sportshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;  // Đã sửa
import org.springframework.web.bind.annotation.GetMapping;

import com.example.sportshop.model.Customer;
import com.example.sportshop.repository.CustomerRepository;

import java.security.Principal;

@Controller
public class CustomerLoginController {
    @Autowired
    private CustomerRepository userRepository;

    @GetMapping("/homepage")
    public String myPage(Model model, Principal principal) {
        String email = principal.getName();
        Customer user = userRepository.findByEmail(email);
        model.addAttribute("name", user.getName());

        return "home/index";
    }
}
