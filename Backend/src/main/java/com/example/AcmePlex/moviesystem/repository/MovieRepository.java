package com.example.AcmePlex.moviesystem.repository;

import com.example.AcmePlex.moviesystem.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class MovieRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MovieRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Movie> movieRowMapper = new RowMapper<Movie>() {
        @Override
        public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Movie(
                    rs.getInt("id"),
                    rs.getInt("category_id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("poster_path")
            );
        }
    };

    public Optional<Movie> findById(int id) {
        String sql = "SELECT * FROM movie WHERE id = ?";
        List<Movie> movies = jdbcTemplate.query(sql, movieRowMapper, id);
        return movies.isEmpty() ? Optional.empty() : Optional.of(movies.get(0));
    }

    public List<Movie> findAll() {
        String sql = "SELECT * FROM movie";
        return jdbcTemplate.query(sql, movieRowMapper);
    }
}
