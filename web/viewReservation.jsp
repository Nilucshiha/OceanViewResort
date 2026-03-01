<%-- 
    Document   : viewReservation
    Created on : Feb 22, 2026, 11:32:27?AM
    Author     : Nilucshiha
--%>

<%@ page import="model.User" %>
<%@ page session="true" %>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    // SESSION CHECK
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Reservation - Ocean View Resort</title>
    <link rel="stylesheet" href="css/style.css">
</head>

<body class="dashboard">

<!-- SIDEBAR MENU -->
<div class="sidebar">
    <h2 class="logo">Ocean View Resort</h2>
    <p class="role">Role: <%= user.getRole() %></p>

    <a href="home.jsp">? Home</a>
    <a href="addReservation.jsp">? Add Reservation</a>
    <a href="viewReservation.jsp" class="active">? View Reservations</a>

    <% if ("ADMIN".equals(user.getRole())) { %>
        <a href="report.jsp">? Reports</a>
    <% } %>

    <a href="LogoutServlet" class="logout">? Logout</a>
</div>

<!-- MAIN CONTENT -->
<div class="main-content">
    <h1>View Reservation</h1>

    <!-- SUCCESS MESSAGE -->
    <c:if test="${not empty sessionScope.success}">
        <p class="success">${sessionScope.success}</p>
        <c:remove var="success" scope="session"/>
    </c:if>

    <!-- ERROR MESSAGE -->
    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>

    <!-- SEARCH FORM -->
    <form action="ViewReservationServlet" method="get" class="search-form">
        <label>Reservation Number:</label>
        <input type="text" name="reservationNumber"
               placeholder="Enter reservation number" required>
        <input type="submit" value="Search">
    </form>

    <!-- DISPLAY RESERVATION DETAILS -->
    <c:if test="${not empty reservation}">
        <div class="reservation-details">
            <h2>Reservation Details</h2>

            <table>
                <tr>
                    <th>Reservation No</th>
                    <td>${reservation.reservationNumber}</td>
                </tr>
                <tr>
                    <th>Guest Name</th>
                    <td>${reservation.guestName}</td>
                </tr>
                <tr>
                    <th>Address</th>
                    <td>${reservation.address}</td>
                </tr>
                <tr>
                    <th>Contact</th>
                    <td>${reservation.contactNumber}</td>
                </tr>
                <tr>
                    <th>Room Type</th>
                    <td>${reservation.roomType}</td>
                </tr>
                <tr>
                    <th>Check-in</th>
                    <td>${reservation.checkIn}</td>
                </tr>
                <tr>
                    <th>Check-out</th>
                    <td>${reservation.checkOut}</td>
                </tr>
                <tr>
                    <th>Status</th>
                    <td>${reservation.status}</td>
                </tr>
            </table>
        </div>
    </c:if>

</div>

</body>
</html>