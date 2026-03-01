/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Nilucshiha
 */
public class Room {
    private int roomId;
    private String roomNumber;
    private String roomType;
    private double pricePerNight;
    private String status;

    public Room(int roomId, String roomNumber, String roomType, double pricePerNight, String status){
        this.roomId = roomId; this.roomNumber = roomNumber; this.roomType = roomType; this.pricePerNight = pricePerNight; this.status = status;
    }

    public int getRoomId() { return roomId; }
    public String getRoomNumber() { return roomNumber; }
    public String getRoomType() { return roomType; }
    public double getPricePerNight() { return pricePerNight; }
    public String getStatus() { return status; }
}
