package com.example.acmeplex.usersystem.repository;

import com.example.acmeplex.usersystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper for User
    private final RowMapper<User> userRowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setEmail(rs.getString("email"));
            return user;
        }
    };

    /**
     * Find a User by email.
     * 
     * @param email The email of the user.
     * @return An Optional containing the User if found, otherwise empty.
     */
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        List<User> users = jdbcTemplate.query(sql, userRowMapper, email);
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    /**
     * Check if a User exists by email.
     * 
     * @param email The email to check.
     * @return True if the user exists, false otherwise.
     */
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

    /**
     * Find all Users.
     * 
     * @return A list of all Users.
     */
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    /**
     * Save a new User.
     * 
     * @param user The User to save.
     * @return The saved User.
     */
    public User save(User user) {
        String sql = "INSERT INTO users (email) VALUES (?)";
        jdbcTemplate.update(sql, user.getEmail());
        return user;
    }

    /**
     * Update an existing User's email.
     * 
     * Note: Since email is the primary key, updating it might have implications.
     * Ensure that related entities are handled appropriately.
     * 
     * @param oldEmail The current email of the user.
     * @param newEmail The new email to update to.
     */
    public void updateEmail(String oldEmail, String newEmail) {
        String sql = "UPDATE users SET email = ? WHERE email = ?";
        jdbcTemplate.update(sql, newEmail, oldEmail);
    }

    /**
     * Delete a User by email.
     * 
     * @param email The email of the User to delete.
     */
    public void deleteByEmail(String email) {
        String sql = "DELETE FROM users WHERE email = ?";
        jdbcTemplate.update(sql, email);
    }

    /**
     * Check if a User exists by email.
     * 
     * @param email The email to check.
     * @return True if the user exists, false otherwise.
     */
    public boolean existsById(String email) {
        return existsByEmail(email);
    }
}