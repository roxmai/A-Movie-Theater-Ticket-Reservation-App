package com.example.AcmePlex.moviesystem.repository;

import com.example.AcmePlex.moviesystem.model.Genre;
import com.example.AcmePlex.moviesystem.model.Movie;
import com.example.AcmePlex.moviesystem.model.dto.MovieGenreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class MovieGenreRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MovieGenreRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Movie> movieRowMapper = new RowMapper<Movie>() {
        @Override
        public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Movie(
                    rs.getInt("id"),
                    rs.getInt("genre_id"),
                    rs.getString("title"),
                    rs.getDate("release_date"),
                    rs.getString("description"),
                    rs.getString("image"),
                    rs.getInt("length")
            );
        }
    };

    private final RowMapper<MovieGenreDTO> movieCategoryDTORowMapper = new RowMapper<MovieGenreDTO>() {
        @Override
        public MovieGenreDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new MovieGenreDTO(
                    rs.getInt("id"),
                    rs.getString("genre.title"),
                    rs.getString("title"),
                    rs.getDate("release_date"),
                    rs.getString("description"),
                    rs.getString("image"),
                    rs.getInt("length")
            );
        }
    };

    private final RowMapper<Genre> genreRowMapper = new RowMapper<Genre>() {
        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Genre(rs.getInt("id"), rs.getString("title"));
        }
    };

    public List<Movie> findAllMovies() {
        String sql = "SELECT * FROM movie";
        return jdbcTemplate.query(sql, movieRowMapper);
    }

    public List<Movie> findMoviesByGenre(int genreId) {
        String sql = "SELECT * FROM movie where genre_id = ?";
        return jdbcTemplate.query(sql, movieRowMapper, genreId);
    }

    public Optional<Movie> findMovieById(int id) {
        String sql = "SELECT * FROM movie where id=?";
        List<Movie> movies = jdbcTemplate.query(sql, movieRowMapper, id);
        return movies.isEmpty() ? Optional.empty() : Optional.of(movies.get(0));
    }

    public List<Movie> findMovieBySearch(String searchQuery) {
        String sql = "SELECT * FROM movie where LOWER(title)  LIKE ?";
        String formattedSql = "%" + searchQuery.toLowerCase() + "%";
        return jdbcTemplate.query(sql, movieRowMapper, formattedSql);
    }

    public List<Genre> findAllGenres() {
        String sql = "SELECT * FROM genre";
        return jdbcTemplate.query(sql, genreRowMapper);
    }

    public Optional<MovieGenreDTO> findMovieDetailsById(int id) {
        String sql = "SELECT * FROM movie INNER JOIN genre ON genre_id=genre.id where movie.id=?";
        List<MovieGenreDTO> movies = jdbcTemplate.query(sql, movieCategoryDTORowMapper, id);
        return movies.isEmpty() ? Optional.empty() : Optional.of(movies.get(0));
    }
}
