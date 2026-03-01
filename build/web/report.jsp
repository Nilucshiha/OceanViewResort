<%-- 
    Document   : report
    Created on : Feb 22, 2026, 11:31:24?AM
    Author     : Nilucshiha
--%>

<%@ page import="model.User" %>
<%@ page session="true" %>
<%
    User user = (User) session.getAttribute("user");
    if(user == null || !user.getRole().equals("ADMIN")){
        response.sendRedirect("index.jsp");
        return;
    }
%>
<html>
<head>
    <title>Reports - Ocean View Resort</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h2>Reports</h2>
<form action="ReportServlet" method="post">
    <label>Select Report:</label>
    <select name="reportType">
        <option value="Room Occupancy">Room Occupancy</option>
        <option value="Total Revenue">Total Revenue</option>
    </select>
    <input type="submit" value="Generate">
</form>

<c:if test="${not empty report}">
    <div style="margin-top:20px;">
        <%= request.getAttribute("report") %>
    </div>
</c:if>

<p><a href="home.jsp">Back to Home</a></p>
</body>
</html>
