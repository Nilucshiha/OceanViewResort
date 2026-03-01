/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Nilucshiha
 */
import model.User;
import util.DatabaseConnection;
import java.sql.*;

public class UserDAO {

    // ======================
    // LOGIN / AUTHENTICATION
    // ======================
    public User authenticate(String username, String password) {
        User user = null;
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            String sql = "SELECT user_id, username, password, role FROM users WHERE username=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String dbPassword = rs.getString("password");

                // Plain text comparison (OK for academic project)
                if (dbPassword.equals(password)) {
                    user = new User(
                            rs.getInt("user_id"),
                            rs.getString("username"),
                            rs.getString("role")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // ======================
    // CHECK IF USER EXISTS
    // ======================
    public boolean userExists(String username) {
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            String sql = "SELECT user_id FROM users WHERE username=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // true if found
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ======================
    // REGISTER NEW USER
    // ======================
    public boolean registerUser(String username, String password, String role) {
        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();
            String sql = "INSERT INTO users(username, password, role) VALUES (?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password); // can hash later
            ps.setString(3, role);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}