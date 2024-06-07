package com.example.pflanzenhandel.controller;

import com.example.pflanzenhandel.entity.Product;
import com.example.pflanzenhandel.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ItemUploadController {

    @GetMapping("/itemUpload")
    public String showItemUploadPage() {
        return "itemUpload"; // Name der HTML-Datei in src/main/resources/templates
    }
}