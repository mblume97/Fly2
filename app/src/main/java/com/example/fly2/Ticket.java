package com.example.fly2;

public class Ticket {

    private Flight flight;
    private double price;

    public Ticket(Flight flight, double price) {
        this.flight = flight;
        this.price = price;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
