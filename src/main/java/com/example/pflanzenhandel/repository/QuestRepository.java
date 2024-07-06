package com.example.pflanzenhandel.repository;

import com.example.pflanzenhandel.entity.Quest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface QuestRepository extends JpaRepository<Quest, Long> {
}
