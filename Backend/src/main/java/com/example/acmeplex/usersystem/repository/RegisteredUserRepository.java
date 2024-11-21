package com.example.acmeplex.usersystem.repository;

import com.example.acmeplex.usersystem.model.RegisteredUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class RegisteredUserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RegisteredUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper for RegisteredUser
    private final RowMapper<RegisteredUser> registeredUserRowMapper = new RowMapper<RegisteredUser>() {
        @Override
        public RegisteredUser mapRow(ResultSet rs, int rowNum) throws SQLException {
            RegisteredUser registeredUser = new RegisteredUser();
            registeredUser.setId(rs.getLong("id"));
            registeredUser.setName(rs.getString("name"));
            registeredUser.setEmail(rs.getString("email"));
            registeredUser.setAddress(rs.getString("address"));
            registeredUser.setCreditCardInfo(rs.getString("credit_card_info"));
            registeredUser.setActiveSubscription(rs.getBoolean("active_subscription"));
            return registeredUser;
        }
    };

    // Find a RegisteredUser by email
    public Optional<RegisteredUser> findByEmail(String email) {
        String sql = "SELECT * FROM registered_users WHERE email = ?";
        List<RegisteredUser> users = jdbcTemplate.query(sql, registeredUserRowMapper, email);
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    // Check if a RegisteredUser exists by email
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM registered_users WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    // Find all RegisteredUsers
    public List<RegisteredUser> findAll() {
        String sql = "SELECT * FROM registered_users";
        return jdbcTemplate.query(sql, registeredUserRowMapper);
    }

    // Find RegisteredUser by ID
    public Optional<RegisteredUser> findById(Long id) {
        String sql = "SELECT * FROM registered_users WHERE id = ?";
        List<RegisteredUser> users = jdbcTemplate.query(sql, registeredUserRowMapper, id);
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    // Save a new RegisteredUser
    public RegisteredUser save(RegisteredUser registeredUser) {
        String sql = "INSERT INTO registered_users (name, email, address, credit_card_info, active_subscription) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, registeredUser.getName(), registeredUser.getEmail(), registeredUser.getAddress(),
                registeredUser.getCreditCardInfo(), registeredUser.isActiveSubscription());
        // For simplicity, we'll assume the ID is auto-incremented and not returned here.
        // In production, use RETURNING clauses or queries to retrieve the inserted ID.
        return registeredUser;
    }

    // Update an existing RegisteredUser
    public void update(RegisteredUser registeredUser) {
        String sql = "UPDATE registered_users SET name = ?, email = ?, address = ?, credit_card_info = ?, active_subscription = ? WHERE id = ?";
        jdbcTemplate.update(sql, registeredUser.getName(), registeredUser.getEmail(), registeredUser.getAddress(),
                registeredUser.getCreditCardInfo(), registeredUser.isActiveSubscription(), registeredUser.getId());
    }

    // Delete a RegisteredUser by ID
    public void deleteById(Long id) {
        String sql = "DELETE FROM registered_users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public boolean existsById(Long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
