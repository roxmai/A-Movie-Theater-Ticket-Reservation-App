package com.example.acmeplex.moviesystem.exceptions;

public class TicketAlreadyCancelledException extends RuntimeException{
    public TicketAlreadyCancelledException() {
        super("Ticket is not active or  is already canceled.");
    }
}
