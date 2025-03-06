<%@ page import="com.megacity.bookings.model.Driver" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Objects" %>
<%@ page import="com.megacity.bookings.service.BookingService" %>
<%
    List<Driver> driverList = (List) request.getAttribute("driverList");
    Driver driverRecord = (Driver) request.getAttribute("driver");
    BookingService bookingService = new BookingService();
%>

<h2>Driver</h2>

<% if (request.getParameter("error") != null) { %>
<% String errorString = (String) request.getParameter("error"); %>
<div class="alert alert-danger" role="alert">
    <%= errorString %>
</div>
<% } else if (request.getParameter("success") != null) { %>
<% String successString = (String) request.getParameter("success"); %>
<div class="alert alert-success" role="alert">
    <%= successString %>
</div>
<% } %>

<div class="container">
    <div class="row">
        <div class="col-sm-4 pt-4 bg-light">
            <form method="post" action="driver<%= (driverRecord.getId() > 0) ? "?edit=" +driverRecord.getId() : "" %>">
                <div class="mb-3">
                    <label for="firstName" class="form-label">First Name</label>
                    <input type="text" class="form-control" name="firstName" id="firstName" required
                           value="<%= (driverRecord.getFirstName() != null) ? driverRecord.getFirstName() : "" %>">
                </div>

                <div class="mb-3">
                    <label for="lastName" class="form-label">Last Name</label>
                    <input type="text" class="form-control" name="lastName" id="lastName" required
                           value="<%= (driverRecord.getLastName() != null) ? driverRecord.getLastName() : "" %>">
                </div>

                <div class="mb-3">
                    <label for="licenseNo" class="form-label">License No</label>
                    <input type="text" class="form-control" name="licenseNo" id="licenseNo" required
                           value="<%= (driverRecord.getLicenseNo() != null) ? driverRecord.getLicenseNo() : "" %>">
                </div>

                <div class="mb-3">
                    <label for="licenseExpireDate" class="form-label">License Exp.</label>
                    <input type="date" class="form-control" name="licenseExpireDate" id="licenseExpireDate" required
                           value="<%= (driverRecord.getLicenseExpireDate() != null) ? driverRecord.getLicenseExpireDate() : "" %>">
                </div>

                <div class="mb-3">
                    <label for="status" class="form-label">Status</label>
                    <select class="form-select" aria-label="Status" id="status" name="status">
                        <option value="">Select Status</option>
                        <option value="available" <%= (Objects.equals(driverRecord.getStatus(), "available")) ? "selected" : "" %>>
                            Available
                        </option>
                        <option value="na" <%= (Objects.equals(driverRecord.getStatus(), "na")) ? "selected" : "" %>>
                            Booked
                        </option>
                    </select>
                </div>

                <% if (request.getParameter("error") != null) { %>
                <% String errorString = (String) request.getParameter("error"); %>
                <div class="text-danger mb-2"><%= errorString%>
                </div>
                <button type="submit" class="btn btn-danger w-100">Save</button>
                <% } else {%>
                <button type="submit" class="btn btn-primary w-100">Save</button>
                <% } %>
            </form>

        </div>
        <div class="col-sm-8">
            <h4>All Records</h4>
            <hr>
            <div class="table-responsive">
                <table class="table table-striped table-bordered">
                    <thead>
                    <tr>
                        <th>#ID</th>
                        <th>Name</th>
                        <th>License No</th>
                        <th>License Exp.</th>
                        <th>Status</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (Driver driver : driverList) { %>
                    <tr>
                        <td><%= driver.getId() %>
                        </td>
                        <td>
                            <%= driver.getFirstName() %>
                            <br>
                            <%= driver.getLastName() %>
                        </td>
                        <td><%= driver.getLicenseNo() %>
                        </td>
                        <td><%= driver.getLicenseExpireDate() %>
                        </td>
                        <td>
                            <% if (bookingService.isDriverAvailable(driver.getId())) { %>
                            Available
                            <% } else { %>
                            Booked
                            <% }%>
                        </td>
                        <td>
                            <a href="driver?edit=<%= driver.getId() %>"
                               class="btn btn-secondary btn-sm w-100 mb-1">Edit</a>
                            <br>
                            <a href="driver?delete=<%= driver.getId() %>" class="btn btn-danger btn-sm w-100">Delete</a>
                        </td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

