package com.example.acmeplex.usersystem.service;

import com.example.acmeplex.usersystem.dto.UserDTO;
import com.example.acmeplex.usersystem.model.User;
import com.example.acmeplex.usersystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.client.ResourceAccessException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserService userService;

    private UserDTO userDTO;
    private User user;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO("test@example.com");
        user = new User();
        user.setEmail("test@example.com");
    }

    /**
     * Test creating a user successfully when the email does not already exist.
     */
    @Test
    void createUser_Success() {
        // Arrange
        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(false);
        when(modelMapper.map(userDTO, User.class)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

        // Act
        UserDTO createdUserDTO = userService.createUser(userDTO);

        // Assert
        assertNotNull(createdUserDTO);
        assertEquals(userDTO.getEmail(), createdUserDTO.getEmail());

        // Verify interactions
        verify(userRepository, times(1)).existsByEmail(userDTO.getEmail());
        verify(modelMapper, times(1)).map(userDTO, User.class);
        verify(userRepository, times(1)).save(user);
        verify(modelMapper, times(1)).map(user, UserDTO.class);
    }

    /**
     * Test creating a user fails with DuplicateKeyException when email already exists.
     */
    @Test
    void createUser_DuplicateEmail_ThrowsException() {
        // Arrange
        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(true);

        // Act & Assert
        DuplicateKeyException exception = assertThrows(DuplicateKeyException.class, () -> {
            userService.createUser(userDTO);
        });

        assertEquals("Email already in use: " + userDTO.getEmail(), exception.getMessage());

        // Verify interactions
        verify(userRepository, times(1)).existsByEmail(userDTO.getEmail());
        verify(userRepository, never()).save(any(User.class));
        verify(modelMapper, never()).map(any(UserDTO.class), eq(User.class));
    }

    /**
     * Test creating a user when ModelMapper fails.
     */
    @Test
    void createUser_ModelMapperThrowsException() {
        // Arrange
        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(false);
        when(modelMapper.map(userDTO, User.class)).thenThrow(new RuntimeException("ModelMapper failed"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.createUser(userDTO);
        });

        assertEquals("ModelMapper failed", exception.getMessage());

        // Verify interactions
        verify(userRepository, times(1)).existsByEmail(userDTO.getEmail());
        verify(modelMapper, times(1)).map(userDTO, User.class);
        verify(userRepository, never()).save(any(User.class));
    }

    /**
     * Test creating a user when repository save fails.
     */
    @Test
    void createUser_SaveFails_ThrowsException() {
        // Arrange
        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(false);
        when(modelMapper.map(userDTO, User.class)).thenReturn(user);
        when(userRepository.save(user)).thenThrow(new RuntimeException("Database save failed"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.createUser(userDTO);
        });

        assertEquals("Database save failed", exception.getMessage());

        // Verify interactions
        verify(userRepository, times(1)).existsByEmail(userDTO.getEmail());
        verify(modelMapper, times(1)).map(userDTO, User.class);
        verify(userRepository, times(1)).save(user);
        verify(modelMapper, never()).map(any(User.class), eq(UserDTO.class));
    }

    /**
     * Test creating a user and verify that UserRepository.save is called with correct parameters.
     */
    @Test
    void createUser_VerifySaveParameters() {
        // Arrange
        when(userRepository.existsByEmail(userDTO.getEmail())).thenReturn(false);
        when(modelMapper.map(userDTO, User.class)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

        // Act
        userService.createUser(userDTO);

        // Assert using ArgumentCaptor
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        assertEquals(userDTO.getEmail(), userCaptor.getValue().getEmail());
    }
}