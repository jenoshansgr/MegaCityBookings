package com.megacity.bookings.controller;

import com.megacity.bookings.model.Customer;
import com.megacity.bookings.model.User;
import com.megacity.bookings.service.CustomerService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/template.jsp");
        try {
            request.setAttribute("page", "register");
            request.setAttribute("title", "Register");
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String address = request.getParameter("address");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        CustomerService customerService = new CustomerService();

        try {
            User user = new User();
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(password);
            user.setRole("customer");

            Customer customer = new Customer();
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setAddress(address);
            customer.setEmail(email);

            int userId = customerService.register(user, customer);

            HttpSession session = request.getSession();
            session.setAttribute("userId", userId);
            session.setAttribute("username", user.getUsername());
            response.sendRedirect("index");
        } catch (Exception e) {
            response.sendRedirect("register?error=" + e.getMessage());
        }

    }
}
