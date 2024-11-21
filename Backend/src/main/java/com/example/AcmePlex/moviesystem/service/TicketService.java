package com.example.acmeplex.moviesystem.service;

import com.example.acmeplex.moviesystem.exceptions.DataNotFoundException;
import com.example.acmeplex.moviesystem.exceptions.OperationFailedException;
import com.example.acmeplex.moviesystem.exceptions.TicketCancellationRefusedException;
import com.example.acmeplex.moviesystem.model.Showtime;
import com.example.acmeplex.moviesystem.model.ShowtimeSeat;
import com.example.acmeplex.moviesystem.model.Ticket;
import com.example.acmeplex.moviesystem.repository.TheatreShowtimeSeatRepository;
import com.example.acmeplex.moviesystem.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class TicketService {

    private final TheatreShowtimeSeatRepository theatreShowtimeSeatRepository;
    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TheatreShowtimeSeatRepository theatreShowtimeSeatRepository, TicketRepository ticketRepository) {
        this.theatreShowtimeSeatRepository = theatreShowtimeSeatRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public Map<String, String> bookTickets(List<Integer> showtimeSeats, String email) {
        Map<String, String> response = new HashMap<>();
        try {
            for(Integer showtimeSeatId : showtimeSeats) {
                int result1 = theatreShowtimeSeatRepository.updateSeatAvailability(showtimeSeatId, false);
                int result2 = ticketRepository.updateTicketReservation(showtimeSeatId, email, Timestamp.valueOf(LocalDateTime.now()));
                if(result1==0 || result2==0) throw new OperationFailedException("Ticket booking");
            }
            response.put("success", "Tickets booked successfully.");
            return response;
        } catch (RuntimeException exception) {
            response.put("error", exception.getMessage());
            return response;
        }
    }

    @Transactional
    public Map<String, String> cancelTicketByTicketNumber(String ticketNumber) {
        Map<String, String> response = new HashMap<>();
        try {
            int result = ticketRepository.updateTicketReservationByTicketNumber(ticketNumber, null, null);
            if (result==0) throw new OperationFailedException("Ticket cancellation");
            Optional<Ticket> ticket = ticketRepository.findByTicketNumber(ticketNumber);
            if(ticket.isEmpty()) throw new DataNotFoundException("Ticket");
            Optional<ShowtimeSeat> showtimeSeat = theatreShowtimeSeatRepository.findShowtimeSeatById(ticket.get().getId());
            if(showtimeSeat.isEmpty()) throw new DataNotFoundException("Showtime Seat");
            Optional<Showtime> showtime  = theatreShowtimeSeatRepository.findShowtimeById(showtimeSeat.get().getId());
            if(showtime.isEmpty()) throw new DataNotFoundException("Showtime");

            Timestamp now = Timestamp.valueOf(LocalDateTime.now());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.add(Calendar.HOUR, 72);
            Timestamp timeAfter = new Timestamp(calendar.getTimeInMillis());
            if(timeAfter.after(showtime.get().getStartTime())) {
                throw new TicketCancellationRefusedException();
            }

            result =  theatreShowtimeSeatRepository.updateSeatAvailability(ticket.get().getId(), true);
            if(result==0) throw new OperationFailedException("Set Seat Availability");

            response.put("success", "Ticket cancelled");
            return response;
        } catch (RuntimeException exception) {
            response.put("error", exception.getMessage());
            return response;
        }
    }
}
