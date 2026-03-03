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
import java.time.temporal.ChronoUnit;
import model.Bill;
import model.Reservation;
import service.BillingService;
import service.ReservationService;
import util.DatabaseConnection;

/**
 *
 * @author Nilucshiha
 */
@WebServlet(name = "SaveBillServlet", urlPatterns = {"/SaveBillServlet"})
public class SaveBillServlet extends HttpServlet {

    private BillingService billService = new BillingService();
    private ReservationService reservationService = new ReservationService();

 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int reservationId = Integer.parseInt(request.getParameter("reservationId"));
            Reservation res = reservationService.getReservationById(reservationId);

            if (res == null) {
                request.setAttribute("error", "Reservation not found!");
                request.getRequestDispatcher("generateBill.jsp").forward(request, response);
                return;
            }

            // Calculate total nights
            long totalNights = java.time.temporal.ChronoUnit.DAYS.between(
                    res.getCheckIn(), res.getCheckOut()
            );

            // Example: Room rate based on room type
            double ratePerNight = switch(res.getRoomType()) {
                case "STANDARD" -> 1000;
                case "DELUXE" -> 2500;
                case "SUITE" -> 4000;
                default -> 0;
            };

            double totalAmount = totalNights * ratePerNight;

            Bill bill = new Bill(reservationId, (int) totalNights, totalAmount);

            // Save the bill
            billService.createBill(bill);

            request.setAttribute("msg", "Bill generated successfully! Total: " + totalAmount);
            request.getRequestDispatcher("generateBill.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid Reservation ID!");
            request.getRequestDispatcher("generateBill.jsp").forward(request, response);
        }
    }
}