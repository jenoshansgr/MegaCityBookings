package com.megacity.bookings.controller;

import com.megacity.bookings.service.BookingService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "bookingServlet", value = "/booking")
public class BookingServlet extends HttpServlet {
    private final String page = "booking";

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

            BookingService bookingService = new BookingService();
            String delete = request.getParameter("delete");

            if (delete != null) {
                int id = Integer.parseInt(delete);

                if (bookingService.deleteBookingById(id)) {
                    response.sendRedirect(this.page + "?success=Record successfully deleted.");
                } else {
                    response.sendRedirect(this.page + "?error=Failed to delete record.");
                }

                return;
            }

            request.setAttribute("page", this.page);
            request.setAttribute("title", "Booking");
            request.setAttribute("bookingList", bookingService.getBookingList());

            dispatcher.forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

    public void destroy() {
    }
}