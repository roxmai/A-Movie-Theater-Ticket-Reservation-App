package com.example.acmeplex.usersystem.service;

import com.example.acmeplex.usersystem.dto.RegisteredUserDTO;
import com.example.acmeplex.usersystem.model.RegisteredUser;
import com.example.acmeplex.usersystem.repository.RegisteredUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.client.ResourceAccessException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for RegisteredUserService.
 */
@ExtendWith(MockitoExtension.class)
public class RegisteredUserServiceTest {

    @Mock
    private RegisteredUserRepository registeredUserRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RegisteredUserService registeredUserService;

    private RegisteredUserDTO userDTO;
    private RegisteredUser user;

    @BeforeEach
    public void setUp() {
        // Initialize sample data for tests
        userDTO = new RegisteredUserDTO("john.doe@example.com", "John Doe", "123 Main St", "password123");

        user = new RegisteredUser();
        user.setEmail("john.doe@example.com");
        user.setName("John Doe");
        user.setAddress("123 Main St");
        user.setPassword("password123");
        // Initialize other fields as necessary
    }

    /**
     * Test creating a registered user successfully.
     */
    @Test
    public void testCreateRegisteredUser_Success() {
        // Arrange
        when(registeredUserRepository.existsByEmail(userDTO.getEmail())).thenReturn(false);
        when(modelMapper.map(userDTO, RegisteredUser.class)).thenReturn(user);
        when(registeredUserRepository.save(user)).thenReturn(user);
        when(modelMapper.map(user, RegisteredUserDTO.class)).thenReturn(userDTO);

        // Act
        RegisteredUserDTO createdUser = registeredUserService.createRegisteredUser(userDTO);

        // Assert
        assertNotNull(createdUser);
        assertEquals(userDTO.getEmail(), createdUser.getEmail());
        assertEquals(userDTO.getName(), createdUser.getName());
        assertEquals(userDTO.getAddress(), createdUser.getAddress());
        assertEquals(userDTO.getPassword(), createdUser.getPassword());
        // Add assertions for other fields as necessary

        verify(registeredUserRepository, times(1)).existsByEmail(userDTO.getEmail());
        verify(modelMapper, times(1)).map(userDTO, RegisteredUser.class);
        verify(registeredUserRepository, times(1)).save(user);
        verify(modelMapper, times(1)).map(user, RegisteredUserDTO.class);
    }

    /**
     * Test creating a registered user when email already exists.
     */
    @Test
    public void testCreateRegisteredUser_EmailAlreadyExists() {
        // Arrange
        when(registeredUserRepository.existsByEmail(userDTO.getEmail())).thenReturn(true);

        // Act & Assert
        DuplicateKeyException exception = assertThrows(DuplicateKeyException.class, () -> {
            registeredUserService.createRegisteredUser(userDTO);
        });

        assertEquals("Email already in use: " + userDTO.getEmail(), exception.getMessage());
        verify(registeredUserRepository, times(1)).existsByEmail(userDTO.getEmail());
        verify(modelMapper, never()).map(any(), any());
        verify(registeredUserRepository, never()).save(any());
        verify(modelMapper, never()).map(any(), any());
    }

    /**
     * Test retrieving a registered user by email successfully.
     */
    @Test
    public void testGetRegisteredUserByEmail_Success() {
        // Arrange
        when(registeredUserRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.of(user));
        when(modelMapper.map(user, RegisteredUserDTO.class)).thenReturn(userDTO);

        // Act
        RegisteredUserDTO foundUser = registeredUserService.getRegisteredUserByEmail(userDTO.getEmail());

        // Assert
        assertNotNull(foundUser);
        assertEquals(userDTO.getEmail(), foundUser.getEmail());
        assertEquals(userDTO.getName(), foundUser.getName());
        assertEquals(userDTO.getAddress(), foundUser.getAddress());
        assertEquals(userDTO.getPassword(), foundUser.getPassword());
        // Add assertions for other fields as necessary

        verify(registeredUserRepository, times(1)).findByEmail(userDTO.getEmail());
        verify(modelMapper, times(1)).map(user, RegisteredUserDTO.class);
    }

