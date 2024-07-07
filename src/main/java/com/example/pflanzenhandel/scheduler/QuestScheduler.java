package com.example.pflanzenhandel.scheduler;

import com.example.pflanzenhandel.entity.Benutzer;
import com.example.pflanzenhandel.repository.BenutzerRepository;
import com.example.pflanzenhandel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuestScheduler {

    @Autowired
    private UserService userService;
    @Autowired
    private BenutzerRepository userRepository;

    // Schedule this to run every Monday at 00:00 (midnight)
    // second minute hour day-of-month month day-of-week
    @Scheduled(cron = "00 50 15 * * SUN")
    public void assignWeeklyQuests() {
        List<Benutzer> users = userRepository.findAll();
        for (Benutzer user : users) {
            userService.assignRandomQuestsToUser(user.getId(), 6); // Maximum 4 quests at the start of the week
        }
    }
}
