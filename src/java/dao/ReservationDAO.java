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

public class ReservationDAO {

    public int addReservation(Reservation res) {
        int id = -1;
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            String sql = "INSERT INTO reservations (reservation_number, guest_id, room_id, check_in, check_out, status) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, res.getReservationNumber());
            ps.setInt(2, res.getGuestId());
            ps.setInt(3, res.getRoomId());
            ps.setDate(4, Date.valueOf(res.getCheckIn()));
            ps.setDate(5, Date.valueOf(res.getCheckOut()));
            ps.setString(6, res.getStatus());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) {
                id = rs.getInt(1);
                res.setReservationId(id);
            }
        } catch(SQLException e) { e.printStackTrace(); }
        return id;
    }

    public Reservation getReservationByNumber(String reservationNumber) {

    Reservation reservation = null;

    String sql = """
        SELECT 
            r.reservation_number,
            r.check_in,
            r.check_out,
            r.status,
            g.guest_name,
            g.address,
            g.contact_number,
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
            reservation = new Reservation();

            reservation.setReservationNumber(rs.getString("reservation_number"));
            reservation.setGuestName(rs.getString("guest_name"));
            reservation.setAddress(rs.getString("address"));
            reservation.setContactNumber(rs.getString("contact_number"));
            reservation.setRoomType(rs.getString("room_type"));
            reservation.setCheckIn(rs.getDate("check_in").toLocalDate());
            reservation.setCheckOut(rs.getDate("check_out").toLocalDate());
            reservation.setStatus(rs.getString("status"));
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return reservation;
}
    
    public List<Reservation> getAllReservations() {

    List<Reservation> list = new ArrayList<>();

    String sql = """
        SELECT 
            r.reservation_number,
            r.check_in,
            r.check_out,
            r.status,
            g.guest_name,
            g.address,
            g.contact_number,
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
