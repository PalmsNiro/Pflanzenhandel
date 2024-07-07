package com.example.pflanzenhandel.service;

import com.example.pflanzenhandel.entity.Quest;
import com.example.pflanzenhandel.repository.QuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

        if (allQuests.size() <= number) {
            return allQuests;
        }

        // Shuffle and select unique quests
        Collections.shuffle(allQuests);
        Set<Quest> randomQuests = new HashSet<>();
        int i = 0;
        while (randomQuests.size() < number && i < allQuests.size()) {
            randomQuests.add(allQuests.get(i));
            i++;
        }

        // Debugging
        System.out.println("Random Quests: " + randomQuests);

        return randomQuests.stream().collect(Collectors.toList());
    }
}
