package com.example.pflanzenhandel.repository;

import com.example.pflanzenhandel.entity.Benutzer;
import com.example.pflanzenhandel.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderAndRecipient(Benutzer sender, Benutzer recipient);
    List<Message> findByRecipient(Benutzer recipient);
}
