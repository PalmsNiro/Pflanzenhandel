package com.example.pflanzenhandel.service;

import com.example.pflanzenhandel.entity.Benutzer;
import com.example.pflanzenhandel.entity.Message;
import com.example.pflanzenhandel.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public void sendMessage(Benutzer sender, Benutzer recipient, String content) {
        Message message = new Message();
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());
        messageRepository.save(message);
    }

    public List<Message> getMessages(Benutzer recipient) {
        return messageRepository.findByRecipient(recipient);
    }

    public List<Message> getConversation(Benutzer user1, Benutzer user2) {
        List<Message> messages = messageRepository.findBySenderAndRecipient(user1, user2);
        messages.addAll(messageRepository.findBySenderAndRecipient(user2, user1));
        messages.sort((m1, m2) -> m1.getTimestamp().compareTo(m2.getTimestamp()));
        return messages;
    }
}
