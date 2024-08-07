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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
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
    public String getUserQuests(Model model) {
//        Benutzer user = userService.findById(userId);
        Benutzer user = userService.getCurrentUser();
        if (user != null) {
            List<UserQuest> userQuests = userQuestRepository.findByUser(user);
            model.addAttribute("quests", userQuests);
            model.addAttribute("user", user); // Add user to the model if needed
        }
        return "quests";
    }

    @GetMapping("/assignRandom")
    public String assignRandomQuestsToUser(Model model) {
        Benutzer user = userService.getCurrentUser();
        userService.assignRandomQuestsToUser(user.getId(), 2);
        model.addAttribute("user", user);
        return "redirect:/quests?userId=" + user.getId(); // Redirect to the quests page for the user
    }

    @GetMapping("/complete/{id}")
    public String completeQuest(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        UserQuest userQuest = userQuestRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid quest ID"));

        if (userQuest.getProgress() >= userQuest.getQuest().getNeededAmount()) {
            Benutzer user = userQuest.getUser();
            userService.addExperiencePoints(user,userQuest.getQuest().getXpForUser()); // Quest BelohnungsXp dem User geben
            userQuestRepository.delete(userQuest); // Quest löschen wenn fertig
            user.setNumberOfQuestsCompleted(user.getNumberOfQuestsCompleted() + 1); // erhöhen des questcompletedCounters des users
            user.setWeeklyQuestsProgress(user.getWeeklyQuestsProgress()+1); //Weekly Quests Progress erhöhen
            userService.saveUser(user);
            redirectAttributes.addFlashAttribute("message", "Quest completed successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Quest is not yet completed.");
        }

        // Redirect to the homepage
        return "redirect:/quests?userId=" + userQuest.getUser().getId();
    }

    @PostMapping("/resetWeeklyProgress")
    public String resetWeeklyProgress(RedirectAttributes redirectAttributes) {
        Benutzer user = userService.getCurrentUser();

        if (user.getWeeklyQuestsProgress() >= 5) {
            user.setWeeklyQuestsProgress(0);
            user.setNumberOfBoosts(user.getNumberOfBoosts() + 1);
            user.setWeeklyRewardReceived(true);
            userService.saveUser(user);
            redirectAttributes.addFlashAttribute("successMessage", "Der wöchentliche Fortschritt wurde zurückgesetzt.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Der wöchentliche Fortschritt ist noch nicht voll.");
        }

        return "redirect:/quests";
    }
}