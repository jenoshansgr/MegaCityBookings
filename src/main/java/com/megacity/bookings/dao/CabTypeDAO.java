package com.megacity.bookings.dao;

import com.megacity.bookings.model.CabType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class CabTypeDAO extends MainDAO {
    public CabTypeDAO() {
        this.tableName = "CabType";
    }

    public CabType getCabTypeById(int id) {
        try {
            ResultSet rs = selectById(id);

            if (rs.next()) {
                CabType cabType = new CabType();
                cabType.setId(rs.getInt("id"));
                cabType.setName(rs.getString("name"));
                cabType.setPricePerDay(rs.getDouble("pricePerDay"));
                cabType.setPricePerKm(rs.getDouble("pricePerKm"));
                return cabType;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public int saveCabType(CabType cabType) {
        String query = "INSERT INTO " + this.tableName + " (name, pricePerDay, pricePerKm) " +
                "VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            // Set values for the placeholders
            stmt.setString(1, cabType.getName());
            stmt.setDouble(2, cabType.getPricePerDay());
            stmt.setDouble(3, cabType.getPricePerKm());

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

    public int updateCabType(CabType cabType) {
        String query = "UPDATE " + this.tableName + " SET name=?, pricePerDay=?, pricePerKm=? " +
                " WHERE id=?";

        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            // Set values for the placeholders
            stmt.setString(1, cabType.getName());
            stmt.setDouble(2, cabType.getPricePerDay());
            stmt.setDouble(3, cabType.getPricePerKm());
            stmt.setInt(4, cabType.getId());


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
