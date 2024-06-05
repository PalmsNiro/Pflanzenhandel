package com.example.pflanzenhandel.controller;

import com.example.pflanzenhandel.entity.Product;
import com.example.pflanzenhandel.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping("/product/{id}")
    public String getProductById(@PathVariable Integer id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "productDetails"; // Name of your product detail view
    }
}