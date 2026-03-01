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
import model.Reservation;

/**
 *
 * @author Nilucshiha
 */
@WebServlet(name = "EditReservationServlet", urlPatterns = {"/EditReservationServlet"})
public class EditReservationServlet extends HttpServlet {

    private ReservationDAO reservationDAO = new ReservationDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String reservationNumber = request.getParameter("reservationNumber");

        Reservation reservation =
                reservationDAO.getReservationByNumber(reservationNumber);

        request.setAttribute("reservation", reservation);
        request.getRequestDispatcher("editReservation.jsp")
               .forward(request, response);
    }
}