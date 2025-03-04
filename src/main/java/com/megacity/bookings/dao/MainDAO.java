package com.megacity.bookings.dao;

import com.megacity.bookings.model.DatabaseConnection;
import com.megacity.bookings.model.User;

import javax.naming.AuthenticationException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;


public class MainDAO {
    public String tableName;
    public Connection connection;

    public MainDAO() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet selectById(int id) {
        String query = "SELECT * FROM " + tableName + " WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(query))  {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public boolean deleteById(int id) {
        String query = "DELETE FROM " + tableName + " WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(query))  {
            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}
