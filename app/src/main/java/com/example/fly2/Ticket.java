package com.example.fly2;

import java.util.Date;

public class Ticket {

    //    private Airport departureAirport;
//    private Airport arrivalAirport;
//    private Date departureDate;
    private Flight flight;
//    private Date arrivalDate;
//    private Date expirationDate;
//    private String flightNumber;
    private Price price;

    public Ticket(Flight flight, Price price) {
        this.flight = flight;
        this.price = price;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

//    public Airport getDepartureAirport() {
//        return departureAirport;
//    }
//
//    public void setDepartureAirport(Airport departureAirport) {
//        this.departureAirport = departureAirport;
//    }
//
//    public Airport getArrivalAirport() {
//        return arrivalAirport;
//    }
//
//    public void setArrivalAirport(Airport arrivalAirport) {
//        this.arrivalAirport = arrivalAirport;
//    }
//
//    public Date getDepartureDate() {
//        return departureDate;
//    }
//
//    public void setDepartureDate(Date departureDate) {
//        this.departureDate = departureDate;
//    }

//    public Date getArrivalDate() {
//        return arrivalDate;
//    }
//
//    public void setArrivalDate(Date arrivalDate) {
//        this.arrivalDate = arrivalDate;
//    }
//
//    public Date getExpirationDate() {
//        return expirationDate;
//    }
//
//    public void setExpirationDate(Date expirationDate) {
//        this.expirationDate = expirationDate;
//    }
//
//    public String getFlightNumber() {
//        return flightNumber;
//    }
//
//    public void setFlightNumber(String flightNumber) {
//        this.flightNumber = flightNumber;
//    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

}
