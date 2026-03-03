/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Nilucshiha
 */
public class Bill {
    private int billId;
    private int reservationId;
    private int totalNights;
    private double totalAmount;

    public Bill(int reservationId, int totalNights, double totalAmount) {
        this.reservationId = reservationId;
        this.totalNights = totalNights;
        this.totalAmount = totalAmount;
    }

    public int getBillId() { return billId; }
    public void setBillId(int billId) { this.billId = billId; }
    public int getReservationId() { return reservationId; }
    public int getTotalNights() { return totalNights; }
    public double getTotalAmount() { return totalAmount; }
}