package com.example.acmeplex.usersystem.payload;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String email;
    private Collection<? extends GrantedAuthority> roles;

    public JwtResponse(String accessToken, String email, Collection<? extends GrantedAuthority> roles) {
        this.token = accessToken;
        this.email = email;
        this.roles = roles;
    }

    // Getters and Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Collection<? extends GrantedAuthority> getRoles() { return roles; }
    public void setRoles(Collection<? extends GrantedAuthority> roles) { this.roles = roles; }
}