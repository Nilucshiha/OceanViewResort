/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Nilucshiha
 */
import model.Payment;
import util.DatabaseConnection;
import java.sql.*;

public class PaymentDAO {

    public int addPayment(Payment p) {
        int id = -1;
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            String sql = "INSERT INTO payments (bill_id, payment_method, amount) VALUES (?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, p.getBillId());
            ps.setString(2, p.getPaymentMethod());
            ps.setDouble(3, p.getAmount());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) {
                id = rs.getInt(1);
                p.setPaymentId(id);
            }
        } catch(SQLException e) { e.printStackTrace(); }
        return id;
    }

    public Payment getPayment(int billId) {
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            String sql = "SELECT * FROM payments WHERE bill_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, billId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                Payment p = new Payment(rs.getInt("bill_id"), rs.getString("payment_method"), rs.getDouble("amount"));
                p.setPaymentId(rs.getInt("payment_id"));
                return p;
            }
        } catch(SQLException e) { e.printStackTrace(); }
        return null;
    }
}
