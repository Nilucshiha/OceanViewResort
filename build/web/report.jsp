<%-- 
    Document   : report
    Created on : Feb 22, 2026, 11:31:24?AM
    Author     : Nilucshiha
--%>
<%@ page import="model.User" %>
<%@ page session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    User user = (User) session.getAttribute("user");
    if (user == null || !"ADMIN".equals(user.getRole())) {
        response.sendRedirect("index.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Reports - Ocean View Resort</title>
    <link rel="stylesheet" href="css/dashboard.css">
    <link rel="stylesheet" href="css/report.css">
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
    <div class="report-wrapper">

        <h1>System Reports</h1>

        <!-- Report Selection -->
        <div class="report-card">
            <form action="ReportServlet" method="post" class="report-form">
                <label for="reportType">Select Report Type</label>
                <select name="reportType" id="reportType" required>
                    <option value="">-- Select Report --</option>
                    <option value="Room Occupancy">Room Occupancy</option>
                    <option value="Total Revenue">Total Revenue</option>
                </select>

                <input type="submit" value="Generate Report">
            </form>
        </div>

       
        <c:if test="${not empty report}">
            <div class="report-card report-output">
                <h3>Report Result</h3>
                ${report}
            </div>
        </c:if>

        <c:if test="${empty report}">
            <p class="report-empty">No report generated yet.</p>
        </c:if>

    </div>
</div>

</body>
</html>