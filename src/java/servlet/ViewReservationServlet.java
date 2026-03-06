/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servlet;

/**
 *
 * @author Nilucshiha
 */
import model.Reservation;
import service.ReservationService;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ViewReservationServlet")
public class ViewReservationServlet extends HttpServlet {

    private final ReservationService reservationService = new ReservationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get filter input
        String reservationNumber = request.getParameter("reservationNumber");
        List<Reservation> reservations = new ArrayList<>();

       String searchAction = request.getParameter("search");

if (searchAction != null) {


    if (reservationNumber != null && !reservationNumber.trim().isEmpty()) {

        Reservation res = reservationService.getReservation(reservationNumber.trim());

        if (res != null) {
            reservations.add(res);
        } else {
            request.setAttribute("error", "No reservation found for number: " + reservationNumber);
        }

    } else {
        request.setAttribute("error", "Please enter a reservation number.");
    }

} else {
  
    reservations = reservationService.getAllReservations();
}

        request.setAttribute("reservations", reservations);
        request.setAttribute("totalReservations", reservations.size()); 

        request.getRequestDispatcher("viewReservation.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}