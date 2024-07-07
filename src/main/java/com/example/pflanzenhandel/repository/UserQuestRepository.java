package com.example.pflanzenhandel.repository;

import com.example.pflanzenhandel.entity.Benutzer;
import com.example.pflanzenhandel.entity.UserQuest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserQuestRepository extends JpaRepository<UserQuest, Long> {
    List<UserQuest> findByUser(Benutzer user);
}
