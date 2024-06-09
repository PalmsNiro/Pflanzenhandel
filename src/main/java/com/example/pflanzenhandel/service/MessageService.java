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

    public void sendMessage(Benutzer sender, Benutzer recipient, Product product, String content) {
        Message message = new Message();
        message.setSender(sender);
        message.setProduct(product);
        message.setRecipient(recipient);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());
        messageRepository.save(message);
    }

    public List<Message> getMessages(Benutzer recipient) {
        return messageRepository.findByRecipient(recipient);
    }

    public List<Message> getConversation(Benutzer  sender, Benutzer recipient,Product product) {
        List<Message> messages = messageRepository.findBySenderAndRecipientAndProduct(sender, recipient, product);
        messages.addAll(messageRepository.findBySenderAndRecipientAndProduct(recipient, sender, product));
        messages.sort((m1, m2) -> m1.getTimestamp().compareTo(m2.getTimestamp()));
        return messages;
    }
}
