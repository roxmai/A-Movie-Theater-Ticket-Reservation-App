package com.example.acmeplex.usersystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Create a sample endpoint that requires authentication
@RestController
public class TestController {
    
    @GetMapping("/api/test/register-user")
    public String userAccess() {
        return "User Content.";
    }
    
}
