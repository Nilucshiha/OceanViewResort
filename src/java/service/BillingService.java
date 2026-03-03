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

    // Create a bill
    public boolean createBill(Bill bill) {
        int id = billDAO.addBill(bill);
        return id > 0;
    }

    // Get bill by reservationId
    public Bill getBill(int reservationId) {
        return billDAO.getBill(reservationId);
    }
}