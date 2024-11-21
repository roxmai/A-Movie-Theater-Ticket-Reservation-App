package com.example.acmeplex.moviesystem.repository;

import com.example.acmeplex.moviesystem.model.Ticket;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class TicketRepository {
    private final JdbcTemplate jdbcTemplate;

    public TicketRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Ticket> ticketRowMapper = new RowMapper<Ticket>() {
        @Override
        public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Ticket(
                    rs.getInt("id"),
                    rs.getString("ticket_number"),
                    rs.getString("holder_email"),
                    rs.getBigDecimal("price"),
                    rs.getInt("movie_id"),
                    rs.getInt("theatre_id"),
                    rs.getInt("showtime_id"),
                    rs.getInt("seat_id"),
                    rs.getTimestamp("reserved_time"),
                    rs.getTimestamp("expire_time")
            );
        }
    };

    public int updateTicketReservation(int id, String holderEmail, Timestamp reservedTime) {
        String sql = "UPDATE ticket SET holder_email=?, reserved_time=? WHERE id=?";
        return jdbcTemplate.update(sql, holderEmail, reservedTime, id);
    }

    public int updateTicketReservationByTicketNumber(String ticketNumber, String holderEmail, Timestamp reservedTime) {
        String sql = "UPDATE ticket SET holder_email=?, reserved_time=? WHERE ticket_number=?";
        return jdbcTemplate.update(sql, holderEmail, reservedTime, ticketNumber);
    }

    public Optional<Ticket> findByTicketNumber(String ticketNumber) {
        String sql = "SELECT * FROM ticket WHERE ticket_number=?";
        List<Ticket> tickets = jdbcTemplate.query(sql, ticketRowMapper, ticketNumber);
        return tickets.isEmpty() ? Optional.empty() : Optional.of(tickets.get(0));
    }
}
