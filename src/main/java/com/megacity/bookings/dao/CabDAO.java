package com.megacity.bookings.dao;

import com.megacity.bookings.model.Cab;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class CabDAO extends MainDAO {
    public CabDAO() {
        this.tableName = "Cab";
    }

    public List<Cab> selectAllCabs() {
        try (PreparedStatement stmt = connection.prepareStatement(this.getSelectAllQuery())) {
            List<Cab> cabList = new ArrayList<>();
            ResultSet rs = stmt.executeQuery();
            BookingDAO bookingDAO = new BookingDAO();

            while (rs.next()) {
                Cab cab = new Cab();
                cab.setId(rs.getInt("id"));
                cab.setCabTypeId(rs.getInt("cabTypeId"));
                cab.setModel(rs.getString("model"));
                cab.setNumber(rs.getString("number"));

                if (bookingDAO.isCabAvailable(cab.getId())) {
                    cab.setStatus("available");
                } else {
                    cab.setStatus("na");
                }

                cabList.add(cab);
            }

            return cabList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Cab getCabById(int id) {
        try (PreparedStatement stmt = connection.prepareStatement(this.getSelectByIdQuery(id))) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            BookingDAO bookingDAO = new BookingDAO();

            if (rs.next()) {
                Cab cab = new Cab();
                cab.setId(rs.getInt("id"));
                cab.setCabTypeId(rs.getInt("cabTypeId"));
                cab.setModel(rs.getString("model"));
                cab.setNumber(rs.getString("number"));

                if (bookingDAO.isCabAvailable(cab.getId())) {
                    cab.setStatus("available");
                } else {
                    cab.setStatus("na");
                }

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

    public boolean updateCab(Cab cab) {
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
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    public List<Cab> selectAllAvailableCabs() {
        try (PreparedStatement stmt = connection.prepareStatement(this.getSelectAllQuery())) {
            List<Cab> cabList = new ArrayList<>();
            ResultSet rs = stmt.executeQuery();

            BookingDAO bookingDAO = new BookingDAO();

            while (rs.next()) {
                Cab cab = new Cab();
                cab.setId(rs.getInt("id"));


                if (bookingDAO.isCabAvailable(cab.getId())) {
                    cab.setCabTypeId(rs.getInt("cabTypeId"));
                    cab.setModel(rs.getString("model"));
                    cab.setNumber(rs.getString("number"));
                    cab.setStatus(rs.getString("status"));
                    cabList.add(cab);
                }
            }

            return cabList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
