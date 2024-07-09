package com.example.pflanzenhandel.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Quest {
    @Id
    @GeneratedValue
    private Long id;

    private String description;
    private int points =3; //for all quests the same
    private int xpForUser;

    private int neededAmount;


    @OneToMany(mappedBy = "quest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserQuest> userQuests = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long userId) {
        this.id = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNeededAmount() {
        return neededAmount;
    }

    public void setNeededAmount(int neededAmount) {
        this.neededAmount = neededAmount;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Set<UserQuest> getUserQuests() {
        return userQuests;
    }

    public void setUserQuests(Set<UserQuest> userQuests) {
        this.userQuests = userQuests;
    }

    public int getXpForUser() {
        return xpForUser;
    }

    public void setXpForUser(int xpForUser) {
        this.xpForUser = xpForUser;
    }
}
