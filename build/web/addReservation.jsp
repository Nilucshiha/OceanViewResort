<%-- 
    Document   : addReservation
    Created on : Feb 22, 2026, 11:28:52?AM
    Author     : Nilucshiha
--%>

<%-- 
    Document   : addReservation
    Created on : Feb 22, 2026, 11:28:52?AM
    Author     : Nilucshiha
--%>

<%@ page import="model.User" %>
<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    User user = (User) session.getAttribute("user");
    if(user == null){
        response.sendRedirect("index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add Reservation - Ocean View Resort</title>
    <link rel="stylesheet" href="css/base.css">
    <link rel="stylesheet" href="css/dashboard.css">
    <link rel="stylesheet" href="css/reservation.css">
</head>
<body class="auth-body">

<div class="auth-container">
    <div class="auth-card">
        <h2>New Reservation</h2>
        <p class="auth-subtitle">Fill the details below</p>

        <!-- Success / Error Messages -->
        <c:if test="${not empty msg}">
            <div class="message success">${msg}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="message error">${error}</div>
        </c:if>

        <form action="ReservationServlet" method="post" class="auth-form">

            <div class="form-group">
                <input type="text" name="guestName" placeholder=" " required>
                <label>Guest Name</label>
            </div>

            <div class="form-group">
                <input type="text" name="address" placeholder=" " required>
                <label>Address</label>
            </div>

            <div class="form-group">
                <input type="text" name="contactNumber" placeholder=" " required>
                <label>Contact Number</label>
            </div>

            <div class="form-group">
                <select name="roomType" required>
                    <option value="" disabled selected hidden></option>
                    <option value="STANDARD">STANDARD</option>
                    <option value="DELUXE">DELUXE</option>
                    <option value="SUITE">SUITE</option>
                </select>
                <label>Room Type</label>
            </div>

            <div class="form-group">
                <input type="date" name="checkIn" placeholder=" " required>
                <label>Check-In Date</label>
            </div>

            <div class="form-group">
                <input type="date" name="checkOut" placeholder=" " required>
                <label>Check-Out Date</label>
            </div>

            <button type="submit" class="btn-submit">Add Reservation</button>
        </form>

        <p class="auth-footer"><a href="home.jsp">Back to Home</a></p>
    </div>
</div>

</body>
</html>

