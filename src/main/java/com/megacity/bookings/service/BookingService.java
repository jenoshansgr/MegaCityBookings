package com.megacity.bookings.service;

import com.megacity.bookings.dao.BookingDAO;
import com.megacity.bookings.dao.CustomerBookingDAO;
import com.megacity.bookings.model.Booking;
import com.megacity.bookings.model.CustomerBooking;

import java.util.List;

// Booking Service
public class BookingService {
    private final BookingDAO bookingDAO;
    private final CustomerBookingDAO customerBookingDAO;


    public BookingService() {
        this.bookingDAO = new BookingDAO();
        this.customerBookingDAO = new CustomerBookingDAO();
    }

    public List<Booking> getBookingList() {
        return this.bookingDAO.selectAllBooking();
    }

    public int insert(Booking booking) throws Exception {
        int bookingId = this.bookingDAO.saveBooking(booking);

        if (bookingId > 0) {
            return bookingId;
        }

        throw new Exception("Failed to save booking");
    }

    public Booking getBookingById(int id) {
        Booking booking = this.bookingDAO.getBookingById(id);

        if (booking == null) {
            return new Booking();
        }

        return booking;
    }

    public boolean deleteBookingById(int id) {
        return this.bookingDAO.deleteById(id);
    }

    public boolean updateBooking(Booking booking) {
        return this.bookingDAO.updateBooking(booking);
    }

    public boolean isDriverAvailable(int id) {
        return this.bookingDAO.isDriverAvailable(id);
    }

    public List<Booking> getCustomerBookingList(int customerId) {
        return this.bookingDAO.selectAllCustomerBooking(customerId);
    }

    public static String generateOrderNumber() {
        long timestamp = System.currentTimeMillis();
        return "MCB_" + timestamp;
    }

    public boolean insertCustomerBooking(CustomerBooking customerBooking) throws Exception {
        return this.customerBookingDAO.saveCustomerBooking(customerBooking);
    }
}
