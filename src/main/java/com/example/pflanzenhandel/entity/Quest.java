package com.example.pflanzenhandel.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
public class Quest {
    @Id
    @GeneratedValue
    private Long id;

    private String description;
    private int points =3; //for all quests the same
    private int xpForUser = 4;
    private int currentAmount = 0;
    private int neededAmount;
    private boolean completed = false;
    private LocalDateTime assignedDate;

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

    public int getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(int count) {
        this.currentAmount = count;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
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

    public LocalDateTime getAssignedDate() {return assignedDate;}

    public void setAssignedDate(LocalDateTime assignedDate) {this.assignedDate = assignedDate;}
}
