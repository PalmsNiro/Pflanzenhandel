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
    public String getAllQuests(Model model) {
        model.addAttribute("quests", questService.findAll());
        return "quests";
    }

    @PostMapping("/assign")
    public String assignQuestToUser(@RequestParam Long questId, @RequestParam Long userId) {
        Optional<Quest> quest = questService.findById(questId);
        Benutzer user = userService.findById(userId);

        if (quest.isPresent() && user!=null) {
            user.getQuests().add(quest.get());
//            userService.saveUser(user);
//            model.addAttribute("quests", quest);
        }

        return "redirect:/quests";
    }
}
