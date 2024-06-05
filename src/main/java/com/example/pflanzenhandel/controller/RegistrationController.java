package com.example.pflanzenhandel.controller;

import com.example.pflanzenhandel.entity.Benutzer;
import com.example.pflanzenhandel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("benutzer", new Benutzer());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(Benutzer benutzer, String repeatPassword, Model model) {
        if (!benutzer.getPassword().equals(repeatPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "register";
        }
        userService.saveUser(benutzer);
        return "redirect:/login";
    }
}