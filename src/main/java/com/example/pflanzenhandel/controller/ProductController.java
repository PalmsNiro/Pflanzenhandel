package com.example.pflanzenhandel.controller;

import com.example.pflanzenhandel.entity.Benutzer;
import com.example.pflanzenhandel.entity.Product;
import com.example.pflanzenhandel.service.ProductService;
import com.example.pflanzenhandel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;


    @GetMapping("/product/{id}")
    public String getProductById(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        Benutzer verkaufer = product.getVerkaufer();
        Benutzer currentUser = userService.getCurrentUser();
        model.addAttribute("verkaufer", verkaufer);
        model.addAttribute("product", product);
        model.addAttribute("currentUser", currentUser);
        return "productDetails";
    }

    @GetMapping("/product/new")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "addProduct";
    }

    @PostMapping("/product/new")
    public String addProduct(@ModelAttribute Product product, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Benutzer verkaufer = userService.getCurrentUser();
        product.setVerkaufer(verkaufer);
        String errorMessage = validateProduct(product);
        if (errorMessage != null) {
            model.addAttribute("error", errorMessage);
            return "addProduct";
        }
        productService.saveProduct(product);
        return "redirect:/product/" + product.getId();
    }

    @GetMapping("/product/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        Benutzer currentUser = userService.getCurrentUser();

        if (!product.getVerkaufer().equals(currentUser)) {
            return "redirect:/product/" + id;
        }
        model.addAttribute("product", product);

        return "editProduct";
    }

    @PostMapping("/product/edit")
    public String editProduct(@ModelAttribute Product product, Model model) {

        Benutzer userCurrent = userService.getCurrentUser();
        if (product.getVerkaufer() == null || !product.getVerkaufer().equals(userCurrent)) {
            return "redirect:/product/" + product.getId();
        }
        String errorMessage = validateProduct(product);
        if (errorMessage != null) {
            model.addAttribute("error", errorMessage);
            return "editProduct";
        }
        System.out.println("Produkt vor dem Update im Controller: " + product);

        productService.updateProduct(product);

        // Debug-Ausgabe
        Product updatedProduct = productService.getProductById(product.getId());
        System.out.println("Produkt nach dem Update im Controller: " + updatedProduct);
        return "redirect:/product/" + product.getId();
    }


    private String validateProduct(Product product) {
        if (product.getName() == null || product.getName().isEmpty()) {
            return "Name is mandatory";
        }
        if (product.getPrice() <= 0) {
            return "Price must be greater than or equal to 0";
        }
        if (product.getHeight() <= 0) {
            return "Height is mandatory and must be greater than or equal to 0";
        }
        if (product.getShippingCosts() <= 0) {
            return "Shopping costs must be greater than or equal to 0";
        }
        return null;
    }
}
