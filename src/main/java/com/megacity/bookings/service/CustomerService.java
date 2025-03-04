package com.megacity.bookings.service;

import com.megacity.bookings.dao.CustomerDAO;
import com.megacity.bookings.dao.UserDAO;
import com.megacity.bookings.manager.SessionManager;
import com.megacity.bookings.model.Customer;
import com.megacity.bookings.model.User;

import javax.naming.AuthenticationException;


// User Service
public class CustomerService {
    private final UserDAO userDAO;
    private final CustomerDAO customerDAO;

    public CustomerService() {
        this.userDAO = new UserDAO();
        this.customerDAO = new CustomerDAO();
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
}
