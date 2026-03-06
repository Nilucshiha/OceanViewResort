/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servlet;

/**
 *
 * @author Nilucshiha
 */
import service.ReservationService;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/ReservationServlet")
public class ReservationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        try {
            
            String guestName = request.getParameter("guestName");
            String address = request.getParameter("address");
            String contactNumber = request.getParameter("contactNumber");
            String roomType = request.getParameter("roomType");

            LocalDate checkIn = LocalDate.parse(request.getParameter("checkIn"));
            LocalDate checkOut = LocalDate.parse(request.getParameter("checkOut"));

            if (checkOut.isBefore(checkIn)) {
                request.setAttribute("error", "Check-out date must be after check-in date.");
                request.getRequestDispatcher("addReservation.jsp").forward(request, response);
                return;
            }

            ReservationService resService = new ReservationService();
            boolean success = resService.createReservation(
                    guestName, address, contactNumber, roomType, checkIn, checkOut
            );

            if (success) {
                session.setAttribute("success", "Reservation added successfully!");
                response.sendRedirect("viewReservation.jsp");
                return; 
            } else {
                request.setAttribute("error", "Unable to create reservation.");
                request.getRequestDispatcher("addReservation.jsp").forward(request, response);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Invalid input. Please check all fields.");
            request.getRequestDispatcher("addReservation.jsp").forward(request, response);
        }
    }
}