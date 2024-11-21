package com.example.acmeplex.usersystem.controller;

import com.example.acmeplex.usersystem.dto.RegisteredUserDTO;
import com.example.acmeplex.usersystem.model.RegisteredUser;
import com.example.acmeplex.usersystem.service.RegisteredUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/registered-users")
public class RegisteredUserController {

    @Autowired
    private RegisteredUserService registeredUserService;

    /**
     * Create a new registered user.
     *
     * @param registeredUserDTO DTO containing user information.
     * @return Created user as a response.
     */
    @PostMapping
    public ResponseEntity<RegisteredUserDTO> createRegisteredUser(@Valid @RequestBody RegisteredUserDTO registeredUserDTO) {
        RegisteredUserDTO createdRegisteredUser = registeredUserService.createRegisteredUser(registeredUserDTO);
        return ResponseEntity.ok(createdRegisteredUser);
    }

    /**
     * Get registered user by ID.
     *
     * @param id ID of the user.
     * @return User details as a response.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RegisteredUserDTO> getRegisteredUserById(@PathVariable Long id) {
        RegisteredUserDTO registeredUser = registeredUserService.getRegisteredUserById(id);
        return ResponseEntity.ok(registeredUser);
    }

    /**
     * Get all registered users.
     *
     * @return List of all registered users.
     */
    @GetMapping
    public ResponseEntity<List<RegisteredUserDTO>> getAllRegisteredUsers() {
        List<RegisteredUserDTO> registeredUsers = registeredUserService.getAllRegisteredUsers();
        return ResponseEntity.ok(registeredUsers);
    }

    /**
     * Update a registered user by ID.
     *
     * @param id                 ID of the user to update.
     * @param registeredUserDTO  Updated user information.
     * @return Updated user details.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RegisteredUserDTO> updateRegisteredUser(@PathVariable Long id, @Valid @RequestBody RegisteredUserDTO registeredUserDTO) {
        RegisteredUserDTO updatedRegisteredUser = registeredUserService.updateRegisteredUser(id, registeredUserDTO);
        return ResponseEntity.ok(updatedRegisteredUser);
    }

    /**
     * Delete a registered user by ID.
     *
     * @param id ID of the user to delete.
     * @return No content response if deletion is successful.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegisteredUser(@PathVariable Long id) {
        registeredUserService.deleteRegisteredUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Find a registered user by email.
     *
     * @param email Email of the registered user.
     * @return User details as a response.
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<RegisteredUserDTO> getRegisteredUserByEmail(@PathVariable String email) {
        RegisteredUserDTO registeredUser = registeredUserService.getRegisteredUserByEmail(email);
        return ResponseEntity.ok(registeredUser);
    }
}
