package com.example.acmeplex.paymentsystem.repository;

import com.example.acmeplex.paymentsystem.entity.CreditRecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@Repository
public class CreditRecordRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CreditRecordRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<CreditRecord> creditRecordRowMapper = new RowMapper<CreditRecord>() {
        @Override
        public CreditRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new CreditRecord(
                    rs.getInt("id"),
                    rs.getString("email"),
                    rs.getDouble("creditPoints"),
                    rs.getDouble("usedPoints"),
                    rs.getDate("expirationDate")
            );
        }
    };
    
    public int getLastCreditRecordId() {
        return jdbcTemplate.queryForObject("SELECT MAX(id) FROM credit_record", Integer.class);
    }

    public List<CreditRecord> getCreditRecordByEmail(String email) {
        String sql = "SELECT * FROM credit_record WHERE email = ?";
        return jdbcTemplate.query(sql, creditRecordRowMapper, email);
    }

    public List<CreditRecord> getValidCreditRecordByEmail(String email) {
        String sql = "SELECT * FROM credit_record WHERE usedpoints<creditpoints  AND expirationDate >= CURRENT_DATE AND email = ?";
        return jdbcTemplate.query(sql, creditRecordRowMapper, email);
    }

    public void addCreditRecord(CreditRecord creditRecord) {
        jdbcTemplate.update("INSERT INTO credit_record (id, email, creditPoints, usedPoints, expirationDate) VALUES (?, ?, ?, ?, ?)",
                creditRecord.getId(), creditRecord.getEmail(), creditRecord.getCreditPoints(), creditRecord.getUsedPoints(), creditRecord.getExpirationDate());
    }

    public void updateUsedPoints(int id, double usedPoints) {
        jdbcTemplate.update("UPDATE credit_record SET usedPoints = ? WHERE id = ?", usedPoints, id);
    }    
}
