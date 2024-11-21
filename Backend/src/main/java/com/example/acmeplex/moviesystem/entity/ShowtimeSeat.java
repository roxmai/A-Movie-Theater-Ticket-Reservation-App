package com.example.acmeplex.moviesystem.entity;

public class ShowtimeSeat {
    private int id;
    private int seatId;
    private int showtimeId;
    private boolean available;

    public ShowtimeSeat(int id, int seatId, int showtimeId, boolean available) {
        this.id = id;
        this.seatId = seatId;
        this.showtimeId = showtimeId;
        this.available = available;
    }

    public ShowtimeSeat() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public int getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(int showtimeId) {
        this.showtimeId = showtimeId;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
