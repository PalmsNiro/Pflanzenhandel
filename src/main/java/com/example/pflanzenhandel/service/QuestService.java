package com.example.pflanzenhandel.service;

import com.example.pflanzenhandel.entity.Quest;
import com.example.pflanzenhandel.repository.QuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class QuestService {
    @Autowired
    private QuestRepository questRepository;

    public List<Quest> findAll() {
        return questRepository.findAll();
    }

    public Quest save(Quest quest) {
        return questRepository.save(quest);
    }

    public List<Quest> getRandomQuests(int number) {
        List<Quest> allQuests = questRepository.findAll();
        // Debugging
        System.out.println("All Quests: " + allQuests);

        if (allQuests.isEmpty()) {
            System.out.println("No quests available in the repository.");
            return Collections.emptyList();
        }

        Collections.shuffle(allQuests);
        List<Quest> randomQuests = allQuests.subList(0, Math.min(number, allQuests.size()));

        // Debugging
        System.out.println("Random Quests: " + randomQuests);

        return randomQuests;
    }
}
