package com.enigma.entity;

public class Chair {
    private Integer id;
    private String seatNumber;
    private Integer theaterId;

    public Chair(Integer id, String seatNumber, Integer theaterId) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.theaterId = theaterId;
    }

    public Chair() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Integer getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(Integer theaterId) {
        this.theaterId = theaterId;
    }

    @Override
    public String toString() {
        return String.format(" %d \t%s", id, seatNumber);
    }
}
