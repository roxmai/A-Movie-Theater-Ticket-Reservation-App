package com.example.acmeplex.moviesystem.service;

import com.example.acmeplex.moviesystem.config.AppProperties;
import com.example.acmeplex.moviesystem.dto.MovieNewsDTO;
import com.example.acmeplex.moviesystem.entity.Showroom;
import com.example.acmeplex.moviesystem.entity.Showtime;
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
    private final AppProperties appProperties;

    public ShowtimeService(TheatreShowtimeSeatRepository theatreShowtimeSeatRepository, AppProperties appProperties) {
        this.theatreShowtimeSeatRepository = theatreShowtimeSeatRepository;
        this.appProperties = appProperties;
    }


    public Map<String, List<ShowtimeDTO>> getShowtimeList(int movie_id, int theatre_id, boolean userLoggedIn) {
        List<Showtime> showtimes = theatreShowtimeSeatRepository.findShowtimesByMovieAndTheatre(movie_id, theatre_id, userLoggedIn);
        List<ShowtimeDTO> showtimeDTOS = new ArrayList<>();
        Timestamp now = Timestamp.valueOf(java.time.LocalDateTime.now());
        for (Showtime showtime : showtimes) {
            ShowtimeDTO showtimeDTO = new ShowtimeDTO(showtime.getId(), showtime.getShowroomId(), showtime.getStartTime(), showtime.getEndTime(),
                    showtime.getTickets(), showtime.getTicketsSold());
            // set showroom name
            Optional<Showroom> showroom = theatreShowtimeSeatRepository.findShowroomById(showtime.getShowroomId());
            showroom.ifPresent(value -> showtimeDTO.setShowroomName(value.getName()));

            //set state
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

            if(showtime.getPublicAnnouncementTime().after(now)) {
                int availableTickets = (int)Math.ceil(showtimeDTO.getTickets()*0.1);
                if(showtime.getTicketsSold()==availableTickets) showtimeDTO.setState("Full");
                showtimeDTO.setPreAnnouncement(true);
                showtimeDTO.setTickets(availableTickets);
            }
        }

        //sort by date
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

    public Map<String, Object> getSeats(int showroomId, int showtime_id) {
        Map<String, Object> seatsAndShowroomSize = new HashMap<>();
        Optional<Showroom> showroom = theatreShowtimeSeatRepository.findShowroomById(showroomId);
        List<ShowtimeSeatDTO> seats = theatreShowtimeSeatRepository.findSeatsByShowtime(showtime_id);
        System.out.println(seats);
        for (ShowtimeSeatDTO s: seats) {
            s.setState(s.getState().equals("1")?"available":"reserved");
        }
        if(showroom.isPresent()) {
            seatsAndShowroomSize.put("rows", showroom.get().getRows());
            seatsAndShowroomSize.put("columns", showroom.get().getColumns());
        }
        seatsAndShowroomSize.put("seats", seats);
        return seatsAndShowroomSize;
    }

    public List<MovieNewsDTO> getMovieNews() {
        List<MovieNewsDTO> movieNewsList = theatreShowtimeSeatRepository.findShowtimesBeforeAnnouncement();
        Collections.shuffle(movieNewsList);
        if (movieNewsList.size() > 5) {
            movieNewsList = movieNewsList.subList(0, 5);
        }
        for(MovieNewsDTO movieNews : movieNewsList) {
            movieNews.setImage(appProperties.getBaseUrl()+"images/posters/"+ movieNews.getImage());
        }
        return movieNewsList;
    }
}
