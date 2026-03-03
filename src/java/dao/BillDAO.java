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

    // Save bill
    public int addBill(Bill bill) {
        String sql = "INSERT INTO bills (reservation_id, total_nights, total_amount) VALUES (?, ?, ?)";
        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, bill.getReservationId());
            ps.setInt(2, bill.getTotalNights());
            ps.setDouble(3, bill.getTotalAmount());

            int rows = ps.executeUpdate();
            if (rows == 0) return 0;

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Get bill by reservationId
    public Bill getBill(int reservationId) {
        String sql = "SELECT * FROM bills WHERE reservation_id=?";
        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, reservationId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Bill(
                        rs.getInt("reservation_id"),
                        rs.getInt("total_nights"),
                        rs.getDouble("total_amount")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}