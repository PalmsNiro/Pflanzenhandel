package com.example.pflanzenhandel.entity;

import jakarta.persistence.*;

import java.util.List;

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
    private boolean marked;
    private boolean confirmedPurchase;
    private Boolean isSold = false;
    private String category;
    /*
    Currently for testing purposes only a String
    Needs to be replaced with a Set of Strings for Image Urls
    If you change his, make sure the home controller/home.htlm only pics one image
     */
    @ElementCollection
    private List<String> imageUrls;

    private String mainImageUrl;

    public boolean isOverPot() {
        return overPot;
    }

    @ManyToOne
    @JoinColumn(name = "verkaufer_id")
    private Benutzer verkaufer;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Benutzer buyer;

    public Product() {
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

    public void setOverPot(boolean overPot) {
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

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public Benutzer getVerkaufer() {
        return verkaufer;
    }

    public void setVerkaufer(Benutzer verkaufer) {
        this.verkaufer = verkaufer;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isConfirmedPurchase() {
        return confirmedPurchase;
    }

    public void setConfirmedPurchase(boolean confirmedPurchase) {
        this.confirmedPurchase = confirmedPurchase;
    }

    public Benutzer getBuyer() {
        return buyer;
    }

    public void setBuyer(Benutzer buyer) {
        this.buyer = buyer;
    }

    public Boolean getIsSold() {
        return isSold;
    }

    public void setIsSold(Boolean isSold) {
        this.isSold = isSold;
    }
}
