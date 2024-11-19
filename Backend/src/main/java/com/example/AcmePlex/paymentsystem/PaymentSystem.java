package com.example.acmeplex.paymentsystem;

public class PaymentSystem {
    private int paymentId;
    private double amount;
    private String paymentMethod;
    private boolean paymentStatus;

    public PaymentSystem(int paymentId, double amount, String paymentMethod) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

    public Boolean processPayment() {
        // Process payment logic
        paymentStatus = true;
        return paymentStatus;
    }

    public Boolean refundPayment() {
        // Refund payment logic
        paymentStatus = false;
        return true;
    }
}
