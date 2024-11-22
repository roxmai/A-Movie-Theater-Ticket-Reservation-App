package com.example.acmeplex.moviesystem.dto;

import java.sql.Timestamp;

public class ShowtimeDTO {
    private int id;
    private Timestamp startTime;
    private Timestamp endTime;
    private int tickets;
    private int ticketsSold;
    private String state;

    public ShowtimeDTO(int id, Timestamp startTime, Timestamp endTime, int tickets, int ticketsSold) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tickets = tickets;
        this.ticketsSold = ticketsSold;
    }

    public ShowtimeDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public int getTickets() {
        return tickets;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }

    public int getTicketsSold() {
        return ticketsSold;
    }

    public void setTicketsSold(int ticketsSold) {
        this.ticketsSold = ticketsSold;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
