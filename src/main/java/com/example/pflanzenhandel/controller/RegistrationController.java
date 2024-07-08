package com.example.pflanzenhandel.controller;

import com.example.pflanzenhandel.entity.Benutzer;
import com.example.pflanzenhandel.entity.Benutzer;
import com.example.pflanzenhandel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String registerUser(Benutzer user, String repeatPassword, String firstName, String lastName, String email, Model model, RedirectAttributes redirectAttributes) {
        if (!user.getPassword().equals(repeatPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "register";
        }
        if (!userService.isUsernameUnique(user.getUsername())) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        userService.saveUser(user);
        redirectAttributes.addFlashAttribute("successMessage", "Account successfully created. You can now log in.");
        return "redirect:/login";
    }
}
