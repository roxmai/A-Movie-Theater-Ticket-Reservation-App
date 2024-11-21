package com.example.acmeplex.moviesystem.dto;

import java.util.List;

public class TicketBookingDTO {
    private List<Integer> ids;
    private String email;

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
