package com.megacity.bookings.model;

public class Cab {
    protected int id;
    protected int cabTypeId;
    protected String model;
    protected String number;
    protected String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCabTypeId() {
        return cabTypeId;
    }

    public void setCabTypeId(int cabTypeId) {
        this.cabTypeId = cabTypeId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
