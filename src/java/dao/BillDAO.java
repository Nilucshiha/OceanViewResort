/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Nilucshiha
 */
import model.Bill;
import util.DatabaseConnection;
import java.sql.*;

public class BillDAO {

    public int addBill(Bill bill) {
        int id = -1;
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            String sql = "INSERT INTO bills (reservation_id, total_nights, total_amount) VALUES (?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, bill.getReservationId());
            ps.setInt(2, bill.getTotalNights());
            ps.setDouble(3, bill.getTotalAmount());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) {
                id = rs.getInt(1);
                bill.setBillId(id);
            }
        } catch(SQLException e) { e.printStackTrace(); }
        return id;
    }

    public Bill getBill(int reservationId) {
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            String sql = "SELECT * FROM bills WHERE reservation_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, reservationId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                Bill b = new Bill(rs.getInt("reservation_id"), rs.getInt("total_nights"), rs.getDouble("total_amount"));
                b.setBillId(rs.getInt("bill_id"));
                return b;
            }
        } catch(SQLException e) { e.printStackTrace(); }
        return null;
    }
}