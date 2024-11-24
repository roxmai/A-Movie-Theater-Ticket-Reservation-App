package com.example.acmeplex.moviesystem.entity;

public class Showroom {
    private int id;
    private int theatreId;
    private String name;
    private int rows;
    private int columns;
    private int seats;

    public Showroom(int id, int theatreId, String name, int rows, int columns, int seats) {
        this.id = id;
        this.theatreId = theatreId;
        this.name = name;
        this.rows = rows;
        this.columns = columns;
        this.seats = seats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTheatreId() {
        return theatreId;
    }

    public void setTheatreId(int theatreId) {
        this.theatreId = theatreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
}
