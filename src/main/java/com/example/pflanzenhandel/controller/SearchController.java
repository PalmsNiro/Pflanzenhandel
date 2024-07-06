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
public class SearchController {

    @Autowired
    private ProductService productService;

    @GetMapping("/search")
    public String search(@RequestParam("query") String query,
                         @RequestParam(value = "sort", required = false) String sort,
                         @RequestParam(value = "sortBy", required = false) String sortBy,
                         @RequestParam(value = "category", required = false) String category,
                         @RequestParam(value = "minPrice", required = false) Double minPrice,
                         @RequestParam(value = "maxPrice", required = false) Double maxPrice,
                         @RequestParam(value = "hasUebertopf", required = false) Boolean hasUebertopf,
                         @RequestParam(value = "minHeight", required = false) Double minHeight,
                         @RequestParam(value = "maxHeight", required = false) Double maxHeight,
                         Model model) {

        if (category == null && minPrice == null && maxPrice == null && hasUebertopf == null && minHeight == null && maxHeight == null) {
            List<Product> plants = productService.searchProducts(query);
            model.addAttribute("plants", plants);
        } else {
            List<Product> plants = productService.filterAndSortProducts(query, category, minPrice, maxPrice, hasUebertopf, minHeight, maxHeight, sort, sortBy);
            model.addAttribute("plants", plants);
        }

        model.addAttribute("query", query);
        model.addAttribute("sort", sort);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("category", category);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("hasUebertopf", hasUebertopf);
        model.addAttribute("minHeight", minHeight);
        model.addAttribute("maxHeight", maxHeight);

        return "search";
    }
}