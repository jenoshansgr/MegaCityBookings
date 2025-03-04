<%--
  Created by IntelliJ IDEA.
  User: praneethnidarshan
  Date: 03.03.25
  Time: 11:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String pageFile = (String) request.getAttribute("page");
    String pageTitle = (String) request.getAttribute("title");

    int userId = 0;

    if(session.getAttribute("userId") != null) {
        userId = (int) session.getAttribute("userId");
    }
%>
<html>
<head>
    <title><%= pageTitle %></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <div class="row">
        <nav class="navbar navbar-expand-lg bg-body-tertiary">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">MegaCity Bookings</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
                    <ul class="navbar-nav">
                        <% if (userId > 0) {%>
                            <li class="nav-item">
                                <a class="nav-link" href="cabs">Cabs</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="customer">Customers</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="logout">Login</a>
                            </li>
                        <% } else {%>
                            <li class="nav-item">
                                <a class="nav-link" href="login">Login</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="register">Register</a>
                            </li>
                        <% }%>
                    </ul>
                </div>
            </div>
        </nav>
    </div>

    <div class="row p-4">
        <%-- Conditionally include the page based on the 'page' attribute --%>
        <% if ("index".equals(pageFile)) { %>
            <jsp:include page="index.jsp" />
        <% } else if ("admin".equals(pageFile)) { %>
            <jsp:include page="admin.jsp" />
        <% } else if ("user".equals(pageFile)) { %>
            <jsp:include page="user.jsp" />
        <% } else if ("login".equals(pageFile)) { %>
            <jsp:include page="user/login.jsp" />
        <% } else if ("register".equals(pageFile)) { %>
            <jsp:include page="user/register.jsp" />
        <% } else if ("booking".equals(pageFile)) { %>
            <jsp:include page="booking.jsp" />
        <% } else { %>
        <p>Page not found</p>
        <% } %>
    </div>
</div>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
