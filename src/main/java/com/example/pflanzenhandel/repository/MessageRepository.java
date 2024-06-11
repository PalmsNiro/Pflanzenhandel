package com.example.pflanzenhandel.repository;

import com.example.pflanzenhandel.entity.Benutzer;
import com.example.pflanzenhandel.entity.Message;
import com.example.pflanzenhandel.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderIdOrRecipientId(Long senderId, Long recipientId);
    List<Message> findBySenderIdAndRecipientIdOrRecipientIdAndSenderId(Long senderId, Long recipientId, Long altRecipientId, Long altSenderId);
    @Query("SELECT m FROM Message m WHERE (m.sender.id = :userId OR m.recipient.id = :userId) " +
            "AND (m.sender.id = :otherUserId OR m.recipient.id = :otherUserId)")
    List<Message> findConversation(@Param("userId") Long userId, @Param("otherUserId") Long otherUserId);
}
