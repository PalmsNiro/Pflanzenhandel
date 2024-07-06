package com.example.pflanzenhandel.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Benutzer sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private Benutzer recipient;

    private String content;
    private LocalDateTime timestamp;

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Benutzer getSender() {
        return sender;
    }

    public void setSender(Benutzer sender) {
        this.sender = sender;
    }

    public Benutzer getRecipient() {
        return recipient;
    }

    public void setRecipient(Benutzer recipient) {
        this.recipient = recipient;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}