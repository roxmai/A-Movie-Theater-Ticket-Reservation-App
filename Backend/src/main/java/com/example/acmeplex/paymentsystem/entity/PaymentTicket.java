package com.example.acmeplex.paymentsystem.entity;

public class PaymentTicket {

    private int payment_id;
    private String ticket_number;
    private String status;

    public PaymentTicket() {
    }

    public PaymentTicket(int payment_id, String ticket_number, String status) {
        this.payment_id = payment_id;
        this.ticket_number= ticket_number;
        this.status = status;
    }


    public int getPayment_id() {
        return this.payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTicket_number() {
        return this.ticket_number;
    }

    public void setTicket_number(String ticket_number) {
        this.ticket_number = ticket_number;
    }



    
}
