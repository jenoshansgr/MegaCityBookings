<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String pageFile = (String) request.getAttribute("page");
    String pageTitle = (String) request.getAttribute("title");

    int userId = 0;
    String username = "";
    String role = "";

    if (session.getAttribute("userId") != null) {
        userId = (int) session.getAttribute("userId");
        username = (String) session.getAttribute("username");
        role = (String) session.getAttribute("role");
    }
%>
<html>
<head>
    <title><%= pageTitle %>
    </title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <div class="row">
        <nav class="navbar navbar-expand-lg bg-primary" data-bs-theme="dark">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">MegaCity Bookings</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                        aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
                    <ul class="navbar-nav">
                        <% if (userId > 0) {%>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                               aria-expanded="false">
                                Hello <%= username %>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item" href="login?logout=1">Logout</a></li>
                            </ul>
                        </li>
                        <% } else {%>
                        <li class="nav-item">
                            <a class="nav-link" href="login">Login</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="register">Sign Up</a>
                        </li>
                        <% }%>
                    </ul>
                </div>
            </div>
        </nav>
    </div>
    <div class="row pt-4">
        <% if (userId > 0) { %>
        <div class="col col-lg-3">
            <div class="list-group">
                <% if ("customer".equals(role)) { %>
                <a href="booking"
                   class="list-group-item list-group-item-action <%= ("booking".equals(pageFile)) ? "active aria-current=\"true\"" : "" %>">My
                    Booking</a>
                <% } else if ("admin".equals(role)) { %>
                <a href="admin"
                   class="list-group-item list-group-item-action <%= ("admin".equals(pageFile)) ? "active aria-current=\"true\"" : "" %>">Dashboard</a>
                <a href="booking"
                   class="list-group-item list-group-item-action <%= ("booking".equals(pageFile)) ? "active aria-current=\"true\"" : "" %>">Booking</a>
                <a href="cab"
                   class="list-group-item list-group-item-action <%= ("cab".equals(pageFile)) ? "active aria-current=\"true\"" : "" %>">Cabs</a>
                <a href="cabType"
                   class="list-group-item list-group-item-action <%= ("cabType".equals(pageFile)) ? "active aria-current=\"true\"" : "" %>">Cab
                    Type</a>
                <a href="driver"
                   class="list-group-item list-group-item-action <%= ("driver".equals(pageFile)) ? "active aria-current=\"true\"" : "" %>">Driver</a>
                <a href="customer"
                   class="list-group-item list-group-item-action <%= ("customer".equals(pageFile)) ? "active aria-current=\"true\"" : "" %>">Customer</a>
                <% } %>
            </div>
        </div>
        <% } %>
        <div class="col <%= (userId > 0) ? "col-lg-9" : "" %>">

            <% if (userId == 0) {%>
            <% if ("index".equals(pageFile)) { %>
            <jsp:include page="index.jsp"/>
            <% } else if ("login".equals(pageFile)) { %>
            <jsp:include page="user/login.jsp"/>
            <% } else if ("register".equals(pageFile)) { %>
            <jsp:include page="user/register.jsp"/>
            <% } else { %>
            <p>Page not found</p>
            <% } %>
            <% } else {%>
            <% if ("customer".equals(role)) { %>
            <% if ("customer-booking".equals(pageFile)) { %>
            <jsp:include page="booking.jsp"/>
            <% } else {%>
            <p>Page not found</p>
            <% }%>
            <% } else if ("admin".equals(role)) { %>
            <% if ("admin".equals(pageFile)) { %>
            <jsp:include page="admin.jsp"/>
            <% } else if ("user".equals(pageFile)) { %>
            <jsp:include page="user.jsp"/>
            <% } else if ("admin-booking".equals(pageFile)) { %>
            <jsp:include page="booking.jsp"/>
            <% } else if ("cab".equals(pageFile)) { %>
            <jsp:include page="cab.jsp"/>
            <% } else if ("cabType".equals(pageFile)) { %>
            <jsp:include page="cabType.jsp"/>
            <% } else if ("driver".equals(pageFile)) { %>
            <jsp:include page="driver.jsp"/>
            <% } else if ("customer".equals(pageFile)) { %>
            <jsp:include page="customer.jsp"/>
            <% } else { %>
            <p>Page not found</p>
            <% } %>
            <% } else {%>
            <p>Page not found</p>
            <% }%>
            <% }%>
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"
        integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
</body>
</html>
