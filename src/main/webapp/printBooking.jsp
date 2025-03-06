<%@ page import="com.megacity.bookings.model.Booking" %>
<%@ page import="com.megacity.bookings.model.Driver" %>
<%@ page import="com.megacity.bookings.service.DriverService" %>
<%@ page import="com.megacity.bookings.model.Customer" %>
<%@ page import="com.megacity.bookings.service.CustomerService" %>
<%@ page import="com.megacity.bookings.service.CabService" %>
<%@ page import="com.megacity.bookings.model.Cab" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int userId = (int) session.getAttribute("userId");
    String pageTitle = (String) request.getAttribute("title");
    DriverService driverService = new DriverService();
    CabService cabService = new CabService();

    Booking booking = (Booking) request.getAttribute("booking");
    CustomerService customerService = new CustomerService();
    Customer customer = customerService.getCustomerByUserId(userId);
    Cab cab = cabService.getCabById(booking.getCabId());
%>
<html>
<head>
    <title><%= pageTitle %>
    </title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
<div class="container mt-5">
    <div class="row">
        <div class="col-sm-8 bg-light">
            <h2 class="text-center mb-3 mt-3">MegaCity Bookings</h2>
            <table class="table table-striped table-bordered ">
                <tbody>
                <tr>
                    <th colspan="2" class="text-primary">Booking Order Details</th>
                </tr>
                <tr>
                    <th>Order No:</th>
                    <td><%= booking.getOrderNo() %>
                    </td>
                </tr>

                <tr>
                    <th>Order Date:</th>
                    <td><%= booking.getOrderDate() %>
                    </td>
                </tr>

                <tr>
                    <th colspan="2" class="text-primary">Trip Details</th>
                </tr>

                <% if (booking.getNoOfDays() > 0) { %>
                <tr>
                    <th>No of Days</th>
                    <td><%= booking.getNoOfDays() %> Days</td>
                </tr>
                <% } else if (booking.getDistanceKm() > 0) { %>
                <tr>
                    <th>Distance</th>
                    <td><%= booking.getDistanceKm() %> KM</td>
                </tr>
                <% } %>

                <tr>
                    <th>Destination:</th>
                    <td><%= booking.getDestination() %>
                    </td>
                </tr>

                <tr>
                    <th>Trip Date:</th>
                    <td><%= booking.getTripDate() %>
                    </td>
                </tr>
                <tr>
                    <th>Driver Name:</th>
                    <td>
                        <%
                            Driver driver = driverService.getDriverById(booking.getDriverId());
                            String driverName = driver.getFirstName() + " " + driver.getLastName();
                        %>
                        <%= driverName %>
                    </td>
                </tr>
                <tr>
                    <th>Cab:</th>
                    <td><%= cab.getCabType() %> / <%= cab.getModel() %>
                    </td>
                </tr>

                <tr>
                    <th colspan="2" class="text-primary">Customer Details</th>
                </tr>
                <tr>
                    <th>Customer Name:</th>
                    <td><%= customer.getFirstName() %> <%= customer.getLastName() %>
                    </td>
                </tr>
                <tr>
                    <th>Customer Address:</th>
                    <td><%= customer.getAddress() %>
                    </td>
                </tr>
                <tr>
                    <th>Customer Phone Number:</th>
                    <td><%= customer.getPhoneNumber() %>
                    </td>
                </tr>

                <tr>
                    <th colspan="2" class="text-primary">Bill Detail <small>(Tax Included)</small></th>
                </tr>

                <% if (booking.getNoOfDays() > 0) { %>
                <tr>
                    <th>Price per Day</th>
                    <td><%= booking.getPricePerDay() %> LKR</td>
                </tr>
                <% } else if (booking.getDistanceKm() > 0) { %>
                <tr>
                    <th>Price per KM</th>
                    <td><%= booking.getPricePerKm() %> LKR</td>
                </tr>
                <% } %>

                <% if (booking.getNoOfDays() > 0) { %>
                <tr>
                    <th>No of Days</th>
                    <td><%= booking.getNoOfDays() %> Days</td>
                </tr>
                <% } else if (booking.getDistanceKm() > 0) { %>
                <tr>
                    <th>Distance</th>
                    <td><%= booking.getDistanceKm() %> KM</td>
                </tr>
                <% } %>

                <tr>
                    <th>Total Bill:</th>
                    <td>
                        <%= (booking.getNoOfDays() > 0) ? (booking.getNoOfDays() * booking.getPricePerDay()) : "" %>
                        <%= (booking.getDistanceKm() > 0) ? booking.getDistanceKm() * booking.getPricePerKm() : "" %>
                        LKR
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
