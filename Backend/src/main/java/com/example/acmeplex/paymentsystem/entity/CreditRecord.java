package com.example.acmeplex.paymentsystem.entity;

public class CreditRecord {

    private int creditRecordId;
    private int email;
    private double creditPoints;
    private double usedPoints;
    private double expirationDate;
    
    public CreditRecord() {
    }

    public CreditRecord(int creditRecordId, int email, double creditPoints, double usedPoints, double expirationDate) {
        this.creditRecordId = creditRecordId;
        this.email = email;
        this.creditPoints = creditPoints;
        this.usedPoints = usedPoints;
        this.expirationDate = expirationDate;
    }

    public int getCreditRecordId() {
        return this.creditRecordId;
    }

    public void setCreditRecordId(int creditRecordId) {
        this.creditRecordId = creditRecordId;
    }

    public int getEmail() {
        return this.email;
    }

    public void setEmail(int email) {
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

    public double getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(double expirationDate) {
        this.expirationDate = expirationDate;
    }
}
