package com.example.pflanzenhandel.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String errorMessage = "Ein unbekannter Fehler ist aufgetreten";

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == 403) {
                errorMessage = "Zugriff verweigert";
            } else if (statusCode == 404) {
                errorMessage = "Seite nicht gefunden";
            } else if (statusCode == 500) {
                errorMessage = "Interner Serverfehler";
            }
        }
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }
}
