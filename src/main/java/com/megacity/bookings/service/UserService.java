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

    public User loginUser(String username, String password) throws AuthenticationException {
        User user = this.userDAO.getUserByUsernameAndPassword(username, password);
        SessionManager.login(user);
        return user;
    }
}
