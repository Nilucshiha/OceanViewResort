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

    public boolean createReservation(
            String guestName,
            String address,
            String contactNumber,
            String roomType,
            LocalDate checkIn,
            LocalDate checkOut
    ) {
        try {
           
            if (checkOut.isBefore(checkIn) || checkOut.equals(checkIn)) {
                System.out.println("Invalid check-in/check-out dates");
                return false;
            }

            Guest guest = new Guest(guestName, address, contactNumber);
            int guestId = guestDAO.addGuest(guest); 
            if (guestId <= 0) return false;

            Room room = roomDAO.getAvailableRoom(roomType);
            if (room == null) {
                System.out.println("No available room for type " + roomType);
                return false;
            }

            String reservationNumber = "RES-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

            Reservation reservation = new Reservation(
                    reservationNumber,
                    guestId,
                    room.getRoomId(),
                    checkIn,
                    checkOut,
                    "CONFIRMED"
            );
            
            reservation.setPricePerNight(room.getPricePerNight());

            int reservationId = reservationDAO.addReservation(reservation);
            if (reservationId <= 0) return false;

            roomDAO.updateRoomStatus(room.getRoomId(), "OCCUPIED");

            long nights = reservation.getNumberOfNights(); 
            double totalAmount = nights * room.getPricePerNight();


            Bill bill = new Bill();

        bill.setReservationId(reservation.getReservationId());
        bill.setNumberOfNights((int) nights);
        bill.setTotalAmount(totalAmount);

        billDAO.saveBill(bill);

            System.out.println("Reservation and bill created successfully.");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Reservation getReservation(String reservationNumber) {
        return reservationDAO.getReservationByNumber(reservationNumber);
    }

    public Reservation getReservationById(int reservationId) {
        return reservationDAO.getReservationById(reservationId);
    }

    public List<Reservation> getAllReservations() {
        return reservationDAO.getAllReservations();
    }

    public int getTotalReservations() {
        return reservationDAO.getReservationCount();
    }

    public List<Reservation> getReservationsForBilling() {
        return getAllReservations();
    }
    
    public boolean updateReservation(Reservation reservation) {
        return reservationDAO.updateReservation(reservation);
    }
    
 }