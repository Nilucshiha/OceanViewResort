<%-- 
    Document   : editReservation
    Created on : Mar 1, 2026, 2:22:58?PM
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
    <title>Edit Reservation</title>

    <!-- COMMON -->
    <link rel="stylesheet" href="css/dashboard.css">

    <!-- PAGE SPECIFIC -->
    <link rel="stylesheet" href="css/edit-reservation.css">
</head>

<body class="dashboard">

<!-- SIDEBAR -->
<div class="sidebar">
    <h2 class="logo">Ocean View Resort</h2>
    <p class="role">Role: <%= user.getRole() %></p>

    <a href="home.jsp">Dashboard</a>
    <a href="viewReservation.jsp" class="active">Reservations</a>
    <a href="LogoutServlet" class="logout">Logout</a>
</div>

<!-- MAIN -->
<div class="main-content">

    <div class="edit-header">
        <h1>Edit Reservation</h1>
        <span>Reservation No: ${reservation.reservationNumber}</span>
    </div>

    <form action="UpdateReservationServlet" method="post" class="edit-card">

        <input type="hidden" name="reservationNumber"
               value="${reservation.reservationNumber}">

        <div class="edit-grid">

            <div class="field">
                <label>Guest Name</label>
                <input type="text" name="guestName"
                       value="${reservation.guestName}" required>
            </div>

            <div class="field">
                <label>Contact Number</label>
                <input type="text" name="contactNumber"
                       value="${reservation.contactNumber}" required>
            </div>

            <div class="field full">
                <label>Address</label>
                <input type="text" name="address"
                       value="${reservation.address}" required>
            </div>

            <div class="field">
                <label>Room Type</label>
                <select name="roomType">
                    <option ${reservation.roomType=='STANDARD'?'selected':''}>STANDARD</option>
                    <option ${reservation.roomType=='DELUXE'?'selected':''}>DELUXE</option>
                    <option ${reservation.roomType=='SUITE'?'selected':''}>SUITE</option>
                </select>
            </div>

            <div class="field">
                <label>Status</label>
                <select name="status">
                    <option ${reservation.status=='CONFIRMED'?'selected':''}>CONFIRMED</option>
                    <option ${reservation.status=='CANCELLED'?'selected':''}>CANCELLED</option>
                </select>
            </div>

            <div class="field">
                <label>Check-In</label>
                <input type="date" name="checkIn"
                       value="${reservation.checkIn}" required>
            </div>

            <div class="field">
                <label>Check-Out</label>
                <input type="date" name="checkOut"
                       value="${reservation.checkOut}" required>
            </div>

        </div>

        <div class="edit-actions">
            <a href="viewReservation.jsp" class="btn-cancel">Cancel</a>
            <button type="submit" class="btn-save">Save Changes</button>
        </div>

    </form>

</div>

</body>
</html>