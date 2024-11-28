package com.example.acmeplex.usersystem.dto;

import jakarta.validation.constraints.NotBlank;

public class RegisteredUserDTO extends UserDTO {
    
    @NotBlank(message = "Name is mandatory")
    private String name;
    
    @NotBlank(message = "Address is mandatory")
    private String address;
    
    @NotBlank(message = "Password is mandatory")
    private String password;

    @NotBlank(message = "Credit Card is mandatory")
    private String cardNumber;

    // You can add more fields like subscription details as needed

    // Default constructor
    public RegisteredUserDTO() {}

    // All-args constructor
    public RegisteredUserDTO(String email, String name, String address, String password, String cardNumber) {
        super(email);
        this.name = name;
        this.address = address;
        this.password = password;
        this.cardNumber = cardNumber;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }


}