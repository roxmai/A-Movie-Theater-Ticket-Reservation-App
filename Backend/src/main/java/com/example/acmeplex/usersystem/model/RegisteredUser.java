package com.example.acmeplex.usersystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.util.Date;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "registered_user")
public class RegisteredUser extends User {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String password;

    @Column(name = "subscription_expiration_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date subscriptionExpirationDate;

    // Assuming you still need creditCardInfo and activeSubscription
    @Column(name = "credit_card_info")
    private String creditCardInfo;

    @Column(name = "active_subscription")
    private boolean activeSubscription;

    // Default constructor
    public RegisteredUser() {}

    // All-args constructor
    public RegisteredUser(String email, String name, String address, String password, Date subscriptionExpirationDate,
                          String creditCardInfo, boolean activeSubscription) {
        super(email);
        this.name = name;
        this.address = address;
        this.password = password;
        this.subscriptionExpirationDate = subscriptionExpirationDate;
        this.creditCardInfo = creditCardInfo;
        this.activeSubscription = activeSubscription;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

	public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public Date getSubscriptionExpirationDate() {
        return subscriptionExpirationDate;
    }

    public void setSubscriptionExpirationDate(Date subscriptionExpirationDate) {
        this.subscriptionExpirationDate = subscriptionExpirationDate;
    }

    public String getCreditCardInfo() {
        return creditCardInfo;
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