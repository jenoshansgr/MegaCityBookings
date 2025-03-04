package com.megacity.bookings.dao;

import com.megacity.bookings.model.CustomerBooking;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CustomerBookingDAO extends MainDAO {
    public CustomerBookingDAO() {
        this.tableName = "CustomerBooking";
    }

    public CustomerBooking getCustomerBookingById(int id) {
        try {
            ResultSet rs = selectById(id);

            if (rs.next()) {
                CustomerBooking customerBooking = new CustomerBooking();
                customerBooking.setCustomerId(rs.getInt("customerId"));
                customerBooking.setBookingId(rs.getInt("bookingId"));
                return customerBooking;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public int saveCustomerBooking(CustomerBooking customerBooking) {
        String query = "INSERT INTO " + this.tableName + " (customerId, bookingId) " +
                "VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            // Set values for the placeholders
            stmt.setInt(1, customerBooking.getCustomerId());
            stmt.setInt(2, customerBooking.getBookingId());

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

    public int updateCustomerBooking(CustomerBooking customerBooking) {
        this.deleteById(customerBooking.getBookingId());

        String query = "DELETE FROM " + this.tableName + " WHERE customerId=? AND bookingId=?";
        try (PreparedStatement stmt = connection.prepareStatement(query))  {
            stmt.setInt(1, customerBooking.getCustomerId());
            stmt.setInt(2, customerBooking.getBookingId());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                return saveCustomerBooking(customerBooking);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }

}
