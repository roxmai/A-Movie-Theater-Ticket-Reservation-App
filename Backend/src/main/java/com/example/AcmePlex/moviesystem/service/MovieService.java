package com.example.AcmePlex.moviesystem.service;

import com.example.AcmePlex.moviesystem.config.AppProperties;
import com.example.AcmePlex.moviesystem.model.Genre;
import com.example.AcmePlex.moviesystem.model.Movie;
import com.example.AcmePlex.moviesystem.model.dto.MovieGenreDTO;
import com.example.AcmePlex.moviesystem.model.vo.MovieDetailedView;
import com.example.AcmePlex.moviesystem.model.vo.MovieSimpleView;
import com.example.AcmePlex.moviesystem.model.vo.Showtime;
import com.example.AcmePlex.moviesystem.repository.MovieGenreRepository;
import com.example.AcmePlex.moviesystem.repository.TheatreShowtimeSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieGenreRepository movieGenreRepository;
    private final TheatreShowtimeSeatRepository theatreShowtimeSeatRepository;
    private final AppProperties appProperties;

    @Autowired
    public MovieService(MovieGenreRepository movieGenreRepository, TheatreShowtimeSeatRepository theatreShowtimeSeatRepository, AppProperties appProperties) {
        this.movieGenreRepository = movieGenreRepository;
        this.theatreShowtimeSeatRepository = theatreShowtimeSeatRepository;
        this.appProperties = appProperties;
    }

    private MovieSimpleView convertToSimple(Movie movie) {
        MovieSimpleView movieSimpleView = new MovieSimpleView(movie);
        movieSimpleView.setImage(appProperties.getBaseUrl()+"images/posters/"+movie.getImage());
        return movieSimpleView;
    }

    public List<MovieSimpleView> getAllMovies(int page, int pageSize)
    {
        List<Movie> movies = movieGenreRepository.findAllMovies(page, pageSize);
        return movies.stream().map(this::convertToSimple).toList();
    }

    public List<MovieSimpleView> getMoviesByGenre(int genreId, int page, int pageSize)
    {
        List<Movie> movies =  movieGenreRepository.findMoviesByGenre(genreId, page, pageSize);
        return movies.stream().map(this::convertToSimple).toList();
    }

    public List<Genre> getAllGenres() {
        return movieGenreRepository.findAllGenres();
    }

    public List<MovieSimpleView> getMoviesBySearch(String searchQuery, int page, int pageSize) {
        if(searchQuery.isEmpty())
        {
            return getAllMovies(page, pageSize);
        }
        List<Movie> movies = movieGenreRepository.findMovieBySearch(searchQuery, page, pageSize);
        return movies.stream().map(this::convertToSimple).toList();
    }

    public Optional<MovieDetailedView> getMovieById(int id) {
        Optional<MovieGenreDTO> movie = movieGenreRepository.findMovieDetailsById(id);
        MovieDetailedView movieDetailedView;
        if (movie.isPresent()) {
            movieDetailedView = new MovieDetailedView(movie.get());
            List<Showtime> showtimeList = theatreShowtimeSeatRepository.findShowtimesByMovie(id);
            if(!showtimeList.isEmpty()){
                movieDetailedView.setHasShowtime(true);
            }
            return Optional.of(movieDetailedView);
        }
        return Optional.empty();
    }

    public List<String> getMovieSuggestionByInput(String searchQuery) {
        return movieGenreRepository.findMovieSuggestions(searchQuery);
    }
}
