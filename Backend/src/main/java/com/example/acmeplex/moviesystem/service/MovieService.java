package com.example.acmeplex.moviesystem.service;

import com.example.acmeplex.moviesystem.config.AppProperties;
import com.example.acmeplex.moviesystem.entity.Genre;
import com.example.acmeplex.moviesystem.entity.Movie;
import com.example.acmeplex.moviesystem.dto.MovieGenreDTO;
import com.example.acmeplex.moviesystem.dto.MovieDetailedDTO;
import com.example.acmeplex.moviesystem.dto.MovieSimpleDTO;
import com.example.acmeplex.moviesystem.vo.Pagination;
import com.example.acmeplex.moviesystem.entity.Showtime;
import com.example.acmeplex.moviesystem.repository.MovieGenreRepository;
import com.example.acmeplex.moviesystem.repository.TheatreShowtimeSeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private MovieSimpleDTO convertToSimple(Movie movie) {
        MovieSimpleDTO movieSimpleDTO = new MovieSimpleDTO(movie);
        movieSimpleDTO.setImage(appProperties.getBaseUrl()+"images/posters/"+movie.getImage());
        return movieSimpleDTO;
    }

    public Map<String, Object> getAllMovies(int page, int pageSize)
    {
        Map<String, Object> moviesWithPagination= new HashMap<>();
        List<Movie> movies = movieGenreRepository.findAllMovies(page, pageSize);
        moviesWithPagination.put("movies", movies.stream().map(this::convertToSimple).toList());
        Pagination pagination = new Pagination(page, pageSize, movieGenreRepository.getMoviesCount());
        moviesWithPagination.put("pagination", pagination);
        return moviesWithPagination;
    }

    public Map<String, Object> getMoviesByGenre(int genreId, int page, int pageSize)
    {
        Map<String, Object> moviesWithPagination= new HashMap<>();
        List<Movie> movies =  movieGenreRepository.findMoviesByGenre(genreId, page, pageSize);
        moviesWithPagination.put("movies", movies.stream().map(this::convertToSimple).toList());
        Pagination pagination = new Pagination(page, pageSize, movieGenreRepository.getMoviesCountByGenre(genreId));
        moviesWithPagination.put("pagination", pagination);
        return moviesWithPagination;
    }

    public List<Genre> getAllGenres() {
        return movieGenreRepository.findAllGenres();
    }

    public Map<String, Object> getMoviesBySearch(String searchQuery, int page, int pageSize) {
        if(searchQuery.isEmpty())
        {
            return getAllMovies(page, pageSize);
        }
        Map<String, Object> moviesWithPagination = new HashMap<>();
        List<Movie> movies = movieGenreRepository.findMovieBySearch(searchQuery, page, pageSize);
        Pagination pagination = new Pagination(page, pageSize, movieGenreRepository.getMoviesCountBySearch(searchQuery));
        moviesWithPagination.put("movies", movies.stream().map(this::convertToSimple).toList());
        moviesWithPagination.put("pagination", pagination);
        return moviesWithPagination;
    }

    public Optional<MovieDetailedDTO> getMovieById(int id) {
        Optional<MovieGenreDTO> movie = movieGenreRepository.findMovieDetailsById(id);
        MovieDetailedDTO movieDetailedDTO;
        if (movie.isPresent()) {
            movieDetailedDTO = new MovieDetailedDTO(movie.get());
            List<Showtime> showtimeList = theatreShowtimeSeatRepository.findShowtimesByMovie(id);
            if(!showtimeList.isEmpty()){
                movieDetailedDTO.setHasShowtime(true);
            }
            movieDetailedDTO.setImage(appProperties.getBaseUrl()+"images/posters/"+ movieDetailedDTO.getImage());
            return Optional.of(movieDetailedDTO);
        }
        return Optional.empty();
    }

    public List<String> getMovieSuggestionByInput(String searchQuery) {
        return movieGenreRepository.findMovieSuggestions(searchQuery);
    }
}
