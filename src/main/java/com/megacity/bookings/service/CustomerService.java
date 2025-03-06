package com.megacity.bookings.service;

import com.megacity.bookings.dao.CustomerBookingDAO;
import com.megacity.bookings.dao.CustomerDAO;
import com.megacity.bookings.dao.UserDAO;
import com.megacity.bookings.model.Customer;
import com.megacity.bookings.model.CustomerBooking;
import com.megacity.bookings.model.User;


// Customer Service
public class CustomerService {
    private final UserDAO userDAO;
    private final CustomerDAO customerDAO;
    private final CustomerBookingDAO customerBookingDAO;

    public CustomerService() {
        this.userDAO = new UserDAO();
        this.customerDAO = new CustomerDAO();
        this.customerBookingDAO = new CustomerBookingDAO();
    }

    public int register(User user, Customer customer) throws Exception {
        int userId = this.userDAO.saveUser(user);

        if (userId > 0) {
            customer.setUserId(userId);
            this.customerDAO.saveCustomer(customer);
            return userId;
        }

        throw new Exception("Failed to create user account");
    }

    public Customer getCustomerById(int id) {
        Customer customer = this.customerDAO.getCustomerById(id);

        if (customer == null) {
            return new Customer();
        }

        return customer;
    }

    public Customer getCustomerByUserId(int id) {
        Customer customer = this.customerDAO.getCustomerByUserId(id);

        if (customer == null) {
            return new Customer();
        }

        return customer;
    }

    public Customer getCustomerByBookingId(int id) {
        CustomerBooking customerBooking = this.customerBookingDAO.getCustomerBookingById(id);
        return customerDAO.getCustomerById(customerBooking.getCustomerId());
    }
}
