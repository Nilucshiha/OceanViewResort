<%-- 
    Document   : home
    Created on : Feb 22, 2026, 11:29:50?AM
    Author     : Nilucshiha
--%>

<%@ page import="model.User" %>
<%@ page session="true" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Dashboard - Ocean View Resort</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body class="dashboard">

<!-- SIDEBAR -->
<div class="sidebar">
    <h2 class="logo">Ocean View</h2>
    <p class="role">Role: <%= user.getRole() %></p>

    <a href="home.jsp" class="active">? Dashboard</a>
    <a href="addReservation.jsp">? Add Reservation</a>
    <a href="viewReservation.jsp">? View Reservations</a>

    <% if ("ADMIN".equals(user.getRole())) { %>
        <a href="report.jsp">? Reports</a>
    <% } %>

    <a href="LogoutServlet" class="logout">? Logout</a>
</div>

<!-- MAIN CONTENT -->
<div class="main-content">
    <h1>Welcome, <%= user.getUsername() %> ?</h1>

    <div class="cards">
        <div class="card">
            <h3>New Reservation</h3>
            <p>Create a new guest booking</p>
            <a href="addReservation.jsp">Add</a>
        </div>

        <div class="card">
            <h3>View Reservations</h3>
            <p>Search and view booking details</p>
            <a href="viewReservation.jsp">View</a>
        </div>

        <% if ("ADMIN".equals(user.getRole())) { %>
        <div class="card">
            <h3>Reports</h3>
            <p>View system reports</p>
            <a href="report.jsp">Open</a>
        </div>
        <% } %>
    </div>
</div>

</body>
</html>