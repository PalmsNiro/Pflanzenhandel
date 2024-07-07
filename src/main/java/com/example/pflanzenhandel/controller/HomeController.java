package com.example.pflanzenhandel.controller;

import com.example.pflanzenhandel.entity.Product;
import com.example.pflanzenhandel.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @GetMapping({"/", "/hauptmenu"})
    public String showProductsOnHome(@RequestParam(value = "query", required = false) String query, Model model) {
        List<Product> products;
        if (query != null && !query.isEmpty()) {
            products = productService.searchProducts(query);
        } else {
            products = productService.getAllProducts();
        }
        model.addAttribute("products", products);
        model.addAttribute("query", query);
        return "home";
    }
}
