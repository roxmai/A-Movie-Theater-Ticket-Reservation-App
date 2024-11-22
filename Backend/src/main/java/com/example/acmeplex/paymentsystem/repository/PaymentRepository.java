package com.example.acmeplex.paymentsystem.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.tree.RowMapper;

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
                    rs.getInt("payment_id"),
                    rs.getDouble("amount"),
                    rs.getString("payment_method"),
                    rs.getBoolean("payment_status")
            );
        }
    };

    public int getPaymentId() {
        return jdbcTemplate.queryForObject("SELECT MAX(payment_id) FROM payment", Integer.class);
    }


}
