package com.carService.controller;

import com.carService.model.User;
import com.carService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String showHome() {

        return "index";
    }

    @GetMapping("/login")
    public String showLogin() {

        return "login";
    }

    @GetMapping("/register")
    public String showRegister() {

        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        User userToRegister = userService.registerUser(user);
        if (userToRegister == null)
        {
            //model.addAttribute("error",true);
            return "/register";
        }
        return "redirect:/login";
    }
}
