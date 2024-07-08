package com.example.pflanzenhandel.controller;

import com.example.pflanzenhandel.entity.Benutzer;
import com.example.pflanzenhandel.entity.Message;
import com.example.pflanzenhandel.entity.Product;
import com.example.pflanzenhandel.repository.BenutzerRepository;
import com.example.pflanzenhandel.service.MessageService;
import com.example.pflanzenhandel.service.ProductService;
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
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String viewMessages(Model model, Principal principal) {
        Benutzer user = userService.getUserByUsername(principal.getName());
        List<Benutzer> conversationPartners = messageService.getConversationPartners(user);
        model.addAttribute("conversationPartners", conversationPartners);
        return "messages";
    }

    @GetMapping("/conversation/{recipientId}")
    public String getConversationFragment(@PathVariable Long recipientId, Principal principal, Model model) {
        Benutzer user = userService.getUserByUsername(principal.getName());
        Benutzer recipient = userService.findById(recipientId);
        if (recipient == null) {
            return "error"; // Handle later
        }
        List<Message> conversation = messageService.getConversation(user, recipient);
        model.addAttribute("conversation", conversation);
        model.addAttribute("recipient", recipient);
        return "conversation :: #messageList";
    }

    @GetMapping("/conversation")
    public String viewConversation(@RequestParam Long recipientId, Principal principal, Model model) {
        Benutzer user = userService.getUserByUsername(principal.getName());
        Benutzer recipient = userService.findById(recipientId);
        List<Message> conversation = messageService.getConversation(user, recipient);
        model.addAttribute("conversation", conversation);
        model.addAttribute("recipient", recipient);
        return "conversation";
    }

    @PostMapping("/send")
    public String sendMessage(@RequestParam Long recipientId, @RequestParam String content, Principal principal) {
        Benutzer sender = userService.getUserByUsername(principal.getName());
        Benutzer recipient = userService.findById(recipientId);
        if (recipient == null) {
            return "error";
        }
        if (!content.trim().isEmpty()) { // Überprüfung auf leeren Inhalt
            userService.addExperiencePoints(sender,1);
            userService.incrementMessageQuest(sender);
            //increment message counter on quests
            messageService.sendMessage(sender, recipient, content);
        }
        return "redirect:/messages/conversation?recipientId=" + recipientId;
    }

    @PostMapping("/start")
    public String startConversation(@RequestParam Long productId, Principal principal) {
        Benutzer sender = userService.getUserByUsername(principal.getName());
        Product product = productService.getProductById(productId);
        Benutzer recipient = product.getVerkaufer();
        if (recipient != null) {
            return "redirect:/messages/conversation?recipientId=" + recipient.getId();
        }
        return "error";
    }
}
