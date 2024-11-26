package com.example.acmeplex.usersystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserDTO {
    
    @Email(message = "Email should be valid")
    private String email;

    // Default constructor
    public UserDTO() {}

    // All-args constructor
    public UserDTO(String email) {
        this.email = email;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
}