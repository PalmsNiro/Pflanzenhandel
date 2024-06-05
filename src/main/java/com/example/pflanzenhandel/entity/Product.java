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
    private double price;
    private double height;
    private boolean overPot;
    private double shippingCosts;
    private String description;

    public Product(){

    }

    // Getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public boolean getOverPot() {
        return overPot;
    }

    public void setOverPot(boolean overPot){
        this.overPot = overPot;
    }

    public double getShippingCosts() {
        return shippingCosts;
    }

    public void setShippingCosts(double shippingCosts) {
        this.shippingCosts = shippingCosts;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}