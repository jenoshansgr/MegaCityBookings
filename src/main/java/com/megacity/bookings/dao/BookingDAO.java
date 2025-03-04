package com.megacity.bookings.dao;

import com.megacity.bookings.model.Booking;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class BookingDAO extends MainDAO {
    public BookingDAO() {
        this.tableName = "Booking";
    }

    public Booking getBookingById(int id) {
        try {
            ResultSet rs = selectById(id);

            if (rs.next()) {
                Booking booking = new Booking();
                booking.setId(rs.getInt("id"));
                booking.setDestination(rs.getString("destination"));
                booking.setOrderNo(rs.getString("orderNo"));
                booking.setOrderDate(rs.getDate("orderDate"));
                booking.setTripDate(rs.getDate("tripDate"));
                booking.setNoOfDays(rs.getInt("noOfDays"));
                booking.setDistanceKm(rs.getDouble("distanceKm"));
                booking.setCabId(rs.getInt("cabId"));
                booking.setDriverId(rs.getInt("driverId"));
                booking.setPricePerDay(rs.getDouble("pricePerDay"));
                booking.setPricePerKm(rs.getDouble("pricePerKm"));
                return booking;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public int saveBooking(Booking booking) {
        String query = "INSERT INTO " + this.tableName + " (destination, orderNo, orderDate, tripDate, noOfDays, distanceKm, cabId, driverId, pricePerDay, pricePerKm) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            // Set values for the placeholders
            stmt.setString(1, booking.getDestination());
            stmt.setString(2, booking.getOrderNo());
            stmt.setDate(3, booking.getOrderDate());
            stmt.setDate(4, booking.getTripDate());
            stmt.setInt(5, booking.getNoOfDays());
            stmt.setDouble(6, booking.getDistanceKm());
            stmt.setInt(7, booking.getCabId());
            stmt.setInt(8, booking.getDriverId());
            stmt.setDouble(9, booking.getPricePerDay());
            stmt.setDouble(10, booking.getPricePerKm());

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

    public int updateBooking(Booking booking) {
        String query = "UPDATE " + this.tableName + " SET destination=?, orderNo=?, orderDate=?, tripDate=?, noOfDays=?, distanceKm=?, cabId=?, driverId=?, pricePerDay=?, pricePerKm=? " +
                " WHERE id=?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            // Set values for the placeholders
            stmt.setString(1, booking.getDestination());
            stmt.setString(2, booking.getOrderNo());
            stmt.setDate(3, booking.getOrderDate());
            stmt.setDate(4, booking.getTripDate());
            stmt.setInt(5, booking.getNoOfDays());
            stmt.setDouble(6, booking.getDistanceKm());
            stmt.setInt(7, booking.getCabId());
            stmt.setInt(8, booking.getDriverId());
            stmt.setDouble(9, booking.getPricePerDay());
            stmt.setDouble(10, booking.getPricePerKm());
            stmt.setInt(11, booking.getId());


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
