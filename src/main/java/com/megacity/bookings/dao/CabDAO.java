package com.megacity.bookings.dao;

import com.megacity.bookings.model.Cab;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class CabDAO extends MainDAO {
    public CabDAO() {
        this.tableName = "Cab";
    }

    public Cab getCabById(int id) {
        try {
            ResultSet rs = selectById(id);

            if (rs.next()) {
                Cab cab = new Cab();
                cab.setId(rs.getInt("id"));
                cab.setCabTypeId(rs.getInt("cabTypeId"));
                cab.setModel(rs.getString("model"));
                cab.setNumber(rs.getString("number"));
                cab.setStatus(rs.getString("status"));
                return cab;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public int saveCab(Cab cab) {
        String query = "INSERT INTO " + this.tableName + " (cabTypeId, model, number, status) " +
                "VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            // Set values for the placeholders
            stmt.setInt(1, cab.getCabTypeId());
            stmt.setString(2, cab.getModel());
            stmt.setString(3, cab.getNumber());
            stmt.setString(4, cab.getStatus());

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

    public int updateCab(Cab cab) {
        String query = "UPDATE " + this.tableName + " SET cabTypeId=?, model=?, number=?, status=? " +
                " WHERE id=?";

        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            // Set values for the placeholders
            stmt.setInt(1, cab.getCabTypeId());
            stmt.setString(2, cab.getModel());
            stmt.setString(3, cab.getNumber());
            stmt.setString(4, cab.getStatus());
            stmt.setInt(5, cab.getId());


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

}
