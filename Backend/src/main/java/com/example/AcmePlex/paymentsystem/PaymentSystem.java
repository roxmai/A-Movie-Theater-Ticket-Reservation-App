package com.example.acmeplex.model.paymentsystem;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class PaymentSystem {
    private int TicketId;
    private int paymentId;
    private double amount;
    private String paymentMethod;
    private boolean paymentStatus;
    private String lastUpdateTime;

    public PaymentSystem(int TicketId, double amount, String paymentMethod) {
        this.lastUpdateTime = getCurrentDateTime();
        this.paymentId=TicketId*10+1;
        this.TicketId = TicketId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

    public Boolean processPayment() {
        // Process payment logic
        this.lastUpdateTime = getCurrentDateTime();
        paymentStatus = true;

        return paymentStatus;
    }

    public Boolean refundPayment() {
        // Refund payment logic
        this.lastUpdateTime = getCurrentDateTime();
        paymentStatus = false;
        return true;
    }

    private String getCurrentDateTime(){
        DateTime dt = new DateTime();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String dtStr = fmt.print(dt);
        return dtStr;
    }
}
