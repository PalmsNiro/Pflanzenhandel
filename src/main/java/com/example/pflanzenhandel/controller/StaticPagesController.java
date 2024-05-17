package com.example.pflanzenhandel.controller;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
/**
 * Controller for static pages.
 */
@Controller
public class StaticPagesController implements WebMvcConfigurer {
    /**
     * Maps the "/login" URL to the login view.
     *
     * @param registry the ViewControllerRegistry to register view controllers
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");

    }
}