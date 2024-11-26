package com.example.acmeplex.moviesystem.repository;

import com.example.acmeplex.moviesystem.entity.Ticket;
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
                    rs.getInt("showroom_id"),
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
    public double getTicketPrice(String ticketNumber) {
        String sql = "SELECT price FROM ticket WHERE ticket_number=?";
        return jdbcTemplate.queryForObject(sql, Double.class, ticketNumber);
    }
    public double getTicketPrice(int id) {
        String sql = "SELECT price FROM ticket WHERE id=?";
        return jdbcTemplate.queryForObject(sql, Double.class, id);
    }

    public String getTicketNumber(int id) {
        String sql = "SELECT ticket_number FROM ticket WHERE id=?";
        return jdbcTemplate.queryForObject(sql, String.class, id);
    }

    public String getEmailByTicketNumber(String ticketNumber) {
        String sql = "SELECT holder_email FROM ticket WHERE ticket_number=?";
        return jdbcTemplate.queryForObject(sql, String.class, ticketNumber);
    }

    public String findActiveTicketStatus(String ticketNumber, String status) {
        String sql = "SELECT status FROM payment_ticket WHERE ticket_number=? AND status!=?";
        return jdbcTemplate.queryForObject(sql, String.class, ticketNumber, status);
    }

    public int updateTicketStatus(String ticketNumber, String status) {
        String sql = "UPDATE payment_ticket SET status=? WHERE ticket_number=?";
        return jdbcTemplate.update(sql, status, ticketNumber);
    }

}
