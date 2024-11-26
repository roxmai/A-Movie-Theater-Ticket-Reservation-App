package com.example.acmeplex.paymentsystem.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.acmeplex.paymentsystem.entity.Payment;


@Repository
public class PaymentRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PaymentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Payment> paymentRowMapper = new RowMapper<Payment>() {
        @Override
        public Payment mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Payment(
                    rs.getString("email"),
                    rs.getString("method"),
                    rs.getInt("id"),
                    rs.getDouble("amount"),
                    rs.getString("type")
            );
        }
    };

    public int getLastPaymentId() {
        return jdbcTemplate.queryForObject("SELECT MAX(id) FROM payment", Integer.class);
    }

    public List<Payment> getPaymentsByEmail(String email) {
        String sql = "SELECT * FROM payment WHERE email = ?";
        return jdbcTemplate.query(sql, paymentRowMapper, email);
    }

    public void setStatus(int paymentid, int ticketid, String status) {
        jdbcTemplate.update("UPDATE payment_ticket SET status = ? WHERE payment_id = ? AND ticket_id = ?", status, paymentid, ticketid);
    }

    public void addPayment(Payment payment) {
        jdbcTemplate.update("INSERT INTO payment (id, email, method, amount, type) VALUES (?, ?, ?, ?, ?)",
                payment.getId(),payment.getEmail(), payment.getMethod(), payment.getAmount(), payment.getType());
    }

    public void addPaymentTicket(Payment payment, String ticketId, String status) {
        jdbcTemplate.update("INSERT INTO payment_ticket (payment_id, ticket_number, status) VALUES (?, ?, ?)",
                payment.getId(), ticketId, status);
    }

    public void updatePaymentStatus(String ticket_number, String status) {
        jdbcTemplate.update("UPDATE payment_ticket SET status = ? WHERE ticket_number = ? AND status='paid' ", status, ticket_number);
    }




}
