/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Nilucshiha
 */
import java.time.LocalDateTime;

public class Payment {
    private int paymentId;
    private int billId;
    private String paymentMethod;
    private double amount;
    private LocalDateTime paymentDate;

    public Payment(int billId, String paymentMethod, double amount){
        this.billId = billId; this.paymentMethod = paymentMethod; this.amount = amount;
    }

    public int getPaymentId() { return paymentId; }
    public void setPaymentId(int paymentId) { this.paymentId = paymentId; }
    public int getBillId() { return billId; }
    public String getPaymentMethod() { return paymentMethod; }
    public double getAmount() { return amount; }
    public LocalDateTime getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }
}