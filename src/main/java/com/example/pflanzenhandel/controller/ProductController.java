package com.example.pflanzenhandel.controller;

import com.example.pflanzenhandel.entity.Benutzer;
import com.example.pflanzenhandel.entity.Product;
import com.example.pflanzenhandel.service.ProductService;
import com.example.pflanzenhandel.service.StorageService;
import com.example.pflanzenhandel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private StorageService storageService;

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
    public String addProduct(@ModelAttribute Product product, @RequestParam("imageFile") MultipartFile imageFile, Model model) {
        try {
            if (!imageFile.isEmpty()) {
                String imageUrl = storageService.store(imageFile);
                product.setImageUrl(imageUrl);
                System.out.println("Image URL: " + imageUrl); // Logging für Debugging
            }
            productService.saveProduct(product);
            model.addAttribute("successMessage", "Produkt erfolgreich hinzugefügt!");
            return "redirect:/hauptmenu"; // Weiterleitung auf das Hauptmenü
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Fehler beim Hinzufügen des Produkts");
            return "addProduct";
        }
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
    @GetMapping("/product/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "editProduct";
    }

    @PostMapping("/product/edit/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product, Model model) {
        Product existingProduct = productService.getProductById(id);
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setHeight(product.getHeight());
        existingProduct.setOverPot(product.isOverPot());
        existingProduct.setShippingCosts(product.getShippingCosts());
        existingProduct.setImageUrl(product.getImageUrl());
        productService.saveProduct(existingProduct);
        return "redirect:/product/" + existingProduct.getId();
    }

}