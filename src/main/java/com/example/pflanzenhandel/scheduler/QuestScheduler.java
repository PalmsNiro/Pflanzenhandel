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

    // seconds minutes hours day-of-month month day-of-week
    @Scheduled(cron = "00 00 00 * * MON")
    public void assignWeeklyQuests() {
        List<Benutzer> users = userRepository.findAll();
        for (Benutzer user : users) {
            userService.assignRandomQuestsToUser(user.getId(), 7);
            userService.resetWeeklyQuestProgress(user);
            user.setWeeklyRewardReceived(false);
            userService.saveUser(user);
        }
    }
}
