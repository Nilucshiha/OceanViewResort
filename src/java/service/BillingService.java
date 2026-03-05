/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author Nilucshiha
 */
import dao.BillDAO;
import dao.PaymentDAO;
import dao.ReservationDAO;
import dao.RoomDAO;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import model.Bill;
import model.Payment;
import model.Reservation;
import model.Room;

public class BillingService {

    private ReservationDAO reservationDAO = new ReservationDAO();
    private RoomDAO roomDAO = new RoomDAO();
    private BillDAO billDAO = new BillDAO();

    public Bill calculateBill(int reservationId) {
        Reservation reservation = reservationDAO.getReservationById(reservationId);
        if (reservation == null) return null;

        Room room = roomDAO.getRoomById(reservation.getRoomId());
        if (room == null) return null;

        long nights = ChronoUnit.DAYS.between(reservation.getCheckIn(), reservation.getCheckOut());
        if (nights <= 0) nights = 1;

        double totalAmount = nights * room.getPricePerNight();

        Bill bill = new Bill();
        bill.setReservationId(reservationId);
        bill.setNumberOfNights((int) nights);
        bill.setPricePerNight(room.getPricePerNight());
        bill.setTotalAmount(totalAmount);

        bill.setGuestName(reservation.getGuestName());
        bill.setRoomType(room.getRoomType());
        bill.setCheckIn(reservation.getCheckIn());
        bill.setCheckOut(reservation.getCheckOut());

        billDAO.addBill(bill);

        return bill;
    }
}