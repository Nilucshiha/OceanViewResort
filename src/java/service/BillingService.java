/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author Nilucshiha
 */
import dao.BillDAO;
import dao.PaymentDAO;
import model.Bill;
import model.Payment;

public class BillingService {
    private BillDAO billDAO = new BillDAO();
    private PaymentDAO paymentDAO = new PaymentDAO();

    // Get bill by reservation ID
    public Bill getBill(int reservationId) {
        return billDAO.getBill(reservationId);
    }

    // Make payment
    public String makePayment(int billId, String paymentMethod, double amount) {
        try {
            Payment payment = new Payment(billId, paymentMethod, amount);
            int id = paymentDAO.addPayment(payment);
            if(id > 0) return "Payment successful!";
        } catch(Exception e) {
            e.printStackTrace();
        }
        return "Payment failed!";
    }
}
