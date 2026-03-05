<%-- 
    Document   : register
    Created on : Feb 28, 2026, 6:16:40 PM
    Author     : Nilucshiha
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register | Ocean View Resort</title>
    <link rel="stylesheet" href="css/auth.css">
</head>
<body>

<div class="auth-container">
    <div class="auth-card">
        <h2>Create Account</h2>
        <p class="subtitle">Join Ocean View Resort System</p>

        <form action="RegisterServlet" method="post">
            <div class="form-group">
                <label>Username</label>
                <input type="text" name="username" required>
            </div>

            <div class="form-group">
                <label>Password</label>
                <input type="password" name="password" required>
            </div>

            <div class="form-group">
                <label>Role</label>
                <select name="role" required>
                    <option value="USER">Receptionist</option>
                    <option value="ADMIN">Admin</option>
                </select>
            </div>

            <input type="submit" value="Register" class="btn-primary">
        </form>

        <p class="switch">
            Already have an account?
            <a href="index.jsp">Login here</a>
        </p>

        <% if (request.getAttribute("error") != null) { %>
            <p class="error"><%= request.getAttribute("error") %></p>
        <% } %>

        <% if (request.getAttribute("success") != null) { %>
            <p class="success"><%= request.getAttribute("success") %></p>
        <% } %>
    </div>
</div>

</body>
</html>