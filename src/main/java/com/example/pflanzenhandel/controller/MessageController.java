package com.example.pflanzenhandel.controller;

import com.example.pflanzenhandel.entity.Benutzer;
import com.example.pflanzenhandel.entity.Message;
import com.example.pflanzenhandel.entity.Product;
import com.example.pflanzenhandel.repository.BenutzerRepository;
import com.example.pflanzenhandel.repository.ProductRepository;
import com.example.pflanzenhandel.service.MessageService;
import com.example.pflanzenhandel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private BenutzerRepository benutzerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public String viewMessages(Model model, Principal principal) {
        Benutzer user = benutzerRepository.findByUsername(principal.getName());
        List<Benutzer> conversationPartners = messageService.getConversationPartners(user);
        model.addAttribute("conversationPartners", conversationPartners);
        return "messages";
    }

    @GetMapping("/conversation")
    public String viewConversation(@RequestParam Long recipientId, Principal principal, Model model) {
        Benutzer user = benutzerRepository.findByUsername(principal.getName());
        Benutzer recipient = benutzerRepository.findById(recipientId).orElseThrow();
        List<Message> conversation = messageService.getConversation(user, recipient);
        model.addAttribute("conversation", conversation);
        model.addAttribute("recipient", recipient);
        return "conversation";
    }

    @PostMapping("/send")
    public String sendMessage(@RequestParam Long recipientId, @RequestParam String content, Principal principal) {
        Benutzer sender = benutzerRepository.findByUsername(principal.getName());
        Benutzer recipient = benutzerRepository.findById(recipientId).orElse(null);
        if (recipient == null) {
            return "error";
        }
        messageService.sendMessage(sender, recipient, content);
        return "redirect:/messages/conversation?recipientId=" + recipientId;
    }

    @PostMapping("/start")
    public String startConversation(@RequestParam Long productId, Principal principal) {
        Benutzer sender = benutzerRepository.findByUsername(principal.getName());
        Product product = productRepository.findById(productId).orElseThrow();
        Benutzer recipient = product.getSeller();
        messageService.sendMessage(sender, recipient, "Hi, I'm interested in your product.");
        return "redirect:/messages/conversation?recipientId=" + recipient.getId();
    }

    @GetMapping("/conversations/{userId}")
    @ResponseBody
    public List<Benutzer> getConversations(@PathVariable Long userId) {
        return userService.getConversations(userId);
    }
}