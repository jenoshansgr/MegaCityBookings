package com.megacity.bookings.manager;

import com.megacity.bookings.model.User;

public class SessionManager {
    private static User loggedInUser = null; // Store logged-in user

    public static void login(User user) {
        loggedInUser = user;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void logout() {
        loggedInUser = null;
    }

    public static boolean isLoggedIn() {
        return loggedInUser != null;
    }
}
