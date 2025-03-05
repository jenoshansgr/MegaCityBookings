package com.megacity.bookings.dao;

import com.megacity.bookings.model.User;

import javax.naming.AuthenticationException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;


public class UserDAO extends MainDAO {
    public UserDAO() {
        this.tableName = "User";
    }

    public User getUserById(int id) {
        try (PreparedStatement stmt = connection.prepareStatement(this.getSelectByIdQuery(id))) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role"));
                user.setEmail(rs.getString("email"));
                user.setId(rs.getInt("id"));
                user.setCreatedDate(rs.getDate("createdDate"));
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public User getUserByUsernameAndPassword(String username, String password) throws AuthenticationException {
        String query = "SELECT * FROM " + this.tableName + " WHERE username='" + username +
                "' AND password='" + getMD5(password) + "'";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setRole(rs.getString("role"));
                user.setEmail(rs.getString("email"));
                user.setId(rs.getInt("id"));
                user.setCreatedDate(rs.getDate("createdDate"));
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        throw new AuthenticationException("Invalid username or password");
    }

    public int saveUser(User user) {
        String query = "INSERT INTO " + this.tableName + " (username, password, role, email, createdDate) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            Date sqlDate = new Date(System.currentTimeMillis());

            // Set values for the placeholders
            stmt.setString(1, user.getUsername());
            stmt.setString(2, getMD5(user.getPassword()));
            stmt.setString(3, user.getRole());
            stmt.setString(4, user.getEmail());
            stmt.setDate(5, sqlDate);


            // Execute the statement
            int rowsAffected = stmt.executeUpdate();

            // Check if successful
            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }

    public boolean updateUser(User user) {
        String query = "UPDATE " + this.tableName + " SET username=?, password=?, role=?, email=?, createdDate=?" +
                " WHERE id=?";

        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            // Set values for the placeholders
            stmt.setString(1, user.getUsername());
            stmt.setString(2, getMD5(user.getPassword()));
            stmt.setString(3, user.getRole());
            stmt.setString(4, user.getEmail());
            stmt.setDate(5, user.getCreatedDate());
            stmt.setInt(6, user.getId());


            // Execute the statement
            int rowsAffected = stmt.executeUpdate();

            // Check if successful
            if (rowsAffected > 0) {
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    public static String getMD5(String input) {
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // Compute the hash
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into a 32-character hex string
            String hashText = no.toString(16);

            // Add leading zeros to make it 32 characters if needed
            while (hashText.length() < 32) {
                hashText = "0" + hashText;
            }
            return hashText;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
