package com.example.pflanzenhandel.controller;

import com.example.pflanzenhandel.entity.Benutzer;
import com.example.pflanzenhandel.entity.Product;
import com.example.pflanzenhandel.service.ProductService;
import com.example.pflanzenhandel.service.StorageService;
import com.example.pflanzenhandel.repository.ProductRepository;
import com.example.pflanzenhandel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Optional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/product/{id}")
    public String getProductById(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Product product = productService.getProductById(id);
        Benutzer currentUser = userService.getCurrentUser();
        model.addAttribute("product", product);
        model.addAttribute("currentUser", currentUser);
        return "productDetails";
    }

    @PostMapping("/product/markAsSold/{id}")
    public String markAsSold(@PathVariable Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setIsSold(true);
            productRepository.save(product);
        }
        return "redirect:/product/" + id;
    }

    @PostMapping("/product/confirmPurchase/{id}")
    public String confirmPurchase(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        Product product = productService.getProductById(id);
        Benutzer currentUser = userService.getCurrentUser();
        if (product != null && !product.getVerkaufer().getUsername().equals(currentUser.getUsername())) {
            product.setConfirmedPurchase(true);
            product.setBuyer(currentUser);
            productService.saveProduct(product);
            return "redirect:/product/" + id;
        } else {
            return "redirect:/product/" + id + "?error=not_authorized";
        }
    }

    @GetMapping("/myPurchases")
    public String getMyPurchases(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Benutzer currentUser = userService.getCurrentUser();
        List<Product> myPurchases = productService.findPurchasedProducts(currentUser);
        model.addAttribute("myPurchases", myPurchases);
        return "myPurchases";
    }

    @GetMapping("/conversation")
    public String showConversation(@RequestParam("productId") Long productId, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Product product = productService.getProductById(productId);
        Benutzer currentUser = userService.getCurrentUser();
        Benutzer recipient = userService.getUserByUsername(userDetails.getUsername()); // Annahme, dass recipient der aktuelle Benutzer ist

        model.addAttribute("product", product);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("recipient", recipient);
        return "conversation";
    }

    @GetMapping("/plantCare")
    public String getPlantCarePage() {
        return "plantCare";
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


            // handle xp increase +3
            Benutzer currentUser = userService.getCurrentUser();
            userService.addExperiencePoints(currentUser, 3);

            product.setVerkaufer(currentUser);

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

    @GetMapping("/products")
    public String listProducts(@RequestParam(value = "query", required = false) String searchQuery,
                               @RequestParam(value = "sort", required = false) String sort,
                               @RequestParam(value = "sortBy", required = false) String sortBy,
                               @RequestParam(value = "category", required = false) String category,
                               @RequestParam(value = "minPrice", required = false) Double minPrice,
                               @RequestParam(value = "maxPrice", required = false) Double maxPrice,
                               @RequestParam(value = "hasUebertopf", required = false) Boolean hasUebertopf,
                               @RequestParam(value = "minHeight", required = false) Double minHeight,
                               @RequestParam(value = "maxHeight", required = false) Double maxHeight,
                               Model model) {
        List<Product> products = productService.filterAndSortProducts(searchQuery, category, minPrice, maxPrice, hasUebertopf, minHeight, maxHeight, sort, sortBy);

        model.addAttribute("products", products);
        model.addAttribute("keyword", searchQuery);
        model.addAttribute("sort", sort);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("category", category);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("hasUebertopf", hasUebertopf);
        model.addAttribute("minHeight", minHeight);
        model.addAttribute("maxHeight", maxHeight);
        return "home";
    }

    @GetMapping("/products/mark")
    public String markProduct(@RequestParam("id") Long productId,
                              @RequestParam("marked") boolean marked,
                              @RequestParam(value = "redirect", required = false) String redirect) {
        productService.markProduct(productId, marked);
        // Gamification inkrement Favoriten Quest
        if (marked) {
            Benutzer user = userService.getCurrentUser();
            userService.incrementFavoritenQuest(user);
        }
        if ("favorites".equals(redirect)) {
            return "redirect:/products/marked";
        }
        return "redirect:/products";
    }

    @GetMapping("/products/marked")
    public String getMarkedProducts(Model model) {
        List<Product> markedProducts = productService.findMarkedProducts();
        model.addAttribute("markedProducts", markedProducts);
        return "markedProducts";
    }

    @PostMapping("/products/boost/{id}")
    public String boostProduct(@PathVariable Long id, Principal principal, RedirectAttributes redirectAttributes) {
        Product product = productService.getProductById(id);
        Benutzer user = userService.getUserByUsername(principal.getName());

        if (product != null && product.getVerkaufer().getUsername().equals(principal.getName())) {
            if (product.isBoosted()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Dieses Produkt ist bereits geboosted.");
            } else if (user.getNumberOfBoosts() == 0) {
                redirectAttributes.addFlashAttribute("errorMessage", "Sie haben keine Boosts mehr übrig.");
            } else {
                productService.boostProduct(product);
                user.setNumberOfBoosts(user.getNumberOfBoosts() - 1);
                userService.saveUser(user);
                redirectAttributes.addFlashAttribute("successMessage", "Das Produkt wurde erfolgreich geboosted.");
            }
        }

        return "redirect:/profile"; // Umleiten auf das Profil nach dem Boosten
    }
}