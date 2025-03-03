package com.megacity.bookings.service;

import com.megacity.bookings.dao.UserDAO;
import com.megacity.bookings.manager.SessionManager;
import com.megacity.bookings.model.User;

import javax.naming.AuthenticationException;


// User Service
public class UserService {
    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public boolean registerUser(String username,
                                String password,
                                String role,
                                String email) {
        User user = new User(username, password, role, email);
        return this.userDAO.saveUser(user);
    }

    public User loginUser(String username, String password) {
        try {
            User user = this.userDAO.getUserByUsernameAndPassword(username, password);
            SessionManager.login(user);
            return user;
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }
}
