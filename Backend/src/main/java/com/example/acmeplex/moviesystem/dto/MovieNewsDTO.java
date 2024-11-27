package com.example.acmeplex.moviesystem.dto;

import java.sql.Timestamp;

public class MovieNewsDTO {
    private int movieId;
    private String image;
    private String movieTitle;
    private Timestamp startTime;
    private Timestamp endTime;
    private String theatreName;

    public MovieNewsDTO(int movieId, String image, String movieTitle, Timestamp startTime, Timestamp endTime, String theatreName) {
        this.movieId = movieId;
        this.image = image;
        this.movieTitle = movieTitle;
        this.startTime = startTime;
        this.endTime = endTime;
        this.theatreName = theatreName;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
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

    public String getTheatreName() {
        return theatreName;
    }

    public void setTheatreName(String theatreName) {
        this.theatreName = theatreName;
    }
}
