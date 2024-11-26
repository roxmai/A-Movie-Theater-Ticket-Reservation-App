package com.example.acmeplex.usersystem.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the User model.
 */
class UserModelTest {

    /**
     * A simple concrete subclass of User for testing purposes.
     */
    private static class TestUser extends User {
        public TestUser() {
            super();
        }

        public TestUser(String email) {
            super(email);
        }
    }

    private TestUser user;

    @BeforeEach
    void setUp() {
        user = new TestUser();
    }

    @Test
    @DisplayName("Test default constructor and getters")
    void testDefaultConstructor() {
        assertNull(user.getEmail(), "Email should be null by default");
    }

    @Test
    @DisplayName("Test all-args constructor and getters")
    void testAllArgsConstructor() {
        String email = "john.doe@example.com";

        TestUser constructedUser = new TestUser(email);

        assertEquals(email, constructedUser.getEmail(), "Emails should match");
    }

    @Test
    @DisplayName("Test Email setter and getter")
    void testEmailSetterGetter() {
        String email = "alice.johnson@example.com";
        user.setEmail(email);
        assertEquals(email, user.getEmail(), "Email should be correctly set and retrieved");
    }

    @Test
    @DisplayName("Test setting and getting email property")
    void testEmailProperty() {
        String email = "bob.williams@example.com";

        user.setEmail(email);
        assertAll("User Email",
            () -> assertEquals(email, user.getEmail(), "Email should match")
        );
    }
}