package com.megacity.bookings.controller;

import com.megacity.bookings.model.Driver;
import com.megacity.bookings.service.DriverService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet(name = "driverServlet", value = "/driver")
public class DriverServlet extends HttpServlet {
    private final String page = "driver";

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

            DriverService driverService = new DriverService();
            String delete = request.getParameter("delete");

            if (delete != null) {
                int id = Integer.parseInt(delete);

                if (driverService.deleteDriverById(id)) {
                    response.sendRedirect(this.page + "?success=Record successfully deleted.");
                } else {
                    response.sendRedirect(this.page + "?error=Failed to delete record.");
                }

                return;
            }

            request.setAttribute("page", this.page);
            request.setAttribute("title", "Driver");
            request.setAttribute("driverList", driverService.getDriverList());

            String driverIdParam = request.getParameter("edit");
            Driver driver = new Driver();

            if (driverIdParam != null) {
                int id = Integer.parseInt(driverIdParam);
                driver = driverService.getDriverById(id);
            }

            request.setAttribute("driver", driver);

            dispatcher.forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String licenseNo = request.getParameter("licenseNo");
        String status = request.getParameter("status");
        String licenseExpireDateStr = request.getParameter("licenseExpireDate");
        Date licenseExpireDate = null;

        // Convert String to java.util.Date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date utilDate = sdf.parse(licenseExpireDateStr);
            licenseExpireDate = new Date(utilDate.getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        DriverService driverService = new DriverService();

        try {
            String edit = request.getParameter("edit");

            Driver driver = new Driver();
            driver.setFirstName(firstName);
            driver.setLastName(lastName);
            driver.setLicenseNo(licenseNo);
            driver.setLicenseExpireDate(licenseExpireDate);
            driver.setStatus(status);

            if (edit != null) {
                int id = Integer.parseInt(edit);
                driver.setId(id);

                if (driverService.updateDriver(driver)) {
                    response.sendRedirect(this.page + "?success=Record has been updated.");
                } else {
                    response.sendRedirect(this.page + "?error=Failed to update record.");
                }
            } else {
                int id = driverService.insert(driver);

                if (id > 0) {
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