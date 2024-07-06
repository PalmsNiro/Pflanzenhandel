package com.example.pflanzenhandel.service;

import com.example.pflanzenhandel.entity.Quest;
import com.example.pflanzenhandel.repository.QuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Optional<Quest> findById(Long id) {
        return questRepository.findById(id);
    }
}
