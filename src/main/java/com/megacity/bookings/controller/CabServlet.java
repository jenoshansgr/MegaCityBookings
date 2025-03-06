package com.megacity.bookings.controller;

import com.megacity.bookings.model.Cab;
import com.megacity.bookings.service.CabService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "cabServlet", value = "/cab")
public class CabServlet extends HttpServlet {
    private final String page = "cab";

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

            CabService cabService = new CabService();

            String delete = request.getParameter("delete");

            if (delete != null) {
                int id = Integer.parseInt(delete);

                if (cabService.deleteCabById(id)) {
                    response.sendRedirect(this.page + "?success=Record successfully deleted.");
                } else {
                    response.sendRedirect(this.page + "?error=Failed to delete record.");
                }
            } else {
                request.setAttribute("page", this.page);
                request.setAttribute("title", "Cab");

                request.setAttribute("cabList", cabService.getCabList());
                request.setAttribute("cabTypeList", cabService.getCabTypeList());


                String cabIdParam = request.getParameter("edit");
                Cab cab = new Cab();

                if (cabIdParam != null) {
                    int cabId = Integer.parseInt(cabIdParam);
                    cab = cabService.getCabById(cabId);
                }

                request.setAttribute("cab", cab);

                dispatcher.forward(request, response);
            }
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String model = request.getParameter("model");
        String number = request.getParameter("number");
        String cabType = request.getParameter("cabTypeId");
        String status = request.getParameter("status");
        int cabTypeId = Integer.parseInt(cabType);


        CabService cabService = new CabService();

        try {
            String edit = request.getParameter("edit");

            Cab cab = new Cab();
            cab.setNumber(number);
            cab.setModel(model);
            cab.setStatus(status);
            cab.setCabTypeId(cabTypeId);

            if (edit != null) {
                int id = Integer.parseInt(edit);
                cab.setId(id);

                if (cabService.updateCab(cab)) {
                    response.sendRedirect(this.page + "?success=Record has been updated.");
                } else {
                    response.sendRedirect(this.page + "?error=Failed to update record.");
                }
            } else {
                int cabId = cabService.insert(cab);

                if (cabId > 0) {
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