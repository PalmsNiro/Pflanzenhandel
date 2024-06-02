package com.example.pflanzenhandel.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private String description;
    private double price;

    // Getters and setters
}