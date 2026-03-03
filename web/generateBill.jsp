<%-- 
    Document   : generateBill
    Created on : Mar 1, 2026, 9:24:31?PM
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

<html>
<head>
    <title>Generate Bill - Ocean View Resort</title>
    <link rel="stylesheet" href="css/base.css">
    <link rel="stylesheet" href="css/reservation.css">
</head>
<body>

<div class="form-wrapper">
    <h2>Generate Bill</h2>

    <form action="GenerateBillServlet" method="post">

        <label>Guest Name / Reservation:</label>
        <select name="reservationId" required>
            <option value="">-- Select Reservation --</option>
            <c:forEach var="r" items="${reservations}">
                <option value="${r.reservationId}">
                    ${r.guestName} - ${r.roomType}
                </option>
            </c:forEach>
        </select>

        <input type="submit" value="Generate Bill">
    </form>

    <c:if test="${not empty msg}">
        <p class="success">${msg}</p>
    </c:if>

    <p><a href="home.jsp">Back to Dashboard</a></p>
</div>

</body>
</html>