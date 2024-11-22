package com.example.acmeplex.moviesystem.service;

import com.example.acmeplex.moviesystem.entity.Showtime;
import com.example.acmeplex.moviesystem.entity.Theatre;
import com.example.acmeplex.moviesystem.dto.ShowtimeSeatDTO;
import com.example.acmeplex.moviesystem.dto.ShowtimeDTO;
import com.example.acmeplex.moviesystem.repository.TheatreShowtimeSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ShowtimeService {
    private final TheatreShowtimeSeatRepository theatreShowtimeSeatRepository;

    @Autowired
    public ShowtimeService(TheatreShowtimeSeatRepository theatreShowtimeSeatRepository) {
        this.theatreShowtimeSeatRepository = theatreShowtimeSeatRepository;
    }


    public Map<String, List<ShowtimeDTO>> getShowtimeList(int movie_id, int theatre_id, boolean userLoggedIn) {
        List<Showtime> showtimes = theatreShowtimeSeatRepository.findShowtimesByMovieAndTheatre(movie_id, theatre_id, userLoggedIn);
        List<ShowtimeDTO> showtimeDTOS = new ArrayList<>();
        Timestamp now = Timestamp.valueOf(java.time.LocalDateTime.now());
        for (Showtime showtime : showtimes) {
            ShowtimeDTO showtimeDTO = new ShowtimeDTO(showtime.getId(), showtime.getStartTime(), showtime.getEndTime(),
                    showtime.getTickets(), showtime.getTicketsSold());
            if (showtime.getTickets()==showtime.getTicketsSold()) {
                showtimeDTO.setState("Full");
            } else if (now.after(showtime.getStartTime()) && now.before(showtime.getEndTime())) {
                showtimeDTO.setState("Playing");
            } else if (now.after(showtime.getEndTime())) {
                showtimeDTO.setState("Closed");
            } else {
                showtimeDTO.setState("Open");
            }
            showtimeDTOS.add(showtimeDTO);
        }
        Map<String, List<ShowtimeDTO>> showtimeViewsDividedByDate = new LinkedHashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        LocalDate lastDate = null;

        for (ShowtimeDTO showtimeDTO : showtimeDTOS) {
            LocalDate date = showtimeDTO.getStartTime().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            if (lastDate == null) {
                lastDate = date;
                String formattedDate = lastDate.format(formatter);
                showtimeViewsDividedByDate.put(formattedDate, new ArrayList<ShowtimeDTO>());
                ((ArrayList<ShowtimeDTO>) (showtimeViewsDividedByDate.get(formattedDate))).add(showtimeDTO);
            } else if (!lastDate.equals(date)) {
                String formattedDate = date.format(formatter);
                showtimeViewsDividedByDate.put(formattedDate, new ArrayList<ShowtimeDTO>());
                ((ArrayList<ShowtimeDTO>) (showtimeViewsDividedByDate.get(formattedDate))).add(showtimeDTO);
                lastDate = date;
            } else {
                String formattedDate = lastDate.format(formatter);
                ((ArrayList<ShowtimeDTO>) (showtimeViewsDividedByDate.get(formattedDate))).add(showtimeDTO);
            }
        }
        return showtimeViewsDividedByDate;
    }

    public Map<String, Object> getSeats(int theatre_id, int showtime_id) {
        Map<String, Object> seatsAndTheatreSize = new HashMap<>();
        Optional<Theatre> theatre = theatreShowtimeSeatRepository.findTheatreById(theatre_id);
        List<ShowtimeSeatDTO> seats = theatreShowtimeSeatRepository.findSeatsByShowtime(showtime_id);
        for (ShowtimeSeatDTO s: seats) {
            s.setState(s.getState().equals("1")?"available":"reserved");
        }
        if(theatre.isEmpty()) return null;
        seatsAndTheatreSize.put("theatreRows", theatre.get().getRows());
        seatsAndTheatreSize.put("theatreColumns", theatre.get().getColumns());
        seatsAndTheatreSize.put("seats", seats);
        return seatsAndTheatreSize;
    }
}
