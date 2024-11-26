package com.example.acmeplex.usersystem.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the RegisteredUser model.
 */
class RegisteredUserModelTest {

    /**
     * Helper method to create a sample RegisteredUser instance with all fields populated.
     *
     * @return A RegisteredUser instance.
     */
    private RegisteredUser createSampleRegisteredUser() {
        String email = "john.doe@example.com";
        String name = "John Doe";
        String address = "123 Main St";
        String password = "SecurePassword123";
        
        Calendar cal = Calendar.getInstance();
        cal.set(2025, Calendar.DECEMBER, 31);
        Date subscriptionExpirationDate = cal.getTime();
        
        String creditCardInfo = "4111111111111111";
        boolean activeSubscription = true;

        return new RegisteredUser(email, name, address, password, subscriptionExpirationDate, creditCardInfo, activeSubscription);
    }

    @Test
    @DisplayName("Test Default Constructor")
    void testDefaultConstructor() {
        RegisteredUser user = new RegisteredUser();

        assertNull(user.getEmail(), "Email should be null by default");
        assertNull(user.getName(), "Name should be null by default");
        assertNull(user.getAddress(), "Address should be null by default");
        assertNull(user.getPassword(), "Password should be null by default");
        assertNull(user.getSubscriptionExpirationDate(), "SubscriptionExpirationDate should be null by default");
        assertNull(user.getCreditCardInfo(), "CreditCardInfo should be null by default");
        assertFalse(user.isActiveSubscription(), "ActiveSubscription should be false by default");
    }

    @Test
    @DisplayName("Test All-Args Constructor")
    void testAllArgsConstructor() {
        RegisteredUser user = createSampleRegisteredUser();

        assertEquals("john.doe@example.com", user.getEmail(), "Email should match the constructor argument");
        assertEquals("John Doe", user.getName(), "Name should match the constructor argument");
        assertEquals("123 Main St", user.getAddress(), "Address should match the constructor argument");
        assertEquals("SecurePassword123", user.getPassword(), "Password should match the constructor argument");
        
        Calendar cal = Calendar.getInstance();
        cal.set(2025, Calendar.DECEMBER, 31);
        Date expectedDate = cal.getTime();
        assertEquals(expectedDate, user.getSubscriptionExpirationDate(), "SubscriptionExpirationDate should match the constructor argument");
        
        assertEquals("4111111111111111", user.getCreditCardInfo(), "CreditCardInfo should match the constructor argument");
        assertTrue(user.isActiveSubscription(), "ActiveSubscription should match the constructor argument");
    }

    @Test
    @DisplayName("Test Getters and Setters")
    void testGettersAndSetters() {
        RegisteredUser user = new RegisteredUser();

        // Set values
        user.setEmail("jane.smith@example.com");
        user.setName("Jane Smith");
        user.setAddress("456 Elm St");
        user.setPassword("AnotherSecurePassword456");
        
        Calendar cal = Calendar.getInstance();
        cal.set(2024, Calendar.JUNE, 30);
        Date subscriptionDate = cal.getTime();
        
        user.setSubscriptionExpirationDate(subscriptionDate);
        user.setCreditCardInfo("4222222222222");
        user.setActiveSubscription(true);

        // Assert getters
        assertEquals("jane.smith@example.com", user.getEmail(), "Email should match the set value");
        assertEquals("Jane Smith", user.getName(), "Name should match the set value");
        assertEquals("456 Elm St", user.getAddress(), "Address should match the set value");
        assertEquals("AnotherSecurePassword456", user.getPassword(), "Password should match the set value");
        assertEquals(subscriptionDate, user.getSubscriptionExpirationDate(), "SubscriptionExpirationDate should match the set value");
        assertEquals("4222222222222", user.getCreditCardInfo(), "CreditCardInfo should match the set value");
        assertTrue(user.isActiveSubscription(), "ActiveSubscription should match the set value");
    }

    @Test
    @DisplayName("Test Active Subscription Toggle")
    void testActiveSubscriptionToggle() {
        RegisteredUser user = new RegisteredUser();

        // Initially false
        assertFalse(user.isActiveSubscription(), "ActiveSubscription should be false initially");

        // Set to true
        user.setActiveSubscription(true);
        assertTrue(user.isActiveSubscription(), "ActiveSubscription should be true after setting to true");

        // Set back to false
        user.setActiveSubscription(false);
        assertFalse(user.isActiveSubscription(), "ActiveSubscription should be false after setting to false");
    }

    @Test
    @DisplayName("Test Subscription Expiration Date Handling")
    void testSubscriptionExpirationDateHandling() {
        RegisteredUser user = new RegisteredUser();

        Calendar cal = Calendar.getInstance();
        cal.set(2023, Calendar.JANUARY, 1);
        Date date = cal.getTime();

        // Set subscription expiration date
        user.setSubscriptionExpirationDate(date);
        assertEquals(date, user.getSubscriptionExpirationDate(), "SubscriptionExpirationDate should match the set value");

        // Test with null
        user.setSubscriptionExpirationDate(null);
        assertNull(user.getSubscriptionExpirationDate(), "SubscriptionExpirationDate should be null after setting to null");
    }

    @Test
    @DisplayName("Test Password Handling")
    void testPasswordHandling() {
        RegisteredUser user = new RegisteredUser();

        String password = "StrongPassword789";
        user.setPassword(password);
        assertEquals(password, user.getPassword(), "Password should match the set value");

        // Test with null
        user.setPassword(null);
        assertNull(user.getPassword(), "Password should be null after setting to null");
    }

    @Test
    @DisplayName("Test Credit Card Info Handling")
    void testCreditCardInfoHandling() {
        RegisteredUser user = new RegisteredUser();

        String creditCardInfo = "378282246310005"; // Example of a valid AmEx card
        user.setCreditCardInfo(creditCardInfo);
        assertEquals(creditCardInfo, user.getCreditCardInfo(), "CreditCardInfo should match the set value");

        // Test with null
        user.setCreditCardInfo(null);
        assertNull(user.getCreditCardInfo(), "CreditCardInfo should be null after setting to null");
    }

    @Test
    @DisplayName("Test Email Handling from Superclass")
    void testEmailHandling() {
        RegisteredUser user = new RegisteredUser();

        String email = "alice.brown@example.com";
        user.setEmail(email);
        assertEquals(email, user.getEmail(), "Email should match the set value");

        // Test with null
        user.setEmail(null);
        assertNull(user.getEmail(), "Email should be null after setting to null");
    }
}