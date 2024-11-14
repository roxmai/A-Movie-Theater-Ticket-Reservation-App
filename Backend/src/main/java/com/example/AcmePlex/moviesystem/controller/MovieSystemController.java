package com.example.AcmePlex.moviesystem.controller;

import com.example.AcmePlex.moviesystem.model.Genre;
import com.example.AcmePlex.moviesystem.model.Theatre;
import com.example.AcmePlex.moviesystem.model.vo.MovieDetailedView;
import com.example.AcmePlex.moviesystem.model.vo.MovieSimpleView;
import com.example.AcmePlex.moviesystem.service.MovieService;
import com.example.AcmePlex.moviesystem.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class MovieSystemController {

    private final MovieService movieService;
    private final TheatreService theatreService;

    @Autowired
    public MovieSystemController(MovieService movieService, TheatreService theatreService) {
        this.movieService = movieService;
        this.theatreService = theatreService;
    }

    @GetMapping("/genres")
    public ResponseEntity<List<Genre>> getGenres() {
        return ResponseEntity.ok(movieService.getAllGenres());
    }

    @GetMapping("/movies")
    public ResponseEntity<List<MovieSimpleView>> getMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/movies/genre/{genre_id}")
    public ResponseEntity<List<MovieSimpleView>> getMoviesByCategory(@PathVariable int genre_id) {
        return ResponseEntity.ok(movieService.getMoviesByGenre(genre_id));
    }

    @GetMapping("/movies/search")
    public ResponseEntity<List<MovieSimpleView>> searchMoviesByTitle(@RequestParam("q") String searchQuery) {
        System.out.println("search query: " + searchQuery);
        return ResponseEntity.ok(movieService.getMoviesBySearch(searchQuery));
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<MovieDetailedView> getMovieDetails(@PathVariable int id) {
        Optional<MovieDetailedView> movieDetailedView = movieService.getMovieById(id);
        return ResponseEntity.of(movieDetailedView);
    }

    @GetMapping("/theatres/movie/{movie_id}")
    public ResponseEntity<List<Theatre>> getTheatresNyMovie(@PathVariable int movie_id) {
        return ResponseEntity.ok(theatreService.getTheatresByMovie(movie_id));
    }
}
