package com.megacity.bookings.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "cabTypeServlet", value = "/cabType")
public class CabTypeServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/template.jsp");
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("userId") == null ||
                    !session.getAttribute("role").equals("admin")) {
                session.invalidate();
                response.sendRedirect("login");
                return;
            }

            request.setAttribute("page", "cabType");
            request.setAttribute("title", "CabType");
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

    public void destroy() {
    }
}