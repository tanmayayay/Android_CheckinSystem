package com.example.trialapp.ui.Remote;

public class Login {
    public String username;
    public String password;


    // Getter for Username
    public String getUsername() {
        return username;
    }

    // Setter for Username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter for Password
    public String getPassword() {
        return password;
    }

    // Setter for Password
    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString() {
        return "Login{" +
                "Username='" + username + '\'' +
                ", Password='" + "*****" + '\'' + // Masked for security
                '}';
    }
}
