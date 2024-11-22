package com.example.acmeplex.paymentsystem.entity;

import java.util.Date;

public class CreditRecord {

    private int id;
    private String email;
    private double creditPoints;
    private double usedPoints;
    private Date expirationDate;

    public CreditRecord() {
    }

    public CreditRecord(int id, String email, double creditPoints, double usedPoints, Date expirationDate) {
        this.id = id;
        this.email = email;
        this.creditPoints = creditPoints;
        this.usedPoints = usedPoints;
        this.expirationDate = expirationDate;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getCreditPoints() {
        return this.creditPoints;
    }

    public void setCreditPoints(double creditPoints) {
        this.creditPoints = creditPoints;
    }

    public double getUsedPoints() {
        return this.usedPoints;
    }

    public void setUsedPoints(double usedPoints) {
        this.usedPoints = usedPoints;
    }

    public Date getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
