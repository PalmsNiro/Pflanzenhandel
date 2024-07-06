package com.example.pflanzenhandel.controller;

import com.example.pflanzenhandel.entity.Benutzer;
import com.example.pflanzenhandel.entity.Quest;
import com.example.pflanzenhandel.service.QuestService;
import com.example.pflanzenhandel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/quests")
public class QuestController {
    @Autowired
    private QuestService questService;
    @Autowired
    private UserService userService;

    @GetMapping
    public String getUserQuests(@RequestParam Long userId, Model model) {
        Benutzer user = userService.findById(userId);
        if (user != null) {
            model.addAttribute("quests", user.getQuests());
            model.addAttribute("user", user); // Add user to the model if needed
        }

        // Debugging
        if (user.getQuests() != null)
            System.out.println("User Quests: " + user.getQuests());

        return "quests";
    }

    @GetMapping("/assignRandom")
    public String assignRandomQuestsToUser(@RequestParam Long userId, Model model) {
        Benutzer user = userService.assignRandomQuestsToUser(userId, 2);
        model.addAttribute("user", user);
        return "redirect:/quests?userId=" + userId; // Redirect to the quests page for the user
    }
}
