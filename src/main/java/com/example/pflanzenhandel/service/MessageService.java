package com.example.pflanzenhandel.service;

import com.example.pflanzenhandel.entity.Benutzer;
import com.example.pflanzenhandel.entity.Message;
import com.example.pflanzenhandel.entity.Product;
import com.example.pflanzenhandel.repository.BenutzerRepository;
import com.example.pflanzenhandel.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private BenutzerRepository benutzerRepository;

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
        return messageRepository.findConversation(sender.getId(), recipient.getId());
    }

    public List<Benutzer> getConversationPartners(Benutzer user) {
        List<Message> messages = getMessages(user);
        Set<Benutzer> partners = new HashSet<>();
        for (Message message : messages) {
            if (message.getSender().equals(user)) {
                partners.add(message.getRecipient());
            } else {
                partners.add(message.getSender());
            }
        }
        return new ArrayList<>(partners);
    }

    public void startConversation(Long senderId, Long recipientId, String content) {
        Benutzer sender = benutzerRepository.findById(senderId).orElseThrow(() -> new RuntimeException("Sender not found"));
        Benutzer recipient = benutzerRepository.findById(recipientId).orElseThrow(() -> new RuntimeException("Recipient not found"));
        sendMessage(sender, recipient, content);
    }
}

