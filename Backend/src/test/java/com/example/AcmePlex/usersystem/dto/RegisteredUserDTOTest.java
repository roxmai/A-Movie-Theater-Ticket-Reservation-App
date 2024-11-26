package com.example.acmeplex.usersystem.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RegisteredUserDTOTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testRegisteredUserDTOValid() {
        RegisteredUserDTO userDTO = new RegisteredUserDTO(
                "john.doe@example.com",
                "John Doe",
                "123 Main St",
                "password123"
        );

        Set<ConstraintViolation<RegisteredUserDTO>> violations = validator.validate(userDTO);
        assertTrue(violations.isEmpty(), "DTO should be valid when all fields are properly set.");
    }

    @Test
    void testRegisteredUserDTONameNotBlank() {
        RegisteredUserDTO userDTO = new RegisteredUserDTO(
                "john.doe@example.com",
                "", // Invalid name
                "123 Main St",
                "password123"
        );

        Set<ConstraintViolation<RegisteredUserDTO>> violations = validator.validate(userDTO);
        assertFalse(violations.isEmpty(), "DTO should be invalid when name is blank.");
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("name") && v.getMessage().equals("Name is mandatory")),
                "Expected validation error for blank name.");
    }

    @Test
    void testRegisteredUserDTOAddressNotBlank() {
        RegisteredUserDTO userDTO = new RegisteredUserDTO(
                "john.doe@example.com",
                "John Doe",
                "", // Invalid address
                "password123"
        );

        Set<ConstraintViolation<RegisteredUserDTO>> violations = validator.validate(userDTO);
        assertFalse(violations.isEmpty(), "DTO should be invalid when address is blank.");
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("address") && v.getMessage().equals("Address is mandatory")),
                "Expected validation error for blank address.");
    }

    @Test
    void testRegisteredUserDTOPasswordNotBlank() {
        RegisteredUserDTO userDTO = new RegisteredUserDTO(
                "john.doe@example.com",
                "John Doe",
                "123 Main St",
                "" // Invalid password
        );

        Set<ConstraintViolation<RegisteredUserDTO>> violations = validator.validate(userDTO);
        assertFalse(violations.isEmpty(), "DTO should be invalid when password is blank.");
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("password") && v.getMessage().equals("Password is mandatory")),
                "Expected validation error for blank password.");
    }

    @Test
    void testRegisteredUserDTOEmailNotBlank() {
        RegisteredUserDTO userDTO = new RegisteredUserDTO(
                "", // Invalid email
                "John Doe",
                "123 Main St",
                "password123"
        );

        Set<ConstraintViolation<RegisteredUserDTO>> violations = validator.validate(userDTO);
        assertFalse(violations.isEmpty(), "DTO should be invalid when email is blank.");
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("email") && v.getMessage().equals("must not be blank")),
                "Expected validation error for blank email.");
    }

    @Test
    void testRegisteredUserDTOEmailNull() {
        RegisteredUserDTO userDTO = new RegisteredUserDTO(
                null, // Invalid email
                "John Doe",
                "123 Main St",
                "password123"
        );

        Set<ConstraintViolation<RegisteredUserDTO>> violations = validator.validate(userDTO);
        assertFalse(violations.isEmpty(), "DTO should be invalid when email is null.");
        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("email") && v.getMessage().equals("must not be blank")),
                "Expected validation error for null email.");
    }
}