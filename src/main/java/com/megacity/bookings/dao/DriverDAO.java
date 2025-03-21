package com.megacity.bookings.dao;

import com.megacity.bookings.model.Driver;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class DriverDAO extends MainDAO {
    public DriverDAO() {
        this.tableName = "Driver";
    }

    public List<Driver> selectAllDrivers() {
        try (PreparedStatement stmt = connection.prepareStatement(this.getSelectAllQuery())) {
            List<Driver> driverList = new ArrayList<>();
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Driver driver = new Driver();
                driver.setId(rs.getInt("id"));
                driver.setFirstName(rs.getString("firstName"));
                driver.setLastName(rs.getString("lastName"));
                driver.setLicenseNo(rs.getString("licenseNo"));
                driver.setLicenseExpireDate(rs.getDate("licenseExpireDate"));
                driver.setStatus(rs.getString("status"));
                driverList.add(driver);
            }

            return driverList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Driver getDriverById(int id) {
        try (PreparedStatement stmt = connection.prepareStatement(this.getSelectByIdQuery(id))) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Driver driver = new Driver();
                driver.setId(rs.getInt("id"));
                driver.setFirstName(rs.getString("firstName"));
                driver.setLastName(rs.getString("lastName"));
                driver.setLicenseNo(rs.getString("licenseNo"));
                driver.setLicenseExpireDate(rs.getDate("licenseExpireDate"));
                driver.setStatus(rs.getString("status"));
                return driver;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public int saveDriver(Driver driver) {
        String query = "INSERT INTO " + this.tableName + " (firstName, lastName, licenseNo, licenseExpireDate, status) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            // Set values for the placeholders
            stmt.setString(1, driver.getFirstName());
            stmt.setString(2, driver.getLastName());
            stmt.setString(3, driver.getLicenseNo());
            stmt.setDate(4, driver.getLicenseExpireDate());
            stmt.setString(5, driver.getStatus());

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

    public boolean updateDriver(Driver driver) {
        String query = "UPDATE " + this.tableName + " SET firstName=?, lastName=?, licenseNo=?, licenseExpireDate=?, status=? " +
                " WHERE id=?";

        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            // Set values for the placeholders
            stmt.setString(1, driver.getFirstName());
            stmt.setString(2, driver.getLastName());
            stmt.setString(3, driver.getLicenseNo());
            stmt.setDate(4, driver.getLicenseExpireDate());
            stmt.setString(5, driver.getStatus());
            stmt.setInt(6, driver.getId());


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

    public List<Driver> selectAllAvailableDrivers() {
        try (PreparedStatement stmt = connection.prepareStatement(this.getSelectAllQuery())) {
            List<Driver> driverList = new ArrayList<>();
            ResultSet rs = stmt.executeQuery();

            BookingDAO bookingDAO = new BookingDAO();

            while (rs.next()) {
                Driver driver = new Driver();
                driver.setId(rs.getInt("id"));

                if (bookingDAO.isDriverAvailable(driver.getId())) {
                    driver.setFirstName(rs.getString("firstName"));
                    driver.setLastName(rs.getString("lastName"));
                    driver.setLicenseNo(rs.getString("licenseNo"));
                    driver.setLicenseExpireDate(rs.getDate("licenseExpireDate"));
                    driver.setStatus(rs.getString("status"));
                    driverList.add(driver);
                }

            }

            return driverList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
