/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Nilucshiha
 */
import model.Guest;
import util.DatabaseConnection;
import java.sql.*;

public class GuestDAO {

    public int addGuest(Guest guest) {
        int id = -1;
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            String sql = "INSERT INTO guests (guest_name, address, contact_number) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, guest.getGuestName());
            ps.setString(2, guest.getAddress());
            ps.setString(3, guest.getContactNumber());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) {
                id = rs.getInt(1);
                guest.setGuestId(id);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public Guest getGuest(int guestId) {
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            String sql = "SELECT * FROM guests WHERE guest_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, guestId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return new Guest(rs.getString("guest_name"), rs.getString("address"), rs.getString("contact_number"));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}