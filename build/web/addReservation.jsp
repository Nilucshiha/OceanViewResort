<%-- 
    Document   : addReservation
    Created on : Feb 22, 2026, 11:28:52?AM
    Author     : Nilucshiha
--%>

<%@ page import="model.User" %>
<%@ page session="true" %>
<%
    User user = (User) session.getAttribute("user");
    if(user == null){
        response.sendRedirect("index.jsp");
        return;
    }
%>
<html>
<head>
    <title>Add Reservation - Ocean View Resort</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h2>Add New Reservation</h2>
<form action="ReservationServlet" method="post">
    <label>Guest Name:</label>
    <input type="text" name="guestName" required><br>
    <label>Address:</label>
    <input type="text" name="address" required><br>
    <label>Contact Number:</label>
    <input type="text" name="contactNumber" required><br>
    <label>Room Type:</label>
    <select name="roomType" required>
        <option value="STANDARD">STANDARD</option>
        <option value="DELUXE">DELUXE</option>
        <option value="SUITE">SUITE</option>
    </select><br>
    <label>Check-In Date:</label>
    <input type="date" name="checkIn" required><br>
    <label>Check-Out Date:</label>
    <input type="date" name="checkOut" required><br>
    <input type="submit" value="Add Reservation">
</form>

<c:if test="${not empty msg}">
    <p style="color:green;">${msg}</p>
</c:if>

<p><a href="home.jsp">Back to Home</a></p>
</body>
</html>

