package com.example.acmeplex.paymentsystem.model;



public class Payment {
    private String paymentMethod;
    private boolean paymentStatus;
    private int paymentId;
    private double amount;

    public static int counter=0;

    public Payment(double amount, String paymentMethod) {        
        this.amount = amount;
        this.paymentMethod = paymentMethod;

        counter++;
        this.paymentId=counter;
    }

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
