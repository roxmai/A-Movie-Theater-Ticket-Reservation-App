package com.example.acmeplex.usersystem.service;

import com.example.acmeplex.usersystem.dto.UserDTO;
import com.example.acmeplex.usersystem.model.User;
import com.example.acmeplex.usersystem.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    // Logger for tracking events and debugging
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    // Injecting UserRepository to interact with the database
    @Autowired
    private UserRepository userRepository;
    
    // Injecting ModelMapper to handle DTO-Entity conversions
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Converts a User entity to its corresponding DTO.
     *
     * @param user the entity to convert
     * @return the converted UserDTO
     */
    private UserDTO convertToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    /**
     * Converts a UserDTO to its corresponding entity.
     *
     * @param userDTO the DTO to convert
     * @return the converted User entity
     */
    private User convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    /**
     * Creates a new user in the system.
     *
     * @param userDTO the DTO containing user details
     * @return the created UserDTO
     * @throws DuplicateKeyException if a user with the same email already exists
     */
    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        logger.info("Attempting to create user with email: {}", userDTO.getEmail());

        // Check if a user with the given email already exists to prevent duplicates
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            logger.error("User creation failed: Email {} is already in use.", userDTO.getEmail());
            throw new DuplicateKeyException("Email already in use: " + userDTO.getEmail());
        }

        // Convert DTO to entity
        User user = convertToEntity(userDTO);
        
        // Save the entity to the database
        User savedUser = userRepository.save(user);
        
        logger.info("User created successfully with email: {}", savedUser.getEmail());
        
        // Convert the saved entity back to DTO
        return convertToDTO(savedUser);
    }

    /**
     * Retrieves a user by their email.
     *
     * @param email the email of the user
     * @return the corresponding UserDTO
     * @throws ResourceAccessException if user is not found
     */
    public UserDTO getUserByEmail(String email) {
        logger.info("Fetching user with email: {}", email);
        
        // Fetch the user or throw an exception if not found
        User user = userRepository.findByEmail(email)
                          .orElseThrow(() -> {
                              logger.error("User not found with email: {}", email);
                              return new ResourceAccessException("User not found with email: " + email);
                          });
        
        logger.info("User fetched successfully with email: {}", email);
        
        // Convert the entity to DTO
        return convertToDTO(user);
    }

    /**
     * Retrieves all users in the system.
     *
     * @return a list of UserDTOs
     */
    public List<UserDTO> getAllUsers() {
        logger.info("Fetching all users");
        
        // Fetch all users and convert each to DTO
        List<UserDTO> users = userRepository.findAll()
                             .stream()
                             .map(this::convertToDTO)
                             .collect(Collectors.toList());
        
        logger.info("Fetched {} users successfully", users.size());
        
        return users;
    }

    /**
     * Deletes a user from the system.
     *
     * @param email the email of the user to delete
     * @throws ResourceAccessException if user is not found
     */
    @Transactional
    public void deleteUser(String email) {
        logger.info("Attempting to delete user with email: {}", email);
        
        // Check if the user exists before attempting deletion
        if (!userRepository.existsByEmail(email)) {
            logger.error("User deletion failed: User not found with email: {}", email);
            throw new ResourceAccessException("User not found with email: " + email);
        }
        
        // Delete the user by email
        userRepository.deleteByEmail(email);
        
        logger.info("User deleted successfully with email: {}", email);
    }
}