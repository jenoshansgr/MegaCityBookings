<%@ page import="com.megacity.bookings.model.Booking" %>
<%@ page import="java.util.List" %>
<%@ page import="com.megacity.bookings.service.CustomerService" %>
<%@ page import="com.megacity.bookings.service.DriverService" %>
<%@ page import="com.megacity.bookings.model.Driver" %>
<%@ page import="com.megacity.bookings.model.Customer" %>
<%@ page import="com.megacity.bookings.service.CabService" %>
<%@ page import="com.megacity.bookings.model.Cab" %>
<%
    int userId = (int) session.getAttribute("userId");
    List<Booking> bookingList = (List) request.getAttribute("bookingList");

    CustomerService customerService = new CustomerService();
    DriverService driverService = new DriverService();
    CabService cabService = new CabService();
    Customer customer = customerService.getCustomerByUserId(userId);

    String role = (String) session.getAttribute("role");
    Booking bookingRecord = new Booking();

    List<Driver> driverList = driverService.getAvailableDriverList();
    List<Cab> cabList = cabService.getAvailableCabList();
%>

<h2>Bookings</h2>

<hr>

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
    <div class="row ">
        <div class="col-sm-3 bg-light">
            <form method="post"
                  action="customerBooking<%= (bookingRecord.getId() > 0) ? "?edit=" +bookingRecord.getId() : "" %>">
                <div class="mb-3">
                    <label for="orderNo" class="form-label">Order No</label>
                    <input type="text" class="form-control" name="orderNo" id="orderNo" readonly
                           value="<%= (bookingRecord.getOrderNo() != null) ? bookingRecord.getOrderNo() : "NA" %>">
                </div>

                <div class="mb-3">
                    <label for="firstName" class="form-label">First Name</label>
                    <input type="text" class="form-control" name="firstName" id="firstName" readonly
                           value="<%= (customer.getFirstName() != null) ? customer.getFirstName() : "" %>">
                </div>

                <div class="mb-3">
                    <label for="lastName" class="form-label">Last Name</label>
                    <input type="text" class="form-control" name="lastName" id="lastName" readonly
                           value="<%= (customer.getLastName() != null) ? customer.getLastName() : "" %>">
                </div>

                <div class="mb-3">
                    <label for="address" class="form-label">Address</label>
                    <textarea class="form-control" id="address" name="address"
                              readonly><%= (customer.getAddress() != null) ? customer.getAddress() : "" %></textarea>
                </div>

                <div class="mb-3">
                    <label for="phoneNumber" class="form-label">Phone Number</label>
                    <input type="tel" class="form-control" name="phoneNumber" id="phoneNumber" readonly
                           value="<%= (customer.getPhoneNumber() != null) ? customer.getPhoneNumber() : "" %>">
                </div>

                <div class="mb-3">
                    <label for="tripDate" class="form-label">Trip Date</label>
                    <input type="date" class="form-control" name="tripDate" id="tripDate" required
                           value="<%= (bookingRecord.getTripDate() != null) ? bookingRecord.getTripDate() : "" %>">
                </div>

                <div class="mb-3">
                    <label for="destination" class="form-label">Destination</label>
                    <input type="text" class="form-control" name="destination" id="destination" required
                           value="<%= (bookingRecord.getDestination() != null) ? bookingRecord.getDestination() : "" %>">
                </div>

                <div class="mb-3">
                    <label for="distanceKm" class="form-label">Distance KMs</label>
                    <input type="number" class="form-control" oninput="toggleField()" name="distanceKm" id="distanceKm"
                           required
                           value="<%= (bookingRecord.getDistanceKm() > 0) ? bookingRecord.getDistanceKm() : "" %>">
                </div>

                <div class="mb-3">
                    <label for="noOfDays" class="form-label">No of Days</label>
                    <input type="number" class="form-control" oninput="toggleField()" name="noOfDays" id="noOfDays"
                           required
                           value="<%= (bookingRecord.getNoOfDays() > 0) ? bookingRecord.getNoOfDays() : "" %>">
                </div>

                <div class="mb-3">
                    <label for="driverId" class="form-label">Driver</label>
                    <select class="form-select" aria-label="Driver" id="driverId" name="driverId" required>
                        <% for (Driver driver : driverList) { %>
                        <option value="<%= driver.getId() %>" <%= (bookingRecord.getDriverId() == driver.getId()) ? "selected" : "" %>>
                            <%= driver.getFirstName() %> <%= driver.getLastName() %>
                        </option>
                        <% } %>
                    </select>
                </div>

                <div class="mb-3">
                    <label for="cabId" class="form-label">Cab</label>
                    <select class="form-select" aria-label="Cab" id="cabId" name="cabId" required>
                        <% for (Cab cab : cabList) { %>
                        <option value="<%= cab.getId() %>" <%= (bookingRecord.getCabId() == cab.getId()) ? "selected" : "" %>>
                            <%= cab.getModel() %> : <%= cab.getCabType() %> (PPD : <%= cab.getPricePerDay() %> / PPK
                            : <%= cab.getPricePerKm() %>)
                        </option>
                        <% } %>
                    </select>
                </div>

                <% if (request.getParameter("error") != null) { %>
                <% String errorString = (String) request.getParameter("error"); %>
                <div class="text-danger mb-2"><%= errorString%>
                </div>
                <button type="submit" class="btn btn-danger w-100">Book</button>
                <% } else {%>
                <button type="submit" class="btn btn-primary w-100">Book</button>
                <% } %>
            </form>
        </div>

        <div class="col-sm-9">
            <div class="table-responsive">
                <table class="table table-striped table-bordered">
                    <thead>
                    <tr>
                        <th>#ID</th>
                        <th>Order No/Order Date</th>
                        <th>Trip Date</th>
                        <th>Destination</th>
                        <th>Total Bill</th>
                        <th>Driver</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (Booking booking : bookingList) { %>
                    <tr>
                        <td><%= booking.getId() %>
                        </td>
                        <td>
                            <%= booking.getOrderNo() %>
                            <br>
                            <strong><%= booking.getOrderDate() %>
                            </strong>
                        </td>
                        <td><%= booking.getTripDate() %>
                        </td>
                        <td><%= booking.getDestination() %>
                        </td>
                        <td>
                            <%= (booking.getNoOfDays() > 0) ? (booking.getNoOfDays() * booking.getPricePerDay()) + " <br> For <br> " + booking.getNoOfDays() + " Days" : "" %>
                            <%= (booking.getDistanceKm() > 0) ? booking.getDistanceKm() * booking.getPricePerKm() + " <br> For <br> " + booking.getDistanceKm() + " KMs" : "" %>
                        </td>
                        <td>
                            <%
                                Driver driver = driverService.getDriverById(booking.getDriverId());
                                String driverName = driver.getFirstName() + " " + driver.getLastName();
                            %>
                            <%= driverName %>
                        </td>
                        <td>
                            <a href="printBooking?id=<%= booking.getId() %>"
                               class="btn btn-success btn-sm w-100">Print</a>
                        </td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<script>
    function toggleField() {
        let distanceKm = document.getElementById("distanceKm");
        let noOfDays = document.getElementById("noOfDays");

        if (distanceKm.value.trim() !== "") {
            noOfDays.value = "";    // Clear the field
            noOfDays.readOnly = true; // Make it readonly
        } else {
            noOfDays.readOnly = false; // Enable if field1 is empty
        }

        if (noOfDays.value.trim() !== "") {
            distanceKm.value = "";    // Clear the field
            distanceKm.readOnly = true; // Make it readonly
        } else {
            distanceKm.readOnly = false; // Enable if field1 is empty
        }
    }
</script>

