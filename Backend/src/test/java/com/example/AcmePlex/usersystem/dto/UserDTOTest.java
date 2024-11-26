package com.example.acmeplex.usersystem.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserDTOTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void closeValidatorFactory() {
        validatorFactory.close();
    }

    @Test
    @DisplayName("Valid UserDTO should have no violations")
    void validUserDTO() {
        UserDTO user = new UserDTO("john.doe@example.com");

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);
        assertTrue(violations.isEmpty(), "There should be no validation violations for a valid UserDTO.");
    }

    @Test
    @DisplayName("UserDTO with invalid email should have Email violation")
    void userDTOWithInvalidEmail() {
        UserDTO user = new UserDTO("invalid-email");

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Validation should fail when email is invalid.");

        ConstraintViolation<UserDTO> violation = violations.iterator().next();
        assertEquals("Email should be valid", violation.getMessage());
        assertEquals("email", violation.getPropertyPath().toString());
    }

    @Test
    @DisplayName("UserDTO with null email should pass validation")
    void userDTOWithNullEmail() {
        UserDTO user = new UserDTO(null);

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);
        assertTrue(violations.isEmpty(), "Validation should pass when email is null as @Email does not enforce non-null.");
    }

    @Test
    @DisplayName("UserDTO with empty email should fail Email validation")
    void userDTOWithEmptyEmail() {
        UserDTO user = new UserDTO("");

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Validation should fail when email is empty.");

        ConstraintViolation<UserDTO> violation = violations.iterator().next();
        assertEquals("Email should be valid", violation.getMessage());
        assertEquals("email", violation.getPropertyPath().toString());
    }
}