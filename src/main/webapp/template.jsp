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
%>
<html>
<head>
    <title><%= pageTitle %></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
    <%-- Conditionally include the page based on the 'page' attribute --%>
    <% if ("index".equals(pageFile)) { %>
    <jsp:include page="index.jsp" />
    <% } else if ("admin".equals(pageFile)) { %>
    <jsp:include page="admin.jsp" />
    <% } else if ("user".equals(pageFile)) { %>
    <jsp:include page="user.jsp" />
    <% } else if ("booking".equals(pageFile)) { %>
    <jsp:include page="booking.jsp" />
    <% } else { %>
    <p>Page not found</p>
    <% } %>

    <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>
