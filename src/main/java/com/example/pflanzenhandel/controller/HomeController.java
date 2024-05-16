package com.example.pflanzenhandel.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    /**
     * Zeigt die Startseite Ihrer Anwendung.
     * @param model enthält alle ModelAttribute.
     * @return home-Seite.
     */
    @GetMapping("/")

    public String showHome(Model model) {
        model.addAttribute("message", "Und hier sehen Sie ein ModelAttribut");
        return "home"; // return-Wert der Methode ist der Name der View (HTML-Seite), die angezeigt werden soll
    }
}
