<%-- 
    Document   : calculateBill
    Created on : Mar 5, 2026, 3:33:02?PM
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

    <a href="home.jsp" class="active">Dashboard</a>
    <a href="addReservation.jsp">Add Reservation</a>
    <a href="viewReservation.jsp">View Reservations</a>

    <% if ("ADMIN".equals(user.getRole())) { %>
        <a href="report.jsp">Reports</a>
    <% } %>
    <a href="CalculateBillServlet">Calculate & Print Bill</a>
    <a href="help.jsp">Help</a>

    <a href="LogoutServlet" class="logout">Logout</a>
</div>

<!-- MAIN CONTENT -->
<div class="main-content">
    <div class="reservation-wrapper">

        <h1>Calculate & Print Bill</h1>

  
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
                    
                       <a href="GenerateBillServlet?reservationNumber=${r.reservationNumber}" 
   class="btn-bill">
   Calculate Bill
</a>
   
   <form action="SaveBillServlet" method="post" style="display:inline;">
        <input type="hidden" name="reservationId" value="${r.reservationId}">
        <button type="submit" class="btn-save">
            Save Bill
        </button>
    </form>
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