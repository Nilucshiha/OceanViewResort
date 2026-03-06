/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import dao.BillDAO;
import dao.GuestDAO;
import dao.ReservationDAO;
import dao.RoomDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.Reservation;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import model.Bill;
import model.Guest;
import model.Room;
import model.User;
import service.BillingService;
import service.ReservationService;

@WebServlet(name = "GenerateBillServlet", urlPatterns = {"/GenerateBillServlet"})
public class GenerateBillServlet extends HttpServlet {

    private ReservationDAO reservationDAO = new ReservationDAO();
    private RoomDAO roomDAO = new RoomDAO();
    private GuestDAO guestDAO = new GuestDAO(); 
    private BillDAO billDAO = new BillDAO(); 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String reservationNumber = request.getParameter("reservationNumber");
        if (reservationNumber == null || reservationNumber.isEmpty()) {
            response.getWriter().println("Invalid Reservation Number!");
            return;
        }

        Reservation reservation = reservationDAO.getReservationByNumber1(reservationNumber);
        if (reservation == null) {
            request.setAttribute("errorMessage", "Reservation not found!");
            request.getRequestDispatcher("generateBill.jsp").forward(request, response);
            return;
        }

        Room room = roomDAO.getRoomById(reservation.getRoomId());
        if (room == null) {
            request.setAttribute("errorMessage", "Room not found!");
            request.getRequestDispatcher("generateBill.jsp").forward(request, response);
            return;
        }
        
Guest guest = guestDAO.getGuestById(reservation.getGuestId());
        if (guest != null) {
            reservation.setGuestName(guest.getGuestName());
            reservation.setAddress(guest.getAddress());
            reservation.setContactNumber(guest.getContactNumber());
        }
        
        long nights = ChronoUnit.DAYS.between(reservation.getCheckIn(), reservation.getCheckOut());
        if (nights <= 0) nights = 1;

       
        double totalAmount = nights * room.getPricePerNight();

        Bill bill = new Bill();
            bill.setReservationId(reservation.getReservationId());
            bill.setNumberOfNights((int) nights);
            bill.setTotalAmount(totalAmount);

           if (!billDAO.billExists(reservation.getReservationId())) {
                billDAO.saveBill(bill);
            }
           
        request.setAttribute("reservation", reservation);
        request.setAttribute("room", room);
        request.setAttribute("nights", nights);
        request.setAttribute("totalAmount", totalAmount);

        request.getRequestDispatcher("generateBill.jsp").forward(request, response);
    }
}