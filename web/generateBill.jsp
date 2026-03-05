<%-- 
    Document   : generateBill
    Created on : Mar 1, 2026, 9:24:31?PM
    Author     : Nilucshiha
--%>
<%@ page import="model.User, model.Reservation, model.Room" %>
<%@ page session="true" %>

<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("index.jsp");
        return;
    }

    Reservation reservation = (Reservation) request.getAttribute("reservation");
    Room room = (Room) request.getAttribute("room");
    Long nights = (Long) request.getAttribute("nights");
    Double totalAmount = (Double) request.getAttribute("totalAmount");

    if (reservation == null || room == null) {
%>
        <h2 style="color:red;">Reservation not found!</h2>
<%
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Generate Bill - Ocean View Resort</title>
    <link rel="stylesheet" href="css/billing.css">
    <link rel="stylesheet" href="css/dashboard.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
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
<div class="bill-container" id="billContainer">

    <header>
        <h1>Ocean View Resort</h1>
        <p>Bill / Invoice</p>
    </header>

    <section class="guest-info">
        <h2>Guest Details</h2>
        <table>
            <tr><th>Guest Name:</th><td><%= reservation.getGuestName() %></td></tr>
            <tr><th>Contact:</th><td><%= reservation.getContactNumber() %></td></tr>
            <tr><th>Address:</th><td><%= reservation.getAddress() %></td></tr>
        </table>
    </section>

    <section class="reservation-info">
        <h2>Reservation Details</h2>
        <table>
            <tr><th>Reservation No:</th><td><%= reservation.getReservationNumber() %></td></tr>
            <tr><th>Room Type:</th><td><%= room.getRoomType() %></td></tr>
            <tr><th>Check-In:</th><td><%= reservation.getCheckIn() %></td></tr>
            <tr><th>Check-Out:</th><td><%= reservation.getCheckOut() %></td></tr>
            <tr><th>Nights:</th><td><%= nights %></td></tr>
            <tr><th>Price per Night:</th><td>$<%= room.getPricePerNight() %></td></tr>
            <tr class="total-row"><th>Total Amount:</th><td>$<%= totalAmount %></td></tr>
        </table>
    </section>

    <div class="actions">
        <button onclick="window.print()">Print Bill</button>
        <button onclick="exportPDF()">Export as PDF</button>
    </div>

</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/1.4.1/html2canvas.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>

<script>
function exportPDF() {
    const bill = document.getElementById("billContainer");

    html2canvas(bill).then(canvas => {
        const imgData = canvas.toDataURL('image/png');
        const { jsPDF } = window.jspdf;
        const pdf = new jsPDF('p', 'mm', 'a4');

        // Calculate width & height
        const pageWidth = pdf.internal.pageSize.getWidth();
        const pageHeight = pdf.internal.pageSize.getHeight();
        const imgWidth = pageWidth - 20; // margins
        const imgHeight = canvas.height * imgWidth / canvas.width;

        pdf.addImage(imgData, 'PNG', 10, 10, imgWidth, imgHeight);
        pdf.save("bill_<%= reservation.getReservationNumber() %>.pdf");
    });
}
</script>
</body>
</html>