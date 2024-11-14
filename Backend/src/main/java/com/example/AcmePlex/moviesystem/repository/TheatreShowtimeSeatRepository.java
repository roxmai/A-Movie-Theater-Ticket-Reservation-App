package com.example.AcmePlex.moviesystem.repository;

import com.example.AcmePlex.moviesystem.model.Theatre;
import com.example.AcmePlex.moviesystem.model.vo.Showtime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TheatreShowtimeSeatRepository {
    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public TheatreShowtimeSeatRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Theatre> theatreRowMapper = new RowMapper<Theatre>() {
        @Override
        public Theatre mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Theatre(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("location"),
                    rs.getInt("rows"),
                    rs.getInt("columns")
            );
        }
    };

    private final RowMapper<Showtime> showtimeRowMapper = new RowMapper<Showtime>() {
        @Override
        public Showtime mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Showtime(
                    rs.getInt("id"),
                    rs.getInt("movie_id"),
                    rs.getInt("theatre_id"),
                    rs.getTimestamp("start_time"),
                    rs.getTimestamp("end_time"),
                    rs.getInt("tickets"),
                    rs.getInt("tickets_sold"),
                    rs.getTimestamp("public_announcement_time")
            );
        }
    };

    public List<Showtime> findShowtimesByMovie(int movie_id)
    {
        String sql = "SELECT * from showtime where movie_id = ?";
        return jdbcTemplate.query(sql, showtimeRowMapper, movie_id);
    }

    public List<Theatre> findTheatresByMovie(int movie_id) {
        String sql = "SELECT * FROM  theatre WHERE id IN (SELECT DISTINCT theatre_id FROM showtime where movie_id = ?)";
        return jdbcTemplate.query(sql, theatreRowMapper, movie_id);
    }

    public List<Showtime> findShowtimesByMovieAndTheatre(int movie_id, int theatre_id)
    {
        String sql = "SELECT * from showtime where movie_id = ? AND theatre_id = ?";
        return jdbcTemplate.query(sql, showtimeRowMapper, movie_id, theatre_id);
    }

}
