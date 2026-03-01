/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author Nilucshiha
 */
import dao.ReservationDAO;
import dao.RoomDAO;
import dao.BillDAO;
import model.Room;
import java.sql.*;
import util.DatabaseConnection;

public class ReportService {

    // Show room occupancy
    public String getRoomOccupancyReport() {
        StringBuilder sb = new StringBuilder();
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            String sql = "SELECT room_number, room_type, status FROM rooms";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            sb.append("<h3>Room Occupancy Report</h3>");
            sb.append("<table border='1'><tr><th>Room Number</th><th>Room Type</th><th>Status</th></tr>");
            while(rs.next()) {
                sb.append("<tr><td>").append(rs.getString("room_number")).append("</td>")
                  .append("<td>").append(rs.getString("room_type")).append("</td>")
                  .append("<td>").append(rs.getString("status")).append("</td></tr>");
            }
            sb.append("</table>");
        } catch(SQLException e) { e.printStackTrace(); }
        return sb.toString();
    }

    // Show total revenue
    public String getTotalRevenueReport() {
        double total = 0;
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            String sql = "SELECT SUM(total_amount) as total_revenue FROM bills";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) total = rs.getDouble("total_revenue");
        } catch(SQLException e) { e.printStackTrace(); }
        return "<h3>Total Revenue: $" + total + "</h3>";
    }
}
