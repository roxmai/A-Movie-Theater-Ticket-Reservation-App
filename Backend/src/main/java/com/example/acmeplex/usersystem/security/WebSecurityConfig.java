package com.example.acmeplex.usersystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    private JwtAuthEntryPoint unauthorizedHandler;

    @Bean
    public JwtAuthTokenFilter authenticationJwtTokenFilter() {
        return new JwtAuthTokenFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
            .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
            .and()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests()
                // Authentication endpoints
                .requestMatchers("/api/auth/**").permitAll()
                
                // User Registration - Allow public access
                .requestMatchers(HttpMethod.POST, "/api/users/**").permitAll()
                
                // User-related GET endpoints - Require authentication
                .requestMatchers(HttpMethod.GET, "/api/users/**").authenticated()
                
                // User Deletion - Restrict to ADMIN role
                .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")
                
                // Registered User Registration - Allow public access if applicable
                .requestMatchers(HttpMethod.POST, "/api/registered-users/**").permitAll()
                
                // Registered User-related GET endpoints - Require authentication
                .requestMatchers(HttpMethod.GET, "/api/registered-users/**").authenticated()
                
                // All other registration-related endpoints (e.g., PUT for updating) - Require authentication
                .requestMatchers(HttpMethod.PUT, "/api/registered-users/**").authenticated()
                
                // Movie System Public GET Endpoints
                .requestMatchers(HttpMethod.GET, "/genres/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/movies/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/theatres/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/showtimes/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/seats/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/movies/autocompletion/**").permitAll()
                
                // Movie System Protected Endpoints
                .requestMatchers(HttpMethod.POST, "/book/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/ticketpayment/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/cancel/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/membershippayment/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/issuerefund/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/totalprice/**").authenticated()
                
                // Payment Endpoints - Require authentication
                .requestMatchers("/ticketpayment/**").authenticated()
                .requestMatchers("/membershippayment/**").authenticated()
                .requestMatchers("/issuerefund/**").authenticated()
                .requestMatchers("/totalprice/**").authenticated()
                
                // Any other request requires authentication
                .anyRequest().authenticated();

        // Add JWT token filter
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}