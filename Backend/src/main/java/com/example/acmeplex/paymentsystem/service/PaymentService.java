package com.example.acmeplex.paymentsystem.service;

public class PaymentService {

    public Boolean processPayment() {
        // Process payment logic
        paymentStatus = true;
        //add send email receipt logic

        return paymentStatus;
    }

    public Boolean issueRefund() {
        // Refund payment logic
        paymentStatus = false;
        // add send email refund logic

        return true;
    }
    
}
