package com.example.AcmePlex.moviesystem.model;

public class Movie {
    private int id;
    private int categoryId;
    private String title;
    private String description;
    private String posterPath;

    public Movie() {
    }

    public Movie(int id, int categoryId, String title, String description, String posterPath) {
        this.id = id;
        this.categoryId = categoryId;
        this.title = title;
        this.description = description;
        this.posterPath = posterPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}

