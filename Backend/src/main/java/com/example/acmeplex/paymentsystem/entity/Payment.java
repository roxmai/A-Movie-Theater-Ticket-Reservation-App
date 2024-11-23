package com.example.acmeplex.paymentsystem.entity;



public class Payment {
    private String email;
    private String method;
    private int id;
    private double amount;
    private String type;

    public Payment() {
    }

    public Payment(String email, String method, int id, double amount, String type) {
        this.email = email;
        this.method = method;
        this.id = id;
        this.amount = amount;
        this.type = type;
    }


    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }



   
}
