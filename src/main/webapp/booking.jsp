<%@ page import="com.megacity.bookings.model.Booking" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Objects" %>
<%@ page import="com.megacity.bookings.service.CustomerService" %>
<%@ page import="com.megacity.bookings.service.DriverService" %>
<%@ page import="com.megacity.bookings.model.Driver" %>
<%@ page import="com.megacity.bookings.model.Customer" %>
<%
    List<Booking> bookingList = (List) request.getAttribute("bookingList");

    CustomerService customerService = new CustomerService();
    DriverService driverService = new DriverService();

    String role = (String) session.getAttribute("role");
%>

<h2>Bookings</h2>

<% if (request.getParameter("error") != null) { %>
<% String errorString = request.getParameter("error"); %>
<div class="alert alert-danger" role="alert">
    <%= errorString %>
</div>
<% } else if (request.getParameter("success") != null) { %>
<% String successString = request.getParameter("success"); %>
<div class="alert alert-success" role="alert">
    <%= successString %>
</div>
<% } %>

<div class="container">
    <div class="row">
        <div class="col-sm-12">
            <hr>
            <div class="table-responsive">
                <table class="table table-striped table-bordered">
                    <thead>
                    <tr>
                        <th>#ID</th>
                        <th>Order No</th>
                        <th>Order Date</th>
                        <th>Trip Date</th>
                        <th>No. Days</th>
                        <th>No. KMs</th>
                        <th>Total Price</th>
                        <th>Driver</th>
                        <th>Customer</th>
                        <% if ("admin".equals(role)) { %>
                        <th></th>
                        <% }%>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (Booking booking : bookingList) { %>
                    <tr>
                        <td><%= booking.getId() %>
                        </td>
                        <td><%= booking.getOrderNo() %>
                        </td>
                        <td><%= booking.getOrderDate() %>
                        </td>
                        <td><%= booking.getTripDate() %>
                        </td>
                        <td><%= booking.getNoOfDays() %>
                        </td>
                        <td><%= booking.getDistanceKm() %>
                        </td>
                        <td>
                            <%= (booking.getNoOfDays() > 0) ? booking.getNoOfDays() * booking.getPricePerDay() : "" %>
                            <%= (booking.getDistanceKm() > 0) ? booking.getDistanceKm() * booking.getPricePerKm() : "" %>
                        </td>
                        <td>
                            <%
                                Driver driver = driverService.getDriverById(booking.getDriverId());
                                String driverName = driver.getFirstName() + " " + driver.getLastName();
                            %>
                            <%= driverName %>
                        </td>
                        <td>
                            <%
                                Customer customer = customerService.getCustomerByBookingId(booking.getId());
                                String customerName = customer.getFirstName() + " " + customer.getLastName();
                            %>
                            <%= customerName %>
                        </td>
                        <% if ("admin".equals(role)) { %>
                        <td>
                            <a href="booking?delete=<%= booking.getId() %>"
                               class="btn btn-danger btn-sm w-100">Delete</a>
                        </td>
                        <% }%>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

