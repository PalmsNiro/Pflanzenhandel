package com.example.pflanzenhandel.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import java.time.LocalDateTime;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Benutzer sender;

    @ManyToOne
    private Benutzer recipient;

    @ManyToOne
    private Product product;

    private String content;

    private LocalDateTime timestamp;

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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


}
