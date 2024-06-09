package com.example.pflanzenhandel.controller;
import com.example.pflanzenhandel.entity.Product;
import com.example.pflanzenhandel.service.ProductService;
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

    @Autowired
    private ProductService productService;

    @GetMapping
    public String listMessages(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Benutzer user = userService.getCurrentUser();
        List<Message> messages = messageService.getMessages(user);
        model.addAttribute("messages", messages);
        return "messages";
    }

    @GetMapping("/conversation/{productId}")
    public String getConversation(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long productId, Model model) {
        Benutzer sender = userService.getCurrentUser();
        Product product = productService.getProductById(productId);
        Benutzer recipient = product.getVerkaufer();
        List<Message> conversation = messageService.getConversation(sender, recipient, product);
        model.addAttribute("conversation", conversation);
        model.addAttribute("recipient", recipient);
        model.addAttribute("product", product);
        return "conversation";
    }



    @PostMapping("/send")
    public String sendMessage(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Long recipientId, @RequestParam Long productId, @RequestParam String content) {
        Benutzer sender = userService.getCurrentUser();
        Benutzer recipient = userService.findById(recipientId);
        Product product = productService.getProductById(productId);
        messageService.sendMessage(sender, recipient,product, content);
        return "redirect:/messages/conversation/" + productId;
    }
    @PostMapping("/start")
    public String startConversation(@RequestParam Long productId) {
        // Simply redirect to the conversation page
        return "redirect:/messages/conversation/" + productId;
    }
}
