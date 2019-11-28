package com.maidenhotels.Backend.tibco.schemas;

import java.util.ArrayList;
import java.util.List;

public class BillingReservation {

    private List<RoomsHotel> rooms = new ArrayList<>();
    private float totalCost;
    private String reservationNumber;

    public BillingReservation() {
    }

    public BillingReservation(List<RoomsHotel> rooms, float totalCost, String reservationNumber) {
        this.rooms = rooms;
        this.totalCost = totalCost;
        this.reservationNumber = reservationNumber;
    }

    public List<RoomsHotel> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomsHotel> rooms) {
        this.rooms = rooms;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
    }
}
