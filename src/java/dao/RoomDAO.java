/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Nilucshiha
 */
import model.Room;
import util.DatabaseConnection;
import java.sql.*;

public class RoomDAO {

    public Room getAvailableRoom(String roomType) {
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            String sql = "SELECT * FROM rooms WHERE room_type=? AND status='AVAILABLE' LIMIT 1";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, roomType);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return new Room(rs.getInt("room_id"), rs.getString("room_number"),
                        rs.getString("room_type"), rs.getDouble("price_per_night"),
                        rs.getString("status"));
            }
        } catch(SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean updateRoomStatus(int roomId, String status) {
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            String sql = "UPDATE rooms SET status=? WHERE room_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, roomId);
            return ps.executeUpdate() > 0;
        } catch(SQLException e) { e.printStackTrace(); }
        return false;
    }
}