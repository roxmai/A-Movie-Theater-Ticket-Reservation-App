package com.example.AcmePlex.moviesystem.model.dto;

public class ShowtimeSeatDTO {
    private int id;
    private int row;
    private int column;
    private int theatreRow;
    private int theatreColumn;
    private String state;

    public ShowtimeSeatDTO(int id, int row, int column, int theatreRow, int theatreColumn, String state) {
        this.id = id;
        this.row = row;
        this.column = column;
        this.theatreRow = theatreRow;
        this.theatreColumn = theatreColumn;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
