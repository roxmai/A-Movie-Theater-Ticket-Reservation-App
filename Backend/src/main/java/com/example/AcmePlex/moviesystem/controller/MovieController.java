package com.example.AcmePlex.moviesystem.controller;

import com.example.AcmePlex.moviesystem.model.Movie;
import com.example.AcmePlex.moviesystem.repository.MovieRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MovieController {

    private final MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping("/categories")
    public String getCategories()
    {
        return "list of categories";
    }

    @GetMapping("/movies/")
    public ResponseEntity<List<Movie>> getMovies() {
        List<Movie> movies = movieRepository.findAll();
        return ResponseEntity.ok().body(movies);
    }

    @GetMapping("/movies/category/{category_id}")
    public String getMoviesByCategory(@PathVariable int category_id) {
        return "movies with category_id = " + category_id;
    }
}
