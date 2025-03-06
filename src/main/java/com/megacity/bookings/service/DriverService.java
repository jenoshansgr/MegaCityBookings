package com.megacity.bookings.service;

import com.megacity.bookings.dao.DriverDAO;
import com.megacity.bookings.model.Driver;

import java.util.List;


// User Service
public class DriverService {
    private final DriverDAO driverDAO;


    public DriverService() {
        this.driverDAO = new DriverDAO();
    }

    public List<Driver> getDriverList() {
        return this.driverDAO.selectAllDrivers();
    }

    public int insert(Driver driver) throws Exception {
        int driverId = this.driverDAO.saveDriver(driver);

        if (driverId > 0) {
            return driverId;
        }

        throw new Exception("Failed to save driver");
    }

    public Driver getDriverById(int id) {
        Driver driver = this.driverDAO.getDriverById(id);

        if (driver == null) {
            return new Driver();
        }

        return driver;
    }

    public boolean deleteDriverById(int id) {
        return this.driverDAO.deleteById(id);
    }

    public boolean updateDriver(Driver driver) {
        return this.driverDAO.updateDriver(driver);
    }
}
