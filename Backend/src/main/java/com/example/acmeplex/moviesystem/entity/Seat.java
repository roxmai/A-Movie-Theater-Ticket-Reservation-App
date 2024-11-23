package com.example.acmeplex.moviesystem.entity;

public class Seat {
    private int id;
    private int showroomId;
    private int row;
    private int column;
    private int theatreRow;
    private int theatreColumn;

    public Seat() {
    }

    public Seat(int id, int showroomId, int row, int column, int theatreRow, int theatreColumn) {
        this.id = id;
        this.showroomId = showroomId;
        this.row = row;
        this.column = column;
        this.theatreRow = theatreRow;
        this.theatreColumn = theatreColumn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTheatreId() {
        return showroomId;
    }

    public void setTheatreId(int theatreId) {
        this.showroomId = theatreId;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getTheatreRow() {
        return theatreRow;
    }

    public void setTheatreRow(int theatreRow) {
        this.theatreRow = theatreRow;
    }

    public int getTheatreColumn() {
        return theatreColumn;
    }

    public void setTheatreColumn(int theatreColumn) {
        this.theatreColumn = theatreColumn;
    }
}

