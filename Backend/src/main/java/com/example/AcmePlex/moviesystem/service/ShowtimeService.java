package com.example.AcmePlex.moviesystem.service;

import com.example.AcmePlex.moviesystem.model.vo.Showtime;
import com.example.AcmePlex.moviesystem.repository.TheatreShowtimeSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowtimeService {
    private final TheatreShowtimeSeatRepository theatreShowtimeSeatRepository;

    @Autowired
    public ShowtimeService(TheatreShowtimeSeatRepository theatreShowtimeSeatRepository) {
        this.theatreShowtimeSeatRepository = theatreShowtimeSeatRepository;
    }


    public List<Showtime> getShowtimeList(int movie_id, int theatre_id) {
        return theatreShowtimeSeatRepository.findShowtimesByMovieAndTheatre(movie_id, theatre_id);
    }
}
