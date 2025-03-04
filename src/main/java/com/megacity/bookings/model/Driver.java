package com.megacity.bookings.model;

import java.sql.Date;

public class Driver {
    protected int id;
    protected String firstName;
    protected String lastName;
    protected String licenseNo;
    protected Date licenseExpireDate;
    protected String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLicenseNo() {
        return licenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        this.licenseNo = licenseNo;
    }

    public Date getLicenseExpireDate() {
        return licenseExpireDate;
    }

    public void setLicenseExpireDate(Date licenseExpireDate) {
        this.licenseExpireDate = licenseExpireDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
