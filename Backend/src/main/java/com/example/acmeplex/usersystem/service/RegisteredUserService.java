package com.example.acmeplex.usersystem.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.example.acmeplex.usersystem.dto.RegisteredUserDTO;
import com.example.acmeplex.usersystem.model.RegisteredUser;
import com.example.acmeplex.usersystem.repository.RegisteredUserRepository;

@Service
public class RegisteredUserService {

    // Injecting the repository to interact with the database
    @Autowired
    private RegisteredUserRepository registeredUserRepository;
    
    // Injecting ModelMapper to handle DTO-Entity conversions
    @Autowired
    private ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     * Converts a RegisteredUser entity to its corresponding DTO.
     *
     * @param registeredUser the entity to convert
     * @return the converted DTO
     */
    private RegisteredUserDTO convertToDTO(RegisteredUser registeredUser) {
        return modelMapper.map(registeredUser, RegisteredUserDTO.class);
    }

    /**
     * Converts a RegisteredUserDTO to its corresponding entity.
     *
     * @param registeredUserDTO the DTO to convert
     * @return the converted entity
     */
    private RegisteredUser convertToEntity(RegisteredUserDTO registeredUserDTO) {
        return modelMapper.map(registeredUserDTO, RegisteredUser.class);
    }

    /**
     * Creates a new registered user in the system.
     *
     * @param registeredUserDTO the DTO containing user details
     * @return the created RegisteredUserDTO
     */

    public RegisteredUserDTO createRegisteredUser(RegisteredUserDTO registeredUserDTO) {
        // Convert DTO to entity
        RegisteredUser registeredUser = convertToEntity(registeredUserDTO);
        
        // Save the entity to the database
        RegisteredUser savedRegisteredUser = registeredUserRepository.save(registeredUser);
        
        // Convert the saved entity back to DTO
        return convertToDTO(savedRegisteredUser);
    }



    public RegisteredUserDTO getRegisteredUserById(Long id) {
        // Fetch the user or throw an exception if not found
        RegisteredUser registeredUser = registeredUserRepository.findById(id)
                                  .orElseThrow(() -> new ResourceAccessException("Registered User not found with id: " + id));
        
        // Convert the entity to DTO
        return convertToDTO(registeredUser);
    }

    /**
     * Retrieves all registered users in the system.
     *
     * @return a list of RegisteredUserDTOs
     */

    public List<RegisteredUserDTO> getAllRegisteredUsers() {
        // Fetch all users and convert each to DTO
        return registeredUserRepository.findAll()
                             .stream()
                             .map(this::convertToDTO)
                             .collect(Collectors.toList());
    }



    public RegisteredUserDTO updateRegisteredUser(Long id, RegisteredUserDTO registeredUserDTO) {
        // Fetch the existing user or throw an exception if not found
        RegisteredUser existingRegisteredUser = registeredUserRepository.findById(id)
                                 .orElseThrow(() -> new ResourceAccessException("Registered User not found with id: " + id));
        
        // Update the user's details
        existingRegisteredUser.setName(registeredUserDTO.getName());
        existingRegisteredUser.setEmail(registeredUserDTO.getEmail());
        existingRegisteredUser.setAddress(registeredUserDTO.getAddress());
        existingRegisteredUser.setCreditCardInfo(registeredUserDTO.getCreditCardInfo());
        existingRegisteredUser.setActiveSubscription(registeredUserDTO.isActiveSubscription());
        
        // Save the updated user to the database
        RegisteredUser updatedRegisteredUser = registeredUserRepository.save(existingRegisteredUser);
        
        // Convert the updated entity back to DTO
        return convertToDTO(updatedRegisteredUser);
    }


    public void deleteRegisteredUser(Long id) {
        // Check if the user exists before attempting deletion
        if (!registeredUserRepository.existsById(id)) {
            throw new ResourceAccessException("Registered User not found with id: " + id);
        }
        
        // Delete the user by ID
        registeredUserRepository.deleteById(id);
    }

    public RegisteredUserDTO getRegisteredUserByEmail(String email) {
        logger.info("Fetching user with email: {}", email);
        // Fetch the user by email or throw an exception if not found
        RegisteredUser user = registeredUserRepository.findByEmail(email).orElseThrow(() -> {
            logger.error("User not found with email: {}", email);
            return new ResourceAccessException("User not found with email: " + email);});
        logger.info("User fetched successfully with email: {}", email);
        // Convert the entity to DTO
        return convertToDTO(user);}

    public boolean validRegisteredUser(String email) {
        if (registeredUserRepository.existsByEmail(email)) {
            Date today=new Date();
            if (registeredUserRepository.getExpirationDate(email).compareTo(today) >= 0) {
                return true;
            }
        }            
        return false;
    }

    public void registrationRenewal(String email) {
        Date currentExpirationDate = registeredUserRepository.getExpirationDate(email);
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentExpirationDate);
        cal.add(Calendar.YEAR, 1);
        Date newExpirationDate = cal.getTime();

        registeredUserRepository.updateExpirationDate(email, newExpirationDate);
    }
}
