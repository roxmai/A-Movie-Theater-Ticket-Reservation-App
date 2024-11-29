package com.example.acmeplex.usersystem.payload;

public class LoginRequest {
    private String email;
    private String password;

    // Getters and Setters
    public String getUsername() {
        return email;
    }

    public void setUsername(String email) {
        this.email = email;
    }

    public String getPassword() { 
        return password; 
    }

    public void setPassword(String password) { 
        this.password = password; 
    }
}