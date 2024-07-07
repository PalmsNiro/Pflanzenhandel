package com.example.pflanzenhandel.controller;

import com.example.pflanzenhandel.entity.Benutzer;
import com.example.pflanzenhandel.entity.Quest;
import com.example.pflanzenhandel.entity.UserQuest;
import com.example.pflanzenhandel.service.QuestService;
import com.example.pflanzenhandel.service.UserService;
import com.example.pflanzenhandel.repository.UserQuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/quests")
public class QuestController {
    @Autowired
    private QuestService questService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserQuestRepository userQuestRepository;

    @GetMapping
    public String getUserQuests(@RequestParam Long userId, Model model) {
        Benutzer user = userService.findById(userId);
        if (user != null) {
            List<UserQuest> userQuests = userQuestRepository.findByUser(user);
            model.addAttribute("quests", userQuests);
            model.addAttribute("user", user); // Add user to the model if needed
        }
        return "quests"; // This should correspond to quests.html in your templates folder
    }

    @GetMapping("/assignRandom")
    public String assignRandomQuestsToUser(@RequestParam Long userId, Model model) {
        Benutzer user = userService.assignRandomQuestsToUser(userId, 2);
        model.addAttribute("user", user);
        return "redirect:/quests?userId=" + userId; // Redirect to the quests page for the user
    }
}
