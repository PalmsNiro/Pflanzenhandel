package com.example.pflanzenhandel.service;

import com.example.pflanzenhandel.entity.Benutzer;
import com.example.pflanzenhandel.entity.Message;
import com.example.pflanzenhandel.entity.Product;
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

    public List<Message> getMessages(Benutzer user) {
        return messageRepository.findBySenderIdOrRecipientId(user.getId(), user.getId());
    }

    public List<Message> getConversation(Benutzer sender, Benutzer recipient) {
        return messageRepository.findBySenderIdAndRecipientIdOrRecipientIdAndSenderId(
                sender.getId(), recipient.getId(), sender.getId(), recipient.getId());
    }
}
