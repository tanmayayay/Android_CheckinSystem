package com.example.trialapp.ui.Remote;

import org.jetbrains.annotations.Nullable;

public class User {

    private String givenName;
    private String lastName;
    private String email; // Added email field
    private String militaryId;
    private String militaryRank;
    private String userName;
    private String passwordHash;

    // Updated constructor to include email
    public User(String givenName, String lastName, String email, String militaryId, String militaryRank,
                String userName, String passwordHash) {
        this.givenName = givenName;
        this.lastName = lastName;
        this.email = email; // Initialize email
        this.militaryId = militaryId;
        this.militaryRank = militaryRank;
        this.userName = userName;
        this.passwordHash = passwordHash;
    }

    // Getters and setters
    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() { // Getter for email
        return email;
    }

    public void setEmail(String email) { // Setter for email
        this.email = email;
    }

    public String getMilitaryId() {
        return militaryId;
    }

    public void setMilitaryId(String militaryId) {
        this.militaryId = militaryId;
    }

    public String getMilitaryRank() {
        return militaryRank;
    }

    public void setMilitaryRank(String militaryRank) {
        this.militaryRank = militaryRank;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }


}
