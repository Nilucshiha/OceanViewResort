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

    // ============================
    // ADD RESERVATION (MISSING)
    // ============================
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

    // ============================
    // MAP RESULT SET
    // ============================
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

        return reservation;
    }

    // ============================
    // GET BY NUMBER
    // ============================
    public Reservation getReservationByNumber(String reservationNumber) {
        String sql = """
            SELECT r.reservation_number, r.check_in, r.check_out, r.status,
                   g.guest_name, g.address, g.contact_number,
                   rm.room_type
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

    // ============================
    // GET ALL
    // ============================
    public List<Reservation> getAllReservations() {
        List<Reservation> list = new ArrayList<>();

        String sql = """
            SELECT r.reservation_number, r.check_in, r.check_out, r.status,
                   g.guest_name, g.address, g.contact_number,
                   rm.room_type
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
    
    public void deleteByReservationNumber(String reservationNumber) 
    {
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

public Reservation getReservationById(int reservationId) {
    String sql = """
        SELECT r.reservation_id, r.reservation_number, r.check_in, r.check_out, r.status,
               g.guest_name, g.address, g.contact_number,
               rm.room_type
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
            return mapResultSetToReservation(rs);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return null;
}

}