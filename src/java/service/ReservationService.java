/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author Nilucshiha
 */
import dao.*;
import model.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class ReservationService {

    private GuestDAO guestDAO = new GuestDAO();
    private RoomDAO roomDAO = new RoomDAO();
    private ReservationDAO reservationDAO = new ReservationDAO();
    private BillDAO billDAO = new BillDAO();

    // ==========================
    // CREATE RESERVATION
    // ==========================
    public boolean createReservation(String guestName, String address, String contact,
                                     String roomType, LocalDate checkIn, LocalDate checkOut) {

        try {
            // ---------- VALIDATION ----------
            if (checkOut.isBefore(checkIn)) {
                return false;
            }

            long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
            if (nights <= 0) {
                nights = 1; // minimum 1 night
            }

            // ---------- ADD GUEST ----------
            Guest guest = new Guest(guestName, address, contact);
            int guestId = guestDAO.addGuest(guest);

            // ---------- GET ROOM ----------
            Room room = roomDAO.getAvailableRoom(roomType);
            if (room == null) {
                return false;
            }

            // ---------- UPDATE ROOM ----------
            roomDAO.updateRoomStatus(room.getRoomId(), "OCCUPIED");

            // ---------- RESERVATION ----------
            String reservationNumber = "RES-" +
                    UUID.randomUUID().toString().substring(0, 8).toUpperCase();

            Reservation reservation = new Reservation(
                    reservationNumber,
                    guestId,
                    room.getRoomId(),
                    checkIn,
                    checkOut,
                    "CONFIRMED"
            );

            int reservationId = reservationDAO.addReservation(reservation);

            // ---------- BILL ----------
            double amount = nights * room.getPricePerNight();
            Bill bill = new Bill(reservationId, (int) nights, amount);
            billDAO.addBill(bill);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ==========================
    // GET RESERVATION
    // ==========================
    public Reservation getReservation(String reservationNumber) {
        return reservationDAO.getReservationByNumber(reservationNumber);
    }
}