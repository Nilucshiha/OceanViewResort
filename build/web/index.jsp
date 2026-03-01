<%-- 
    Document   : index
    Created on : Feb 22, 2026, 11:30:49 AM
    Author     : Nilucshiha
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login | Ocean View Resort</title>
    <link rel="stylesheet" href="css/auth.css">
</head>
<body>

<div class="auth-container">
    <div class="auth-card">
        <h2>Ocean View Resort</h2>
        <p class="subtitle">Login to your account</p>

        <form action="LoginServlet" method="post">
            <div class="form-group">
                <label>Username</label>
                <input type="text" name="username" required>
            </div>

            <div class="form-group">
                <label>Password</label>
                <input type="password" name="password" required>
            </div>

            <input type="submit" value="Login" class="btn-primary">
        </form>

        <p class="switch">
            Don’t have an account?
            <a href="register.jsp">Register here</a>
        </p>

        <% if (request.getAttribute("error") != null) { %>
            <p class="error"><%= request.getAttribute("error") %></p>
        <% } %>
    </div>
</div>

</body>
</html>