<%-- 
    Document   : register
    Created on : Feb 28, 2026, 6:16:40 PM
    Author     : Nilucshiha
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>User Registration</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<h2>Register New User</h2>

<form action="RegisterServlet" method="post">
    <label>Username:</label>
    <input type="text" name="username" required>

    <label>Password:</label>
    <input type="password" name="password" required>

    <label>Role:</label>
    <select name="role" required>
        <option value="RECEPTIONIST">Receptionist</option>
        <option value="ADMIN">Admin</option>
    </select>

    <input type="submit" value="Register">
</form>

<p class="error">${error}</p>
<p class="success">${success}</p>

<p>
    Already have an account? <a href="index.jsp">Login here</a>
</p>

</body>
</html>
