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
import java.util.List;
import java.util.UUID;

public class ReservationService {

    private GuestDAO guestDAO = new GuestDAO();
    private RoomDAO roomDAO = new RoomDAO();
    private ReservationDAO reservationDAO = new ReservationDAO();
    private BillDAO billDAO = new BillDAO();

    // ============================
    // CREATE RESERVATION
    // ============================
    public boolean createReservation(
            String guestName,
            String address,
            String contactNumber,
            String roomType,
            LocalDate checkIn,
            LocalDate checkOut
    ) {
        try {
            // 1️⃣ Validate dates
            if (checkOut.isBefore(checkIn) || checkOut.equals(checkIn)) {
                return false;
            }

            // 2️⃣ Save guest
            Guest guest = new Guest(guestName, address, contactNumber);
            int guestId = guestDAO.addGuest(guest);

            if (guestId <= 0) {
                return false;
            }

            // 3️⃣ Get available room
            Room room = roomDAO.getAvailableRoom(roomType);
            if (room == null) {
                return false;
            }

            // 4️⃣ Generate reservation number
            String reservationNumber =
                    "RES-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

            // 5️⃣ Save reservation
            Reservation reservation = new Reservation(
                    reservationNumber,
                    guestId,
                    room.getRoomId(),
                    checkIn,
                    checkOut,
                    "CONFIRMED"
            );

            int reservationId = reservationDAO.addReservation(reservation);
            if (reservationId <= 0) {
                return false;
            }

            // 6️⃣ Update room status
            roomDAO.updateRoomStatus(room.getRoomId(), "OCCUPIED");

            // 7️⃣ Create bill
            int nights = (int) ChronoUnit.DAYS.between(checkIn, checkOut);
            double amount = nights * room.getPricePerNight();

            Bill bill = new Bill(reservationId, nights, amount);
            billDAO.addBill(bill);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ============================
    // FILTER BY RESERVATION NUMBER
    // ============================
    public Reservation getReservation(String reservationNumber) {
        return reservationDAO.getReservationByNumber(reservationNumber);
    }

    // ============================
    // GET ALL RESERVATIONS
    // ============================
    public List<Reservation> getAllReservations() {
        return reservationDAO.getAllReservations();
    }
    
    public int getTotalReservations() {
    return reservationDAO.getReservationCount();
}
}