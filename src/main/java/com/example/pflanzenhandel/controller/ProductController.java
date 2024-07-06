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

import java.io.IOException;
import java.util.ArrayList;
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
    public String getProductById(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Product product = productService.getProductById(id);
        Benutzer currentUser = userService.getCurrentUser();
        model.addAttribute("product", product);
        model.addAttribute("currentUser", currentUser);
        return "productDetails";
    }
    @GetMapping("/plantCare")
        public String getPlantCarePage() {
            return "plantCare"; // Name der Thymeleaf-Vorlage
        }

    @GetMapping("/product/new")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "addProduct";
    }

    @PostMapping("/product/new")
    public String addProduct(@ModelAttribute Product product, @RequestParam("imageFiles") MultipartFile[] imageFiles, Model model) {
        try {
            List<String> imageUrls = new ArrayList<>();
            for (MultipartFile imageFile : imageFiles) {
                if (!imageFile.isEmpty()) {
                    String imageUrl = storageService.store(imageFile);
                    imageUrls.add(imageUrl);
                }
            }
            product.setImageUrls(imageUrls);
            if (!imageUrls.isEmpty()) {
                product.setMainImageUrl(imageUrls.getFirst()); // Set the main image URL as the first image
            }
            productService.saveProduct(product);
            model.addAttribute("successMessage", "Produkt erfolgreich hinzugefügt!");
            return "redirect:/hauptmenu";
        } catch (IOException e) {
            model.addAttribute("errorMessage", "Fehler beim Hinzufügen des Produkts: " + e.getMessage());
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

        return null;
    }
    @GetMapping("/product/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "editProduct";
    }



    @PostMapping("/product/edit/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product, @RequestParam("imageFiles") MultipartFile[] imageFiles, Model model) {
        Product existingProduct = productService.getProductById(id);
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setHeight(product.getHeight());
        existingProduct.setOverPot(product.getOverPot());

        try {
            List<String> imageUrls = existingProduct.getImageUrls();
            for (MultipartFile imageFile : imageFiles) {
                if (!imageFile.isEmpty()) {
                    String imageUrl = storageService.store(imageFile);
                    imageUrls.add(imageUrl);
                }
            }
            existingProduct.setImageUrls(imageUrls);
            if (!imageUrls.isEmpty()) {
                existingProduct.setMainImageUrl(imageUrls.getFirst()); // Set the main image URL as the first image
            }
        } catch (IOException e) {
            model.addAttribute("errorMessage", "Fehler beim Aktualisieren des Produkts: " + e.getMessage());
            return "editProduct";
        }

        productService.saveProduct(existingProduct);
        return "redirect:/product/" + existingProduct.getId();
    }



    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails, Model model) {
        Product product = productService.getProductById(id);
        Benutzer currentUser = userService.getCurrentUser();
        if (product != null && product.getVerkaufer().getUsername().equals(currentUser.getUsername())) {
            productService.deleteProductById(id);
            model.addAttribute("successMessage", "Produkt erfolgreich gelöscht!");
            return "redirect:/hauptmenu"; // Weiterleitung zum Hauptmenü
        } else {
            model.addAttribute("errorMessage", "Sie sind nicht berechtigt, dieses Produkt zu löschen.");
            return "productDetails";
        }
    }

}