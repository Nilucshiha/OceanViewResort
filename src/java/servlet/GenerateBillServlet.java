/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import com.sun.jdi.connect.spi.Connection;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.Reservation;
import java.sql.*;
import java.time.temporal.ChronoUnit;
import model.Bill;
import service.BillingService;
import service.ReservationService;


/**
 *
 * @author Nilucshiha
 */
@WebServlet(name = "GenerateBillServlet", urlPatterns = {"/GenerateBillServlet"})
public class GenerateBillServlet extends HttpServlet {

    private ReservationService reservationService = new ReservationService();
    private BillingService billingService = new BillingService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Fetch all reservations for dropdown
        List<Reservation> reservations = reservationService.getAllReservations();
        request.setAttribute("reservations", reservations);

        request.getRequestDispatcher("generateBill.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int reservationId = Integer.parseInt(request.getParameter("reservationId"));

        // Get reservation details
        Reservation res = reservationService.getReservationById(reservationId);

        // Calculate total nights
        long nights = ChronoUnit.DAYS.between(res.getCheckIn(), res.getCheckOut());
        if (nights <= 0) nights = 1; // fallback

        // Calculate amount (example: room rate per night)
        double ratePerNight = 1000; // you can fetch from roomType if needed
        double totalAmount = nights * ratePerNight;

        Bill bill = new Bill(reservationId, (int) nights, totalAmount);

        boolean success = billingService.createBill(bill);

        if(success){
            request.setAttribute("msg", "Bill generated successfully! Total: LKR " + totalAmount);
        } else {
            request.setAttribute("msg", "Failed to generate bill.");
        }

        // Forward to JSP with dropdown again
        List<Reservation> reservations = reservationService.getAllReservations();
        request.setAttribute("reservations", reservations);
        request.getRequestDispatcher("generateBill.jsp").forward(request, response);
    }
}