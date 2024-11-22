package com.example.acmeplex.moviesystem.dto;

import com.example.acmeplex.moviesystem.entity.Movie;

public class MovieSimpleDTO {
    private int id;
    private String title;
    private String image;

    public MovieSimpleDTO() {
    }

    public MovieSimpleDTO(int id, String title, String image) {
        this.id = id;
        this.title = title;
        this.image = image;
    }

    public MovieSimpleDTO(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
