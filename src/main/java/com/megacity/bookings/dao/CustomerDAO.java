package com.megacity.bookings.dao;

import com.megacity.bookings.model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class CustomerDAO extends MainDAO {
    public CustomerDAO() {
        this.tableName = "Customer";
    }

    public Customer getCustomerById(int id) {
        try {
            ResultSet rs = selectById(id);

            if (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setFirstName(rs.getString("firstName"));
                customer.setLastName(rs.getString("lastName"));
                customer.setEmail(rs.getString("email"));
                customer.setAddress(rs.getString("address"));
                customer.setPhoneNumber(rs.getString("phoneNumber"));
                customer.setUserId(rs.getInt("userId"));
                return customer;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public int saveCustomer(Customer customer) {
        String query = "INSERT INTO " + this.tableName + " (firstName, lastName, email, address, phoneNumber, userId) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            // Set values for the placeholders
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getAddress());
            stmt.setString(5, customer.getPhoneNumber());
            stmt.setInt(6, customer.getUserId());


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

    public int updateCustomer(Customer customer) {
        String query = "UPDATE " + this.tableName + " SET firstName=?, lastName=?, email=?, address=?, phoneNumber=?, userId=? " +
                " WHERE id=?";

        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            // Set values for the placeholders
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getAddress());
            stmt.setString(5, customer.getPhoneNumber());
            stmt.setInt(6, customer.getUserId());
            stmt.setInt(7, customer.getId());


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
