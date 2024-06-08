package com.example.pflanzenhandel.controller;
import com.example.pflanzenhandel.service.UserService;
import com.example.pflanzenhandel.entity.Message;
import com.example.pflanzenhandel.entity.Benutzer;
import com.example.pflanzenhandel.service.MessageService;
import com.example.pflanzenhandel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listMessages(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Benutzer user = userService.getCurrentUser();
        List<Message> messages = messageService.getMessages(user);
        List<Benutzer> users = userService.findAllUsers();
        model.addAttribute("messages", messages);
        model.addAttribute("users", users);
        return "messages";
    }

    @GetMapping("/conversation/{recipientId}")
    public String getConversation(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long recipientId, Model model) {
        Benutzer sender = userService.getCurrentUser();
        Benutzer recipient = userService.findById(recipientId);
        List<Message> conversation = messageService.getConversation(sender, recipient);
        model.addAttribute("conversation", conversation);
        model.addAttribute("recipient", recipient);
        return "conversation";
    }

    @PostMapping("/send")
    public String sendMessage(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Long recipientId, @RequestParam String content) {
        Benutzer sender = userService.getCurrentUser();
        Benutzer recipient = userService.findById(recipientId);
        messageService.sendMessage(sender, recipient, content);
        return "redirect:/messages/conversation/" + recipientId;
    }
    @PostMapping("/start")
    public String startConversation(@RequestParam Long recipientId) {
        // Simply redirect to the conversation page
        return "redirect:/messages/conversation/" + recipientId;
    }
}
