package com.example.acmeplex.paymentsystem.repository;

import java.security.Timestamp;
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
                    rs.getBoolean("status"),
                    rs.getInt("id"),
                    rs.getDouble("amount"),
                    rs.getTimestamp("lastUpdateTime"),
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

    public void setStatus(int id, boolean status) {
        jdbcTemplate.update("UPDATE payment SET status = ? WHERE id = ?", status, id);
    }

    public void setLastUpdateTime(int id) {
        jdbcTemplate.update("UPDATE payment SET lastUpdateTime = CURRENT_TIMESTAMP WHERE id = ?", id);
    }

    public void addPayment(Payment payment) {
        jdbcTemplate.update("INSERT INTO payment (id, email, method, status, amount, lastUpdateTime, type) VALUES (?, ?, ?, ?, ?, ?, ?)",
                payment.getId(),payment.getEmail(), payment.getMethod(), payment.getStatus(), payment.getAmount(), payment.getLastUpdateTime(), payment.getType());
    }


}
