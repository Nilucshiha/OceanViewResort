/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.ReservationDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import model.Reservation;
import model.User;
import service.ReservationService;

/**
 *
 * @author Nilucshiha
 */
@WebServlet("/EditReservationServlet")
public class EditReservationServlet extends HttpServlet {

    private final ReservationDAO reservationDAO = new ReservationDAO();
    private ReservationService reservationService = new ReservationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check login
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        String reservationNumber = request.getParameter("reservationNumber");
        if (reservationNumber == null || reservationNumber.isEmpty()) {
            response.sendRedirect("viewReservation.jsp");
            return;
        }

        Reservation reservation = reservationDAO.getReservationByNumber(reservationNumber);
        if (reservation == null) {
            request.setAttribute("error", "Reservation not found");
        } else {
            request.setAttribute("reservation", reservation);
        }

        request.getRequestDispatcher("editReservation.jsp").forward(request, response);
    }

     @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    try {

        String reservationNumber = request.getParameter("reservationNumber");
        String guestName = request.getParameter("guestName");
        String contactNumber = request.getParameter("contactNumber");
        String address = request.getParameter("address");
        String roomType = request.getParameter("roomType");
        String status = request.getParameter("status");
        String checkIn = request.getParameter("checkIn");
        String checkOut = request.getParameter("checkOut");

        Reservation reservation = new Reservation();

        reservation.setReservationNumber(reservationNumber);
        reservation.setGuestName(guestName);
        reservation.setContactNumber(contactNumber);
        reservation.setAddress(address);
        reservation.setRoomType(roomType);
        reservation.setStatus(status);
        reservation.setCheckIn(LocalDate.parse(checkIn));
        reservation.setCheckOut(LocalDate.parse(checkOut));

        String guestIdStr = request.getParameter("guestId");
        if (guestIdStr != null && !guestIdStr.isEmpty()) {
            reservation.setGuestId(Integer.parseInt(guestIdStr));
        }

        boolean updated = reservationService.updateReservation(reservation);

        if (updated) {
            response.sendRedirect("ViewReservationServlet");
        } else {
            request.setAttribute("error", "Failed to update reservation!");
            request.getRequestDispatcher("editReservation.jsp").forward(request, response);
        }

    } catch (Exception e) {
        e.printStackTrace();
        request.setAttribute("error", "Error updating reservation!");
        request.getRequestDispatcher("editReservation.jsp").forward(request, response);
    }
}
    
}