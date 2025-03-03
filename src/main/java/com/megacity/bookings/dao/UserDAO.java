package com.megacity.bookings.dao;

import com.megacity.bookings.model.DatabaseConnection;
import com.megacity.bookings.model.User;

import javax.naming.AuthenticationException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;


public class UserDAO {
    private String tableName = "User";
    private Connection connection;

    public UserDAO() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserById(int id) {
        String query = "SELECT * FROM " + tableName + " WHERE id=" + id;
        try (PreparedStatement stmt = connection.prepareStatement(query))  {
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("email")
                );
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
        String query = "SELECT * FROM " + tableName + " WHERE username='" + username +
                "' AND password='" + getMD5(password) + "'";
        try (PreparedStatement stmt = connection.prepareStatement(query))  {
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("email")
                );
                user.setId(rs.getInt("id"));
                user.setCreatedDate(rs.getDate("createdDate"));
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        throw new AuthenticationException("Invalid username or password");
    }

    public boolean saveUser(User user) {
        String query = "INSERT INTO users (username, password, role, email, createdDate) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            Date sqlDate = new Date(System.currentTimeMillis());

            // Set values for the placeholders
            stmt.setString(1, user.getUsername());
            stmt.setString(2, getMD5(user.getPassword()));
            stmt.setString(3, user.getRole());
            stmt.setString(4, user.getEmail());
            stmt.setDate(5, sqlDate);


            // Execute the insert statement
            int rowsInserted = stmt.executeUpdate();

            // Check if insert was successful
            if (rowsInserted > 0) {
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
