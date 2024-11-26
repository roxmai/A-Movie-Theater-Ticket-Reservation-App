package com.example.acmeplex.usersystem.controller;

import com.example.acmeplex.usersystem.dto.UserDTO;
import com.example.acmeplex.usersystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import jakarta.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Set up a sample UserDTO with only email
        userDTO = new UserDTO();
        userDTO.setEmail("john.doe@example.com");
    }

    @Test
    @DisplayName("Create User - Success")
    void createUser_ShouldReturnCreatedUser() {
        // Arrange
        when(userService.createUser(any(UserDTO.class))).thenReturn(userDTO);

        // Act
        ResponseEntity<UserDTO> response = userController.createUser(userDTO);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userDTO, response.getBody());
        verify(userService, times(1)).createUser(userDTO);
    }

    @Test
    @DisplayName("Get User By Email - Success")
    void getUserByEmail_ShouldReturnUser() {
        // Arrange
        String email = "john.doe@example.com";
        when(userService.getUserByEmail(email)).thenReturn(userDTO);

        // Act
        ResponseEntity<UserDTO> response = userController.getUserByEmail(email);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userDTO, response.getBody());
        verify(userService, times(1)).getUserByEmail(email);
    }

    @Test
    @DisplayName("Get All Users - Success")
    void getAllUsers_ShouldReturnListOfUsers() {
        // Arrange
        List<UserDTO> users = Arrays.asList(
                new UserDTO("john.doe@example.com"),
                new UserDTO("jane.doe@example.com")
        );
        when(userService.getAllUsers()).thenReturn(users);

        // Act
        ResponseEntity<List<UserDTO>> response = userController.getAllUsers();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(users, response.getBody());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    @DisplayName("Delete User - Success")
    void deleteUser_ShouldReturnNoContent() {
        // Arrange
        String email = "john.doe@example.com";
        doNothing().when(userService).deleteUser(email);

        // Act
        ResponseEntity<Void> response = userController.deleteUser(email);

        // Assert
        assertEquals(204, response.getStatusCodeValue());
        verify(userService, times(1)).deleteUser(email);
    }

    @Test
    @DisplayName("Create User - Invalid Email Should Throw Exception")
    void createUser_InvalidEmail_ShouldThrowException() {
        // Arrange
        UserDTO invalidUserDTO = new UserDTO();
        invalidUserDTO.setEmail("invalid-email"); // Invalid email format

        // Simulate validation failure by throwing ConstraintViolationException
        when(userService.createUser(any(UserDTO.class)))
                .thenThrow(new ConstraintViolationException("Invalid email format", null));

        // Act & Assert
        ConstraintViolationException exception = assertThrows(
                ConstraintViolationException.class,
                () -> userController.createUser(invalidUserDTO),
                "Expected createUser to throw, but it didn't"
        );

        assertEquals("Invalid email format", exception.getMessage());
        verify(userService, times(1)).createUser(invalidUserDTO);
    }
}