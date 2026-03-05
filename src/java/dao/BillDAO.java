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

    public boolean isBillExists(int reservationId) {

        String sql = "SELECT billId FROM bill WHERE reservationId = ?";
        boolean exists = false;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, reservationId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                exists = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exists;
    }
    
    public boolean addBill(Bill bill) {
    String sql = "INSERT INTO bills (reservation_id, nights, price_per_night, total_amount) VALUES (?, ?, ?, ?)";
    try (Connection con = DatabaseConnection.getInstance().getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, bill.getReservationId());
        ps.setInt(2, bill.getNumberOfNights());
        ps.setDouble(3, bill.getPricePerNight());
        ps.setDouble(4, bill.getTotalAmount());
        return ps.executeUpdate() > 0;
        } 
    catch (Exception e) {
        e.printStackTrace();
        return false;
        }
    }
    
    public void saveBill(Bill bill){
    try{
        Connection con = DatabaseConnection.getInstance().getConnection();
        String sql = "INSERT INTO bill(reservation_id,total_nights,total_amount) VALUES (?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, bill.getReservationId());
        ps.setInt(2, bill.getNumberOfNights());
        ps.setDouble(3, bill.getTotalAmount());
        ps.executeUpdate();
        con.close();
        }
    catch(Exception e){
        e.printStackTrace();
        }
    }
    
    public boolean billExists(int reservationId) {

        boolean exists = false;

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();

            String sql = "SELECT bill_id FROM bill WHERE reservation_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, reservationId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                exists = true;
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return exists;
  }  
}