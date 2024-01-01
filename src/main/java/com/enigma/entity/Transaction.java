package com.enigma.entity;

public class Transaction {
    private Integer id;
    private Integer seat_id;
    private Integer customer_id;

    public Transaction(Integer id, Integer seat_id, Integer customer_id) {
        this.id = id;
        this.seat_id = seat_id;
        this.customer_id = customer_id;
    }

    public Transaction() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSeat_id() {
        return seat_id;
    }

    public void setSeat_id(Integer seat_id) {
        this.seat_id = seat_id;
    }

    public Integer getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Integer customer_id) {
        this.customer_id = customer_id;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", seat_id=" + seat_id +
                ", customer_id=" + customer_id +
                '}';
    }
}
