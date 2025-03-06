package com.megacity.bookings.controller;

import com.megacity.bookings.model.Booking;
import com.megacity.bookings.service.BookingService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "printBookingServlet", value = "/printBooking")
public class PrintBookingServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/printBooking.jsp");
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("userId") == null) {
                session.invalidate();
                response.sendRedirect("login");
                return;
            }

            BookingService bookingService = new BookingService();
            int bookingId = Integer.parseInt(request.getParameter("id"));
            Booking booking = bookingService.getBookingById(bookingId);

            request.setAttribute("title", "Booking " + booking.getOrderNo());
            request.setAttribute("booking", booking);

            dispatcher.forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

    public void destroy() {
    }
}