package com.megacity.bookings.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "cabServlet", value = "/cab")
public class CabServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/template.jsp");
        try {

            request.setAttribute("page", "cab");
            request.setAttribute("title", "Cab");
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

    public void destroy() {
    }
}