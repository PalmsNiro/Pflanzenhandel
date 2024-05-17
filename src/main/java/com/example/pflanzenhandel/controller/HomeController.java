package com.example.pflanzenhandel.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    /**
     * Displays the home page of your application.
     *
     * @param model the Model containing all model attributes
     * @return the home page view
     */
    @GetMapping("/")

    public String showHome(Model model) {
        model.addAttribute("message", "Die Webseite befindet sich noch in der Entwicklung. Bitte besuchen sie unsere Webseite zu einem späteren Zeitpunkt erneut.");
        return "home"; // The return value of the method is the name of the view (HTML page) zo be displayed
    }
}
