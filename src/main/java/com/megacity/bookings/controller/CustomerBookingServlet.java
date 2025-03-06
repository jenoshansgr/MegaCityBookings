package com.megacity.bookings.controller;

import com.megacity.bookings.model.Booking;
import com.megacity.bookings.model.Cab;
import com.megacity.bookings.model.CustomerBooking;
import com.megacity.bookings.service.BookingService;
import com.megacity.bookings.service.CabService;
import com.megacity.bookings.service.CustomerService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "customerBookingServlet", value = "/customerBooking")
public class CustomerBookingServlet extends HttpServlet {
    private final String page = "customerBooking";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/template.jsp");
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("userId") == null ||
                    !session.getAttribute("role").equals("customer")) {
                session.invalidate();
                response.sendRedirect("login");
                return;
            }

            CustomerService customerService = new CustomerService();
            int userId = (int) session.getAttribute("userId");
            int customerId = customerService.getCustomerByUserId(userId).getId();

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
            request.setAttribute("bookingList", bookingService.getCustomerBookingList(customerId));

            dispatcher.forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        BookingService bookingService = new BookingService();
        CabService cabService = new CabService();
        CustomerService customerService = new CustomerService();

        String destination = request.getParameter("destination");

        int distanceKm = 0;
        if (!request.getParameter("distanceKm").isEmpty()) {
            distanceKm = Integer.parseInt(request.getParameter("distanceKm"));
        }

        int noOfDays = 0;
        if (!request.getParameter("noOfDays").isEmpty()) {
            noOfDays = Integer.parseInt(request.getParameter("noOfDays"));
        }

        int driverId = Integer.parseInt(request.getParameter("driverId"));
        int cabId = Integer.parseInt(request.getParameter("cabId"));
        Date tripDate = Date.valueOf(request.getParameter("tripDate"));

        int userId = (int) session.getAttribute("userId");
        int customerId = customerService.getCustomerByUserId(userId).getId();
        String orderNo = BookingService.generateOrderNumber();
        Date orderDate = new Date(System.currentTimeMillis());

        Cab cab = cabService.getCabById(cabId);

        try {
            String edit = request.getParameter("edit");
            Booking booking = new Booking();
            booking.setDestination(destination);
            booking.setOrderNo(orderNo);
            booking.setOrderDate(orderDate);
            booking.setTripDate(tripDate);
            booking.setNoOfDays(noOfDays);
            booking.setDistanceKm(distanceKm);
            booking.setCabId(cabId);
            booking.setDriverId(driverId);
            booking.setPricePerDay(cab.getPricePerDay());
            booking.setPricePerKm(cab.getPricePerKm());

            if (edit != null) {
                int id = Integer.parseInt(edit);
                cab.setId(id);

                if (bookingService.updateBooking(booking)) {
                    response.sendRedirect(this.page + "?success=Record has been updated.");
                } else {
                    response.sendRedirect(this.page + "?error=Failed to update record.");
                }
            } else {
                int bookingId = bookingService.insert(booking);

                if (bookingId > 0) {
                    CustomerBooking customerBooking = new CustomerBooking();
                    customerBooking.setCustomerId(customerId);
                    customerBooking.setBookingId(bookingId);
                    bookingService.insertCustomerBooking(customerBooking);
                    response.sendRedirect(this.page + "?success=Record has been created.");
                } else {
                    response.sendRedirect(this.page + "?error=Failed to create record.");
                }
            }
        } catch (Exception e) {
            response.sendRedirect(this.page + "?error=" + e.getMessage());
        }

    }

    public void destroy() {
    }
}