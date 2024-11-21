package com.example.acmeplex.moviesystem.exceptions;

public class OperationFailedException extends RuntimeException{
    public OperationFailedException(String name) {
        super(name + " operation failed.");
    }
}
