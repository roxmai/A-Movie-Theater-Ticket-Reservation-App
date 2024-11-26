package com.example.acmeplex.usersystem.controller;

import com.example.acmeplex.usersystem.dto.RegisteredUserDTO;
import com.example.acmeplex.usersystem.service.RegisteredUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisteredUserControllerTest {

    @Mock
    private RegisteredUserService registeredUserService;

    @InjectMocks
    private RegisteredUserController registeredUserController;

    private RegisteredUserDTO userDTO;
    private final String userEmail = "john.doe@example.com";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userDTO = new RegisteredUserDTO();
        userDTO.setEmail(userEmail);
        userDTO.setName("John Doe");
        userDTO.setAddress("123 Main St");
        userDTO.setPassword("password123");
    }

    @Test
    void testCreateRegisteredUser() {
        when(registeredUserService.createRegisteredUser(any(RegisteredUserDTO.class))).thenReturn(userDTO);

        ResponseEntity<RegisteredUserDTO> response = registeredUserController.createRegisteredUser(userDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(userDTO.getEmail(), response.getBody().getEmail());
        assertEquals(userDTO.getName(), response.getBody().getName());
        assertEquals(userDTO.getAddress(), response.getBody().getAddress());
        verify(registeredUserService, times(1)).createRegisteredUser(any(RegisteredUserDTO.class));
    }

    @Test
    void testGetRegisteredUserByEmail() {
        when(registeredUserService.getRegisteredUserByEmail(userEmail)).thenReturn(userDTO);

        ResponseEntity<RegisteredUserDTO> response = registeredUserController.getRegisteredUserByEmail(userEmail);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(userDTO.getEmail(), response.getBody().getEmail());
        assertEquals(userDTO.getName(), response.getBody().getName());
        assertEquals(userDTO.getAddress(), response.getBody().getAddress());
        verify(registeredUserService, times(1)).getRegisteredUserByEmail(userEmail);
    }

    @Test
    void testGetAllRegisteredUsers() {
        List<RegisteredUserDTO> users = Arrays.asList(userDTO);
        when(registeredUserService.getAllRegisteredUsers()).thenReturn(users);

        ResponseEntity<List<RegisteredUserDTO>> response = registeredUserController.getAllRegisteredUsers();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
        RegisteredUserDTO returnedUser = response.getBody().get(0);
        assertEquals(userDTO.getEmail(), returnedUser.getEmail());
        assertEquals(userDTO.getName(), returnedUser.getName());
        assertEquals(userDTO.getAddress(), returnedUser.getAddress());
        verify(registeredUserService, times(1)).getAllRegisteredUsers();
    }

    @Test
    void testUpdateRegisteredUser() {
        RegisteredUserDTO updatedUserDTO = new RegisteredUserDTO();
        updatedUserDTO.setEmail(userEmail);
        updatedUserDTO.setName("Jane Doe");
        updatedUserDTO.setAddress("456 Elm St");
        updatedUserDTO.setPassword("newpassword456");
        // Initialize other fields as necessary

        when(registeredUserService.updateRegisteredUser(eq(userEmail), any(RegisteredUserDTO.class))).thenReturn(updatedUserDTO);

        ResponseEntity<RegisteredUserDTO> response = registeredUserController.updateRegisteredUser(userEmail, updatedUserDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(updatedUserDTO.getEmail(), response.getBody().getEmail());
        assertEquals("Jane Doe", response.getBody().getName());
        assertEquals("456 Elm St", response.getBody().getAddress());
        verify(registeredUserService, times(1)).updateRegisteredUser(eq(userEmail), any(RegisteredUserDTO.class));
    }

    @Test
    void testDeleteRegisteredUser() {
        doNothing().when(registeredUserService).deleteRegisteredUser(userEmail);

        ResponseEntity<Void> response = registeredUserController.deleteRegisteredUser(userEmail);

        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(registeredUserService, times(1)).deleteRegisteredUser(userEmail);
    }

}