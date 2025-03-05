package com.megacity.bookings.dao;

import com.megacity.bookings.model.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


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

    public String getSelectByIdQuery(int id) {
        return "SELECT * FROM " + tableName + " WHERE id=?";
    }

    public String getSelectAllQuery() {
        return "SELECT * FROM " + tableName;
    }

    public boolean deleteById(int id) {
        String query = "DELETE FROM " + tableName + " WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
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
