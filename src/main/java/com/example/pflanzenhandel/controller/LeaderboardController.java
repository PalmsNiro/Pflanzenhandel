package com.example.pflanzenhandel.controller;

import com.example.pflanzenhandel.repository.BenutzerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.pflanzenhandel.entity.*;
import com.example.pflanzenhandel.service.*;

import javax.management.Query;
import java.security.Principal;
import java.util.List;

@Controller
public class LeaderboardController {

    @Autowired
    private BenutzerRepository userRepository;

    @GetMapping({"/leaderboard"})
    public String showUsersOnLeaderboard(Query query, Model model, Principal principal) {
        List<Benutzer> users;
        users = userRepository.findAll();

        model.addAttribute("users", users);

        return "leaderboard";
    }
}
