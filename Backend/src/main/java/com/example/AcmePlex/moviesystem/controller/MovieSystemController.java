package com.example.AcmePlex.moviesystem.controller;

import com.example.AcmePlex.moviesystem.model.Genre;
import com.example.AcmePlex.moviesystem.model.Theatre;
import com.example.AcmePlex.moviesystem.model.vo.MovieDetailedView;
import com.example.AcmePlex.moviesystem.model.vo.MovieSimpleView;
import com.example.AcmePlex.moviesystem.model.vo.Showtime;
import com.example.AcmePlex.moviesystem.service.MovieService;
import com.example.AcmePlex.moviesystem.service.ShowtimeService;
import com.example.AcmePlex.moviesystem.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class MovieSystemController {

    private final MovieService movieService;
    private final TheatreService theatreService;
    private final ShowtimeService showtimeService;

    @Autowired
    public MovieSystemController(MovieService movieService, TheatreService theatreService, ShowtimeService showtimeService) {
        this.movieService = movieService;
        this.theatreService = theatreService;
        this.showtimeService = showtimeService;
    }

    @GetMapping("/genres")
    public ResponseEntity<List<Genre>> getGenres() {
        return ResponseEntity.ok(movieService.getAllGenres());
    }

    @GetMapping("/movies")
    public ResponseEntity<Map<String, Object>> getMovies(@RequestParam(value = "page", defaultValue = "1")int page,
                                                         @RequestParam(value = "pageSize", defaultValue = "20")int pageSize) {
        return ResponseEntity.ok(movieService.getAllMovies(page, pageSize));
    }

    @GetMapping("/movies/genre/{genre_id}")
    public ResponseEntity<Map<String, Object>> getMoviesByCategory(@PathVariable int genre_id,
                                                                     @RequestParam("page")int page,
                                                                     @RequestParam("pageSize")int pageSize) {
        return ResponseEntity.ok(movieService.getMoviesByGenre(genre_id, page, pageSize));
    }

    @GetMapping("/movies/search")
    public ResponseEntity<Map<String, Object>> searchMoviesByTitle(@RequestParam("q") String searchQuery,
                                                                     @RequestParam("page") int page,
                                                                     @RequestParam("pageSize") int pageSize) {
        System.out.println("search query: " + searchQuery);
        return ResponseEntity.ok(movieService.getMoviesBySearch(searchQuery, page, pageSize));
    }

    @GetMapping("/movies/autocompletion/{searchQuery}")
    public ResponseEntity<List<String>> getSearchAutocompletion(@PathVariable String searchQuery) {
        return ResponseEntity.ok(movieService.getMovieSuggestionByInput(searchQuery));
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<MovieDetailedView> getMovieDetailsById(@PathVariable int id) {
        Optional<MovieDetailedView> movieDetailedView = movieService.getMovieById(id);
        return ResponseEntity.of(movieDetailedView);
    }

    @GetMapping("/theatres/movie/{movie_id}")
    public ResponseEntity<List<Theatre>> getTheatresByMovie(@PathVariable int movie_id) {
        return ResponseEntity.ok(theatreService.getTheatresByMovie(movie_id));
    }

    @GetMapping("/showtime/movie/{movie_id}/theatre/{theatre_id}")
    public ResponseEntity<List<Showtime>> getShowtimesByMovieAndTheatre(@PathVariable int movie_id, @PathVariable int theatre_id) {
        return ResponseEntity.ok(showtimeService.getShowtimeList(movie_id, theatre_id));
    }
}
