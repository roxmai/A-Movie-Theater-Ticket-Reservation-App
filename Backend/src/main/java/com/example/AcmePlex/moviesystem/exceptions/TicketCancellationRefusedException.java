package com.example.acmeplex.moviesystem.exceptions;

public class TicketCancellationRefusedException extends RuntimeException{
    public TicketCancellationRefusedException() {
        super("Ticket cannot be cancelled within 72 hours prior to showtime.");
    }
}
