package com.example.acmeplex.moviesystem.controller;

import com.example.acmeplex.moviesystem.entity.Genre;
import com.example.acmeplex.moviesystem.entity.Theatre;
import com.example.acmeplex.moviesystem.dto.TicketBookingDTO;
import com.example.acmeplex.moviesystem.vo.MovieDetailedView;
import com.example.acmeplex.moviesystem.vo.ShowtimeView;
import com.example.acmeplex.moviesystem.service.MovieService;
import com.example.acmeplex.moviesystem.service.ShowtimeService;
import com.example.acmeplex.moviesystem.service.TheatreService;
import com.example.acmeplex.moviesystem.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class MovieSystemController {

    private final MovieService movieService;
    private final TheatreService theatreService;
    private final ShowtimeService showtimeService;

    private final TicketService ticketService;

    @Autowired
    public MovieSystemController(MovieService movieService, TheatreService theatreService, ShowtimeService showtimeService, TicketService ticketService) {
        this.movieService = movieService;
        this.theatreService = theatreService;
        this.showtimeService = showtimeService;
        this.ticketService = ticketService;
    }

    @GetMapping("/genres")
    public ResponseEntity<List<Genre>> getGenres() {
        return ResponseEntity.ok(movieService.getAllGenres());
    }

    @CrossOrigin
    @GetMapping("/movies")
    public ResponseEntity<Map<String, Object>> getMovies(@RequestParam(value = "page", defaultValue = "1")int page,
                                                         @RequestParam(value = "pageSize", defaultValue = "10")int pageSize) {
        return ResponseEntity.ok(movieService.getAllMovies(page, pageSize));
    }

    @GetMapping("/movies/genre/{genre_id}")
    public ResponseEntity<Map<String, Object>> getMoviesByCategory(@PathVariable int genre_id,
                                                                     @RequestParam(value = "page", defaultValue = "1")int page,
                                                                     @RequestParam(value = "pageSize", defaultValue = "10")int pageSize) {
        return ResponseEntity.ok(movieService.getMoviesByGenre(genre_id, page, pageSize));
    }

    @GetMapping("/movies/search")
    public ResponseEntity<Map<String, Object>> searchMoviesByTitle(@RequestParam("q") String searchQuery,
                                                                     @RequestParam(value = "page", defaultValue = "1") int page,
                                                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        System.out.println("search query: " + searchQuery);
        return ResponseEntity.ok(movieService.getMoviesBySearch(searchQuery, page, pageSize));
    }

    @GetMapping("/movies/autocompletion/{searchQuery}")
    public ResponseEntity<List<String>> getSearchAutocompletion(@PathVariable String searchQuery) {
        return ResponseEntity.ok(movieService.getMovieSuggestionByInput(searchQuery));
    }

    @GetMapping("/movie/{id}")
    public ResponseEntity<MovieDetailedView> getMovieDetailsById(@PathVariable int id) {
        Optional<MovieDetailedView> movieDetailedView = movieService.getMovieById(id);
        return ResponseEntity.of(movieDetailedView);
    }

    @GetMapping("/theatres/movie/{movie_id}")
    public ResponseEntity<List<Theatre>> getTheatresByMovie(@PathVariable int movie_id) {
        return ResponseEntity.ok(theatreService.getTheatresByMovie(movie_id));
    }

    @GetMapping("/showtimes/movie/{movie_id}/theatre/{theatre_id}")
    public ResponseEntity<Map<String, List<ShowtimeView>>> getShowtimesByMovieAndTheatre(@PathVariable int movie_id, @PathVariable int theatre_id) {
        //need to find out whether user is logged in
        return ResponseEntity.ok(showtimeService.getShowtimeList(movie_id, theatre_id, false));
    }

    @GetMapping("/seats/theatre/{theatreId}/showtime/{showtimeId}")
    public ResponseEntity<Map<String, Object>> getSeats(@PathVariable int theatreId, @PathVariable int showtimeId) {
        Map<String, Object> response = showtimeService.getSeats(theatreId, showtimeId);
        return ResponseEntity.ofNullable(response);
    }

    @PutMapping("/cancel/{ticketNumber}")
    public ResponseEntity<Map<String, String>> cancelTicket(@PathVariable String ticketNumber) {
        return ResponseEntity.ok(ticketService.cancelTicketByTicketNumber(ticketNumber));
    }

    @PostMapping("/book")
    public ResponseEntity<String> bookTickets(@RequestBody TicketBookingDTO ticketBookingDTO) {
        System.out.println(ticketBookingDTO.getEmail());
        System.out.println(ticketBookingDTO.getIds());
        ResponseEntity<String> response = ResponseEntity.ok(ticketService.bookTickets(ticketBookingDTO.getIds(), ticketBookingDTO.getEmail()));
        System.out.println(response);
        return response;
    }
}
