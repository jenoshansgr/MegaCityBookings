<%@ page import="com.megacity.bookings.model.Cab" %>
<%@ page import="java.util.List" %>
<%@ page import="com.megacity.bookings.model.CabType" %>
<%@ page import="java.util.Objects" %>
<%
    List<Cab> cabList = (List) request.getAttribute("cabList");
    List<CabType> cabTypeList = (List) request.getAttribute("cabTypeList");
    Cab cabRecord = (Cab) request.getAttribute("cab");


%>

<h2>Cab</h2>

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
            <form method="post" action="cab<%= (cabRecord.getId() > 0) ? "?edit=" +cabRecord.getId() : "" %>">
                <div class="mb-3">
                    <label for="model" class="form-label">Model</label>
                    <input type="text" class="form-control" name="model" id="model" required
                           value="<%= (cabRecord.getModel() != null) ? cabRecord.getModel() : "" %>">
                </div>
                <div class="mb-3">
                    <label for="number" class="form-label">Numberplate NO</label>
                    <input type="text" class="form-control" name="number" id="number" required
                           value="<%= (cabRecord.getNumber() != null) ? cabRecord.getNumber() : "" %>">
                </div>

                <div class="mb-3">
                    <label for="cabType" class="form-label">Cab Type</label>
                    <select class="form-select" aria-label="Cab Type" id="cabType" name="cabTypeId">
                        <option value="">Select Cab Type</option>
                        <% for (CabType cabType : cabTypeList) { %>
                        <option value="<%= cabType.getId() %>" <%= (cabRecord.getCabTypeId() == cabType.getId()) ? "selected" : "" %>>
                            <%= cabType.getName() %> (PPD : <%= cabType.getPricePerDay() %> / PPK
                            : <%= cabType.getPricePerKm() %>)
                        </option>
                        <% } %>
                    </select>
                </div>

                <div class="mb-3">
                    <label for="status" class="form-label">Status</label>
                    <select class="form-select" aria-label="Status" id="status" name="status">
                        <option value="">Select Status</option>
                        <option value="available" <%= (Objects.equals(cabRecord.getStatus(), "available")) ? "selected" : "" %>>
                            Available
                        </option>
                        <option value="na" <%= (Objects.equals(cabRecord.getStatus(), "na")) ? "selected" : "" %>>Not
                            Available
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
                        <th>Cab Type</th>
                        <th>Cab Model</th>
                        <th>Price</th>
                        <th>Status</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (Cab cab : cabList) { %>
                    <tr>
                        <td><%= cab.getId() %>
                        </td>
                        <td><%= cab.getCabType() %>
                        </td>
                        <td><%= cab.getModel() %>
                        </td>
                        <td>PPD: <%= cab.getPricePerDay() %>
                            <br>
                            PPKm: <%= cab.getPricePerKm() %>
                        </td>
                        <td><%= cab.getStatus() %>
                        </td>
                        <td>
                            <a href="cab?edit=<%= cab.getId() %>" class="btn btn-secondary btn-sm w-100 mb-1">Edit</a>
                            <br>
                            <a href="cab?delete=<%= cab.getId() %>" class="btn btn-danger btn-sm w-100">Delete</a>
                        </td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

