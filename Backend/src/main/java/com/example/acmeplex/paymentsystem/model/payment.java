package com.example.acmeplex.paymentsystem.model;

import java.sql.Timestamp;

public class payment {
    private int TicketId;
public class Payment {
    private String paymentMethod;
    private boolean paymentStatus;
    private Timestamp lastUpdateTime;
    private int paymentId;
    private double amount;

    public static int counter=0;

        
    public Payment(int TicketId, double amount, String paymentMethod) {
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.lastUpdateTime = new Timestamp(System.currentTimeMillis());

        counter++;
        this.paymentId=counter;
    }

    public Boolean processPayment() {
        // Process payment logic
        this.lastUpdateTime = new Timestamp(System.currentTimeMillis());
        paymentStatus = true;
        //add send email receipt logic

        return paymentStatus;
    }

    public Boolean issueRefund() {
        // Refund payment logic
        this.lastUpdateTime = new Timestamp(System.currentTimeMillis());
        paymentStatus = false;
        // add send email refund logic

        return true;
    }

   
}
