package com.example.acmeplex.paymentsystem.entity;

public class PaymentTicket {

    private int payment_id;
    private int ticket_id;
    private String status;

    public PaymentTicket() {
    }

    public PaymentTicket(int payment_id, int ticket_id, String status) {
        this.payment_id = payment_id;
        this.ticket_id = ticket_id;
        this.status = status;
    }


    public int getPayment_id() {
        return this.payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public int getTicket_id() {
        return this.ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    
}
