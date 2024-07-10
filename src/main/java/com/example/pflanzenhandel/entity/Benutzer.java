package com.example.pflanzenhandel.entity;

import jakarta.persistence.*;
import java.util.Set;
import java.util.HashSet;

@Entity
public class Benutzer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private boolean enabled = true;

    private int experiencePoints = 0;

    private int level = 0;

    private int numberOfQuestsCompleted = 0;

    private boolean newQuestsAvailable = true;

    private int numberOfBoosts = 0;

    private int weeklyQuestsProgress = 0;

    private boolean weeklyRewardReceived = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserQuest> userQuests = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Benutzer seller;



    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "benutzer_rolle",
            joinColumns = @JoinColumn(name = "benutzer_id"),
            inverseJoinColumns = @JoinColumn(name = "rolle_id")
    )
    private Set<Rolle> roles;

    public Benutzer() {
    }

    // Getter and Setter methods

    public Long getId() {
        return id;
    }

    public void setId(Long userId) {
        this.id = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getExperiencePoints() {
        return experiencePoints;
    }

    public void setExperiencePoints(int experiencePoints) {
        this.experiencePoints = experiencePoints;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getNumberOfQuestsCompleted() {
        return numberOfQuestsCompleted;
    }

    public void setNumberOfQuestsCompleted(int questsCompleted) {
        this.numberOfQuestsCompleted = questsCompleted;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Rolle> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rolle> roles) {
        this.roles = roles;
    }

    public Benutzer getSeller() {
        return seller;
    }

    public void setSeller(Benutzer seller) {
        this.seller = seller;
    }

    public Set<UserQuest> getUserQuests() {
        return userQuests;
    }

    public void setUserQuests(Set<UserQuest> userQuests) {
        this.userQuests = userQuests;
    }

    public boolean isNewQuestsAvailable() {
        return newQuestsAvailable;
    }

    public void setNewQuestsAvailable(boolean newQuestsAvailable) {
        this.newQuestsAvailable = newQuestsAvailable;
    }

    public int getNumberOfBoosts() {
        return numberOfBoosts;
    }

    public void setNumberOfBoosts(int numberOfBoosts) {
        this.numberOfBoosts = numberOfBoosts;
    }

    public boolean hasBoostsLeft(){
        return numberOfBoosts > 0;
    }

    public int getWeeklyQuestsProgress() {
        return weeklyQuestsProgress;
    }

    public void setWeeklyQuestsProgress(int weeklyQuestsProgress) {
        this.weeklyQuestsProgress = weeklyQuestsProgress;
    }

    public boolean isWeeklyRewardReceived() {
        return weeklyRewardReceived;
    }

    public void setWeeklyRewardReceived(boolean weeklyRewardReceived) {
        this.weeklyRewardReceived = weeklyRewardReceived;
    }
}
