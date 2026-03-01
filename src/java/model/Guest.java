/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Nilucshiha
 */
public class Guest {
    private int guestId;
    private String guestName;
    private String address;
    private String contactNumber;

    public Guest(String guestName, String address, String contactNumber){
        this.guestName = guestName; this.address = address; this.contactNumber = contactNumber;
    }

    public int getGuestId() { return guestId; }
    public void setGuestId(int guestId) { this.guestId = guestId; }
    public String getGuestName() { return guestName; }
    public String getAddress() { return address; }
    public String getContactNumber() { return contactNumber; }
}
