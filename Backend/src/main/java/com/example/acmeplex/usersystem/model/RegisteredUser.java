package com.example.acmeplex.usersystem.model;

import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "registered_users")
public class RegisteredUser extends User {

    @Embedded
    @AttributeOverrides({
        // Attribute overrides based on specific PaymentInfo implementations
    })
    private PaymentInfoVO paymentInfo;

    private boolean activeSubscription;
    // Default constructor
    public RegisteredUser() {}

    // All-args constructor
    public RegisteredUser(String name, String email, String address, PaymentInfoVO paymentInfo, boolean activeSubscription) {
        super(name, email, address);
        this.paymentInfo = paymentInfo;
        this.activeSubscription = activeSubscription;
    }

    // Getters and Setters
    public PaymentInfoVO getPaymentInfo() {
        return paymentInfo;
    }

    public void setCreditCardInfo(String creditCardInfo) {
        this.creditCardInfo = creditCardInfo;
    }

    public boolean isActiveSubscription() {
        return activeSubscription;
    }

    public void setActiveSubscription(boolean activeSubscription) {
        this.activeSubscription = activeSubscription;
    }
}
