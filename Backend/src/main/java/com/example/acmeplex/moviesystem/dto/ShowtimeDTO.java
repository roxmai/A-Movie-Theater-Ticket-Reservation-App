package com.example.acmeplex.moviesystem.dto;

import java.sql.Timestamp;

public class ShowtimeDTO {
    private int id;
    private int showroomId;
    private String showroomName;
    private Timestamp startTime;
    private Timestamp endTime;
    private int tickets;
    private int ticketsSold;
    private String state;
    private boolean preAnnouncement = false;

    public ShowtimeDTO(int id, int showroomId, Timestamp startTime, Timestamp endTime, int tickets, int ticketsSold) {
        this.id = id;
        this.showroomId = showroomId;
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

    public String getShowroomName() {
        return showroomName;
    }

    public void setShowroomName(String showroomName) {
        this.showroomName = showroomName;
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

    public int getShowroomId() {
        return showroomId;
    }

    public void setShowroomId(int showroomId) {
        this.showroomId = showroomId;
    }

    public boolean isPreAnnouncement() {
        return preAnnouncement;
    }

    public void setPreAnnouncement(boolean preAnnouncement) {
        this.preAnnouncement = preAnnouncement;
    }
}
