<%-- 
    Document   : help
    Created on : Mar 4, 2026, 1:34:40?PM
    Author     : Nilucshiha
--%>
<%-- 
    Document   : help
    Created on : Mar 4, 2026, 1:34:40?PM
    Author     : Nilucshiha
--%>
<%@page import="model.User"%>
<%@ page session="true" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Help - Ocean View Resort</title>
    <link rel="stylesheet" href="css/dashboard.css">
    <style>
        /* BODY & LAYOUT */
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            background: #f4f6f9;
        }

        .sidebar {
            width: 250px;
            background-color: #043068;
            color: #fff;
            position: fixed;
            top: 0;
            bottom: 0;
            left: 0;
            padding: 25px 15px;
            display: flex;
            flex-direction: column;
            box-shadow: 3px 0 15px rgba(0,0,0,0.2);
        }

        .sidebar .logo {
            font-size: 24px;
            font-weight: bold;
            text-align: center;
            margin-bottom: 25px;
            letter-spacing: 1px;
        }

        .sidebar .role {
            font-size: 14px;
            text-align: center;
            margin-bottom: 30px;
            color: #cce0ff;
        }

        .sidebar a {
            display: flex;
            align-items: center;
            padding: 12px 15px;
            margin: 8px 0;
            color: #fff;
            border-radius: 8px;
            font-weight: 500;
            text-decoration: none;
            transition: all 0.3s ease;
        }

        .sidebar a:hover, .sidebar a.active {
            background-color: #0077b6;
            box-shadow: 2px 2px 12px rgba(0,0,0,0.2);
        }

        .sidebar a.logout {
            margin-top: auto;
            background-color: #d62828;
            font-weight: bold;
            text-align: center;
        }

        .sidebar a.logout:hover {
            background-color: #a11717;
        }

        .main-content {
            margin-left: 270px;
            padding: 40px;
            min-height: 100vh;
            background: linear-gradient(120deg, #e0eafc, #cfdef3);
        }

        /* HELP CARD */
        .help-card {
            background: #fff;
            padding: 30px 40px;
            max-width: 800px;
            margin: 40px auto;
            border-radius: 15px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.15);
            margin-right: 750px;
        }

        .help-card h1 {
            text-align: center;
            margin-bottom: 25px;
            color: #043068;
            font-size: 32px;
        }

        .help-card ul {
            list-style: none;
            padding: 0;
        }

        .help-card ul li {
            background-color: #f0f4ff;
            margin: 10px 0;
            padding: 15px 20px;
            border-left: 6px solid #0077b6;
            border-radius: 8px;
            font-size: 16px;
            line-height: 1.5;
            transition: background 0.3s;
        }

        .help-card ul li b {
            color: #0077b6;
        }

        .help-card ul li:hover {
            background-color: #dbe6ff;
        }

        /* Responsive */
        @media (max-width: 992px) {
            .sidebar {
                width: 60px;
                padding: 25px 5px;
            }
            .sidebar .logo, .sidebar .role {
                display: none;
            }
            .sidebar a {
                justify-content: center;
                padding: 12px 0;
                font-size: 0;
            }
            .main-content {
                margin-left: 80px;
                padding: 30px 20px;
            }
        }
    </style>
</head>
<body>
    <!-- SIDEBAR -->
    <div class="sidebar">
        <h2 class="logo">Ocean View Resort</h2>
        <p class="role">Role: <%= user.getRole() %></p>

        <a href="home.jsp">Dashboard</a>
        <a href="addReservation.jsp">Add Reservation</a>
        <a href="viewReservation.jsp">View Reservations</a>
        <% if ("ADMIN".equals(user.getRole())) { %>
            <a href="report.jsp">Reports</a>
        <% } %>
        <a href="CalculateBillServlet">Calculate & Print Bill</a>
        <a href="help.jsp" class="active">Help</a>
        <a href="LogoutServlet" class="logout">Logout</a>
    </div>

    <!-- MAIN CONTENT -->
    <div class="main-content">
        <div class="help-card">
            <h1>Help Section</h1>
            <ul>
                <li><b>Adding Reservation:</b> Go to "Add Reservation" page and fill all details.</li>
                <li><b>View Reservation:</b> Check all reservations in "View Reservations".</li>
                <li><b>Generate Bill:</b> Use "Generate Bill" page to compute total cost and print invoice.</li>
                <li><b>Payment:</b> Confirm payment after checking the bill details.</li>
                <li><b>Reports:</b> Check "Room Occupancy Report" for booked rooms and "Total Revenue Report" for earnings.</li>
                <li><b>Exit:</b> Use the "Logout" button to safely close the session.</li>
            </ul>
        </div>
    </div>
</body>
</html>