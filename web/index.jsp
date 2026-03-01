<%-- 
    Document   : index
    Created on : Feb 22, 2026, 11:30:49 AM
    Author     : Nilucshiha
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Ocean View Resort - Login</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body class="login-bg">

<div class="login-container">
    <h2>Ocean View Resort</h2>
    <p class="subtitle">Staff Login</p>

    <form action="LoginServlet" method="post">

        <label>Username</label>
        <input type="text" name="username" placeholder="Enter username" required>

        <label>Password</label>
        <input type="password" name="password" placeholder="Enter password" required>

        <input type="submit" value="Login" class="btn">

    </form>

    <!-- ERROR MESSAGE -->
    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>

    <!-- SUCCESS MESSAGE -->
    <c:if test="${not empty success}">
        <p class="success">${success}</p>
    </c:if>

    <div class="links">
        <p>New staff member?
            <a href="register.jsp">Create an account</a>
        </p>
    </div>
</div>

</body>
</html>

