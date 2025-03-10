package com.megacity.bookings.controller;

import com.megacity.bookings.model.User;
import com.megacity.bookings.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/template.jsp");
        try {

            String logout = request.getParameter("logout");

            if (logout != null) {
                HttpSession session = request.getSession();
                session.invalidate();
                response.sendRedirect("login");
            } else {
                request.setAttribute("page", "login");
                request.setAttribute("title", "Login");
                dispatcher.forward(request, response);
            }
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserService userService = new UserService();

        try {
            User user = userService.loginUser(username, password);
            HttpSession session = request.getSession();
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole());

            if (user.getRole().equals("admin")) {
                response.sendRedirect("admin");
            } else {
                response.sendRedirect("customerBooking");
            }
        } catch (Exception e) {
            response.sendRedirect("login?error=" + e.getMessage());
        }

    }
}
