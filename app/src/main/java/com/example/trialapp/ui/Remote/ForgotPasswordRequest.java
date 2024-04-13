package com.example.trialapp.ui.Remote;

import org.jetbrains.annotations.NotNull;

public class ForgotPasswordRequest {
    private String email;

    public ForgotPasswordRequest(@NotNull String email) {
        
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}


