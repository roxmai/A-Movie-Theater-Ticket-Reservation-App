package com.example.acmeplex.moviesystem.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Ticket {
    private int id;
    private String ticketNumber;
    private String holderEmail;
    private BigDecimal price;
    private int movieId;
    private int theatreId;
    private int showtimeId;
    private int seatId;
    private Timestamp reservedTime;
    private Timestamp expireTime;

    public Ticket() {
    }

    public Ticket(int id, String ticketNumber, String holderEmail, BigDecimal price, int movieId, int theatreId, int showtimeId, int seatId, Timestamp reservedTime, Timestamp expireTime) {
        this.id = id;
        this.ticketNumber = ticketNumber;
        this.holderEmail = holderEmail;
        this.price = price;
        this.movieId = movieId;
        this.theatreId = theatreId;
        this.showtimeId = showtimeId;
        this.seatId = seatId;
        this.reservedTime = reservedTime;
        this.expireTime = expireTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getHolderEmail() {
        return holderEmail;
    }

    public void setHolderEmail(String holderEmail) {
        this.holderEmail = holderEmail;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getTheatreId() {
        return theatreId;
    }

    public void setTheatreId(int theatreId) {
        this.theatreId = theatreId;
    }

    public int getShowtimeId() {
        return showtimeId;
    }

    public void setShowtimeId(int showtimeId) {
        this.showtimeId = showtimeId;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public Timestamp getReservedTime() {
        return reservedTime;
    }

    public void setReservedTime(Timestamp reservedTime) {
        this.reservedTime = reservedTime;
    }

    public Timestamp getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Timestamp expireTime) {
        this.expireTime = expireTime;
    }
}
