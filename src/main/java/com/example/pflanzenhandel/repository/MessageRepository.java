package com.example.pflanzenhandel.repository;

import com.example.pflanzenhandel.entity.Benutzer;
import com.example.pflanzenhandel.entity.Message;
import com.example.pflanzenhandel.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderIdOrRecipientId(Long senderId, Long recipientId);
    List<Message> findBySenderIdAndRecipientIdOrRecipientIdAndSenderId(
            Long senderId, Long recipientId, Long altRecipientId, Long altSenderId);
}
