<%-- 
    Document   : viewReservation
    Created on : Feb 22, 2026, 11:32:27?AM
    Author     : Nilucshiha
--%>
<%@ page import="model.User" %>
<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
    <title>All Reservations</title>
    <link rel="stylesheet" href="css/dashboard.css">
    <link rel="stylesheet" href="css/viewReservation.css">
</head>

<body class="dashboard">

<!-- SIDEBAR -->
<div class="sidebar">
    <h2 class="logo">Ocean View Resort</h2>
    <p class="role">Role: <%= user.getRole() %></p>

    <a href="home.jsp">Dashboard</a>
    <a href="addReservation.jsp">Add Reservation</a>
    <a href="viewReservation.jsp" class="active">View Reservations</a>

    <% if ("ADMIN".equals(user.getRole())) { %>
        <a href="report.jsp">Reports</a>
    <% } %>

    <a href="LogoutServlet" class="logout">Logout</a>
</div>

<!-- MAIN CONTENT -->
<div class="main-content">
    <div class="reservation-wrapper">

        <h1>All Reservations</h1>

        <!-- FILTER -->
        <div class="filter-card">
            <form method="get" action="ViewReservationServlet" class="filter-form">
                <input type="text" name="reservationNumber"
                       placeholder="Enter Reservation Number">

                <input type="submit" value="Search">
                <a href="ViewReservationServlet">Show All</a>
            </form>
        </div>

        <!-- ERROR -->
        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>

        <!-- TABLE -->
        <c:if test="${not empty reservations}">
            <div class="table-card">
    <table>
        <tr>
            <th>Reservation No</th>
            <th>Guest Name</th>
            <th>Address</th>
            <th>Contact</th>
            <th>Room Type</th>
            <th>Check-in</th>
            <th>Check-out</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>

        <c:forEach var="r" items="${reservations}">
            <tr>
                <td>${r.reservationNumber}</td>
                <td>${r.guestName}</td>
                <td>${r.address}</td>
                <td>${r.contactNumber}</td>
                <td>${r.roomType}</td>
                <td>${r.checkIn}</td>
                <td>${r.checkOut}</td>
                <td>${r.status}</td>

                <td class="actions">
                    <!-- EDIT -->
                    <a class="btn edit"
                       href="EditReservationServlet?reservationNumber=${r.reservationNumber}">
                        Edit
                    </a>

                    <!-- DELETE -->
                    <a class="btn delete"
                       href="DeleteReservationServlet?reservationNumber=${r.reservationNumber}"
                       onclick="return confirm('Are you sure you want to delete this reservation?');">
                        Delete
                    </a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
        </c:if>

            

    </div>
</div>

</body>
</html>