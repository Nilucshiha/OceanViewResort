/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Nilucshiha
 */
import model.Reservation;
import util.DatabaseConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {

    private Reservation mapResultSetToReservation(ResultSet rs) throws SQLException {
        Reservation reservation = new Reservation();

        reservation.setReservationNumber(rs.getString("reservation_number"));
        reservation.setGuestName(rs.getString("guest_name"));
        reservation.setAddress(rs.getString("address"));
        reservation.setContactNumber(rs.getString("contact_number"));
        reservation.setRoomType(rs.getString("room_type"));
        reservation.setCheckIn(rs.getDate("check_in").toLocalDate());
        reservation.setCheckOut(rs.getDate("check_out").toLocalDate());
        reservation.setStatus(rs.getString("status"));
        reservation.setPricePerNight(rs.getDouble("price_per_night")); 

        return reservation;
    }

    public int addReservation(Reservation reservation) {
        String sql = """
            INSERT INTO reservations
            (reservation_number, guest_id, room_id, check_in, check_out, status)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, reservation.getReservationNumber());
            ps.setInt(2, reservation.getGuestId());
            ps.setInt(3, reservation.getRoomId());
            ps.setDate(4, Date.valueOf(reservation.getCheckIn()));
            ps.setDate(5, Date.valueOf(reservation.getCheckOut()));
            ps.setString(6, reservation.getStatus());

            int rows = ps.executeUpdate();
            if (rows == 0) return -1;

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public List<Reservation> getAllReservations() {
        List<Reservation> list = new ArrayList<>();

        String sql = """
            SELECT r.reservation_number, r.check_in, r.check_out, r.status,
                   g.guest_name, g.address, g.contact_number,
                   rm.room_type, rm.price_per_night
            FROM reservations r
            JOIN guests g ON r.guest_id = g.guest_id
            JOIN rooms rm ON r.room_id = rm.room_id
            ORDER BY r.check_in DESC
        """;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToReservation(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public Reservation getReservationByNumber(String reservationNumber) {
        String sql = """
            SELECT r.reservation_number, r.check_in, r.check_out, r.status,
                   g.guest_name, g.address, g.contact_number,
                   rm.room_type, rm.price_per_night
            FROM reservations r
            JOIN guests g ON r.guest_id = g.guest_id
            JOIN rooms rm ON r.room_id = rm.room_id
            WHERE r.reservation_number = ?
        """;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, reservationNumber);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSetToReservation(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Reservation getReservationById(int reservationId) {
        String sql = """
            SELECT r.reservation_id, r.reservation_number, r.check_in, r.check_out, r.status,
                   g.guest_name, g.address, g.contact_number,
                   rm.room_type, rm.price_per_night
            FROM reservations r
            JOIN guests g ON r.guest_id = g.guest_id
            JOIN rooms rm ON r.room_id = rm.room_id
            WHERE r.reservation_id = ?
        """;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, reservationId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Reservation r = new Reservation();
                r.setReservationId(rs.getInt("reservation_id"));
                r.setReservationNumber(rs.getString("reservation_number"));
                r.setGuestName(rs.getString("guest_name"));
                r.setAddress(rs.getString("address"));
                r.setContactNumber(rs.getString("contact_number"));
                r.setRoomType(rs.getString("room_type"));
                r.setCheckIn(rs.getDate("check_in").toLocalDate());
                r.setCheckOut(rs.getDate("check_out").toLocalDate());
                r.setStatus(rs.getString("status"));
                r.setPricePerNight(rs.getDouble("price_per_night")); 
                return r;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void deleteByReservationNumber(String reservationNumber) {
        String sql = "DELETE FROM reservations WHERE reservation_number = ?";

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, reservationNumber);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getReservationCount() {
        String sql = "SELECT COUNT(*) FROM reservations";

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
    
  public boolean updateReservation(Reservation reservation) {

    String guestSql = "UPDATE guest SET guestName=?, contactNumber=?, address=? WHERE guestId=?";
    String reservationSql = "UPDATE reservation SET roomType=?, status=?, checkIn=?, checkOut=? WHERE reservationNumber=?";

    try (Connection con = DatabaseConnection.getInstance().getConnection()) {

        PreparedStatement guestStmt = con.prepareStatement(guestSql);
        guestStmt.setString(1, reservation.getGuestName());
        guestStmt.setString(2, reservation.getContactNumber());
        guestStmt.setString(3, reservation.getAddress());
        guestStmt.setInt(4, reservation.getGuestId());

        guestStmt.executeUpdate();

        PreparedStatement resStmt = con.prepareStatement(reservationSql);
        resStmt.setString(1, reservation.getRoomType());
        resStmt.setString(2, reservation.getStatus());
        resStmt.setDate(3, java.sql.Date.valueOf(reservation.getCheckIn()));
        resStmt.setDate(4, java.sql.Date.valueOf(reservation.getCheckOut()));
        resStmt.setString(5, reservation.getReservationNumber());

        int rows = resStmt.executeUpdate();

        return rows > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
    
    public Reservation getReservationByNumber1(String reservationNumber) {
        Reservation reservation = null;
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 "SELECT * FROM reservations WHERE reservation_number = ?"
             )) {

            ps.setString(1, reservationNumber.trim());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                reservation = new Reservation();
                reservation.setReservationId(rs.getInt("reservation_id"));
                reservation.setReservationNumber(rs.getString("reservation_number"));
                reservation.setGuestId(rs.getInt("guest_id"));
                reservation.setRoomId(rs.getInt("room_id"));
                reservation.setCheckIn(rs.getDate("check_in").toLocalDate());
                reservation.setCheckOut(rs.getDate("check_out").toLocalDate());
                reservation.setStatus(rs.getString("status"));
                reservation.setPricePerNight(rs.getDouble("price_per_night")); 
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservation;
    }

    public List<Reservation> getAllReservations1() {

    List<Reservation> list = new ArrayList<>();

    String sql = "SELECT * FROM reservations";

    try (Connection conn = DatabaseConnection.getInstance().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Reservation r = new Reservation();

            r.setReservationNumber(rs.getString("reservation_number"));
            r.setGuestName(rs.getString("guest_name"));
            r.setAddress(rs.getString("address"));
            r.setContactNumber(rs.getString("contact_number"));
            r.setRoomType(rs.getString("room_type"));
            r.setCheckIn(rs.getDate("check_in").toLocalDate());
            r.setCheckOut(rs.getDate("check_out").toLocalDate());
            r.setStatus(rs.getString("status"));
            list.add(r);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}
    
}