package com.example.pflanzenhandel.controller;

import com.example.pflanzenhandel.repository.BenutzerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.pflanzenhandel.entity.*;
import org.springframework.web.bind.annotation.RequestParam;

import javax.management.Query;
import java.security.Principal;
import java.util.List;

@Controller
public class LeaderboardController {

    @Autowired
    private BenutzerRepository userRepository;

    @GetMapping({"/leaderboard"})
    public String showUsersOnLeaderboard(@RequestParam(value = "sort", required = false) String sort, Model model, Principal principal) {
        List<Benutzer> users = userRepository.findAll();

        // Sortiere die Benutzer nach dem angegebenen Kriterium
        if ("level".equals(sort)) {
            users.sort((u1, u2) -> Integer.compare(u2.getLevel(), u1.getLevel()));
        } else if ("questsCompleted".equals(sort)) {
            users.sort((u1, u2) -> Integer.compare(u2.getNumberOfQuestsCompleted(), u1.getNumberOfQuestsCompleted()));
        } else {
            // Standard-Sortierung (z.B. nach Level)
            users.sort((u1, u2) -> Integer.compare(u2.getLevel(), u1.getLevel()));
        }

        model.addAttribute("users", users);

        return "leaderboard";
    }
}
