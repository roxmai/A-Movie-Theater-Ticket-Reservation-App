package com.example.acmeplex.moviesystem.dto;

public class ShowtimeSeatDTO {
    private int id;
    private int row;
    private int column;
    private int showroomRow;
    private int showroomColumn;
    private String state;

    public ShowtimeSeatDTO(int id, int row, int column, int showroomRow, int showroomColumn, String state) {
        this.id = id;
        this.row = row;
        this.column = column;
        this.showroomRow = showroomRow;
        this.showroomColumn = showroomColumn;
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

    public int getShowroomRow() {
        return showroomRow;
    }

    public void setTheatreRow(int theatreRow) {
        this.showroomRow = theatreRow;
    }

    public int getShowroomColumn() {
        return showroomColumn;
    }

    public void setTheatreColumn(int theatreColumn) {
        this.showroomColumn = theatreColumn;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "ShowtimeSeatDTO{" +
                "id=" + id +
                ", row=" + row +
                ", column=" + column +
                ", showroomRow=" + showroomRow +
                ", showroomColumn=" + showroomColumn +
                ", state='" + state + '\'' +
                '}';
    }
}
