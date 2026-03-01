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

@WebServlet("/ViewReservationServlet")
public class ViewReservationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // SESSION CHECK
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        try {
            String reservationNumber = request.getParameter("reservationNumber");

            ReservationService service = new ReservationService();
            Reservation reservation = null;

            if (reservationNumber != null && !reservationNumber.isEmpty()) {
                reservation = service.getReservation(reservationNumber);
            }

            request.setAttribute("reservation", reservation);
            request.getRequestDispatcher("viewReservation.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error retrieving reservation!");
            request.getRequestDispatcher("viewReservation.jsp").forward(request, response);
        }
    }
}