    /**
     * Test retrieving a registered user by email when not found.
     */
    @Test
    public void testGetRegisteredUserByEmail_NotFound() {
        // Arrange
        when(registeredUserRepository.findByEmail(userDTO.getEmail())).thenReturn(Optional.empty());

        // Act & Assert
        ResourceAccessException exception = assertThrows(ResourceAccessException.class, () -> {
            registeredUserService.getRegisteredUserByEmail(userDTO.getEmail());
        });

        assertEquals("Registered user not found with email: " + userDTO.getEmail(), exception.getMessage());
        verify(registeredUserRepository, times(1)).findByEmail(userDTO.getEmail());
        verify(modelMapper, never()).map(any(), any());
    }

    /**
     * Test retrieving all registered users successfully.
     */
    @Test
    public void testGetAllRegisteredUsers_Success() {
        // Arrange
        RegisteredUser user2 = new RegisteredUser();
        user2.setEmail("jane.smith@example.com");
        user2.setName("Jane Smith");
        user2.setAddress("456 Elm St");
        user2.setPassword("password456");
        // Initialize other fields as necessary

        RegisteredUserDTO userDTO2 = new RegisteredUserDTO("jane.smith@example.com", "Jane Smith", "456 Elm St", "password456");

        List<RegisteredUser> users = Arrays.asList(user, user2);
        List<RegisteredUserDTO> userDTOs = Arrays.asList(userDTO, userDTO2);

        when(registeredUserRepository.findAll()).thenReturn(users);
        when(modelMapper.map(user, RegisteredUserDTO.class)).thenReturn(userDTO);
        when(modelMapper.map(user2, RegisteredUserDTO.class)).thenReturn(userDTO2);

        // Act
        List<RegisteredUserDTO> allUsers = registeredUserService.getAllRegisteredUsers();

        // Assert
        assertNotNull(allUsers);
        assertEquals(2, allUsers.size());
        assertTrue(allUsers.contains(userDTO));
        assertTrue(allUsers.contains(userDTO2));

        verify(registeredUserRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(user, RegisteredUserDTO.class);
        verify(modelMapper, times(1)).map(user2, RegisteredUserDTO.class);
    }

    /**
     * Test updating a registered user successfully.
     */
    @Test
    public void testUpdateRegisteredUser_Success() {
        // Arrange
        String originalEmail = userDTO.getEmail();
        RegisteredUserDTO updatedDTO = new RegisteredUserDTO("john.updated@example.com", "John Updated", "789 Oak St", "newpassword123");

        RegisteredUser updatedUser = new RegisteredUser();
        updatedUser.setEmail(updatedDTO.getEmail());
        updatedUser.setName(updatedDTO.getName());
        updatedUser.setAddress(updatedDTO.getAddress());
        updatedUser.setPassword(updatedDTO.getPassword());
        // Initialize other fields as necessary

        RegisteredUserDTO returnedDTO = updatedDTO; // Assuming modelMapper maps updatedUser to updatedDTO

        when(registeredUserRepository.findByEmail(originalEmail)).thenReturn(Optional.of(user));
        when(registeredUserRepository.existsByEmail(updatedDTO.getEmail())).thenReturn(false);
        when(registeredUserRepository.save(any(RegisteredUser.class))).thenReturn(updatedUser);
        when(modelMapper.map(updatedUser, RegisteredUserDTO.class)).thenReturn(returnedDTO);

        // Act
        RegisteredUserDTO result = registeredUserService.updateRegisteredUser(originalEmail, updatedDTO);

        // Assert
        assertNotNull(result);
        assertEquals(updatedDTO.getEmail(), result.getEmail());
        assertEquals(updatedDTO.getName(), result.getName());
        assertEquals(updatedDTO.getAddress(), result.getAddress());
        assertEquals(updatedDTO.getPassword(), result.getPassword());
        // Add assertions for other fields as necessary

        verify(registeredUserRepository, times(1)).findByEmail(originalEmail);
        verify(registeredUserRepository, times(1)).existsByEmail(updatedDTO.getEmail());
        verify(registeredUserRepository, times(1)).save(any(RegisteredUser.class));
        verify(modelMapper, times(1)).map(updatedUser, RegisteredUserDTO.class);
    }

    /**
     * Test updating a registered user when the new email is already in use.
     */
    @Test
    public void testUpdateRegisteredUser_EmailAlreadyExists() {
        // Arrange
        String originalEmail = userDTO.getEmail();
        RegisteredUserDTO updatedDTO = new RegisteredUserDTO("existing.email@example.com", "John Updated", "789 Oak St", "newpassword123");

        when(registeredUserRepository.findByEmail(originalEmail)).thenReturn(Optional.of(user));
        when(registeredUserRepository.existsByEmail(updatedDTO.getEmail())).thenReturn(true);

        // Act & Assert
        DuplicateKeyException exception = assertThrows(DuplicateKeyException.class, () -> {
            registeredUserService.updateRegisteredUser(originalEmail, updatedDTO);
        });

        assertEquals("Email already in use: " + updatedDTO.getEmail(), exception.getMessage());
        verify(registeredUserRepository, times(1)).findByEmail(originalEmail);
        verify(registeredUserRepository, times(1)).existsByEmail(updatedDTO.getEmail());
        verify(registeredUserRepository, never()).save(any());
        verify(modelMapper, never()).map(any(), any());
    }

    /**
     * Test updating a registered user when the user is not found.
     */
    @Test
    public void testUpdateRegisteredUser_NotFound() {
        // Arrange
        String originalEmail = userDTO.getEmail();
        RegisteredUserDTO updatedDTO = new RegisteredUserDTO("john.updated@example.com", "John Updated", "789 Oak St", "newpassword123");

        when(registeredUserRepository.findByEmail(originalEmail)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceAccessException exception = assertThrows(ResourceAccessException.class, () -> {
            registeredUserService.updateRegisteredUser(originalEmail, updatedDTO);
        });

        assertEquals("Registered user not found with email: " + originalEmail, exception.getMessage());
        verify(registeredUserRepository, times(1)).findByEmail(originalEmail);
        verify(registeredUserRepository, never()).existsByEmail(anyString());
        verify(registeredUserRepository, never()).save(any());
        verify(modelMapper, never()).map(any(), any());
    }

    /**
     * Test deleting a registered user successfully.
     */
    @Test
    public void testDeleteRegisteredUser_Success() {
        // Arrange
        String email = userDTO.getEmail();
        when(registeredUserRepository.existsByEmail(email)).thenReturn(true);
        doNothing().when(registeredUserRepository).deleteByEmail(email);

        // Act
        registeredUserService.deleteRegisteredUser(email);

        // Assert
        verify(registeredUserRepository, times(1)).existsByEmail(email);
        verify(registeredUserRepository, times(1)).deleteByEmail(email);
    }

    /**
     * Test deleting a registered user when the user is not found.
     */
    @Test
    public void testDeleteRegisteredUser_NotFound() {
        // Arrange
        String email = userDTO.getEmail();
        when(registeredUserRepository.existsByEmail(email)).thenReturn(false);

        // Act & Assert
        ResourceAccessException exception = assertThrows(ResourceAccessException.class, () -> {
            registeredUserService.deleteRegisteredUser(email);
        });

        assertEquals("Registered user not found with email: " + email, exception.getMessage());
        verify(registeredUserRepository, times(1)).existsByEmail(email);
        verify(registeredUserRepository, never()).deleteByEmail(anyString());
    }
}