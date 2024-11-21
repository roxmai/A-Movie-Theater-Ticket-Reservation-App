package com.example.acmeplex.moviesystem.exceptions;

public class DataNotFoundException extends RuntimeException{
    public DataNotFoundException(String name) {
        super(name + " not found.");
    }
}
