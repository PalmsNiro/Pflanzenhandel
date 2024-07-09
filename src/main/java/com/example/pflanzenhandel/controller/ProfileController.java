package com.example.pflanzenhandel.controller;

import com.example.pflanzenhandel.entity.Benutzer;
import com.example.pflanzenhandel.entity.Product;
import com.example.pflanzenhandel.service.ProductService;
import com.example.pflanzenhandel.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    @GetMapping("/profile")
    public String showUserProfile(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Benutzer user = userService.getUserByUsername(username);
        model.addAttribute("user", user);

        List<Product> userProducts = productService.getAllProducts().stream()
                .filter(product -> product.getVerkaufer().getId().equals(user.getId()))
                .toList();
        model.addAttribute("userProducts", userProducts);

        return "profile";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }
    
}
