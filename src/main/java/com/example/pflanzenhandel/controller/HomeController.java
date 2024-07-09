package com.example.pflanzenhandel.controller;

import com.example.pflanzenhandel.entity.Benutzer;
import com.example.pflanzenhandel.entity.Product;
import com.example.pflanzenhandel.service.ProductService;
import com.example.pflanzenhandel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;


    @Autowired
    private UserService userService;

    /**
     * Displays the home page of your application.
     *
     * @param model the Model containing all model attributes
     * @return the home page view
     */
    @GetMapping({"/", "/hauptmenu"})
    public String showProductsOnHome(@RequestParam(value = "query", required = false) String query, Model model, Principal principal) {
        List<Product> products;
        if (query != null && !query.isEmpty()) {
            products = productService.searchProducts(query);
        } else {
            products = productService.getAllProducts();
        }

        // Produkte sortieren: geboostete Produkte zuerst
        products = products.stream()
                .sorted(Comparator.comparing(Product::isBoosted).reversed())
                .collect(Collectors.toList());

        model.addAttribute("products", products);
        model.addAttribute("query", query);

        if (principal != null) {
            Benutzer user = userService.getUserByUsername(principal.getName());

            // Zufällige Quests zuweisen, wenn der Benutzer keine zugewiesenen Quests hat
            if (user.getUserQuests().isEmpty() && user.isNewQuestsAvailable()) {
                user = userService.assignRandomQuestsToUser(user.getId(), 5);
                user.setNewQuestsAvailable(false);
                userService.saveUser(user);
            }

            model.addAttribute("user", user);
        }

        return "home";
    }
}
