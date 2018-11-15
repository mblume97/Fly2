package com.example.fly2;

public class TicketPair {

    private Airport firstDepartureAirport;
    private Airport secondDepartureAirport;
    private City destination;
    private Ticket firstTicket;
    private Ticket secondTicket;
    private Price totalPrice;

    public Airport getFirstDepartureAirport() {
        return firstDepartureAirport;
    }

    public void setFirstDepartureAirport(Airport firstDepartureAirport) {
        this.firstDepartureAirport = firstDepartureAirport;
    }

    public Airport getSecondDepartureAirport() {
        return secondDepartureAirport;
    }

    public void setSecondDepartureAirport(Airport secondDepartureAirport) {
        this.secondDepartureAirport = secondDepartureAirport;
    }

    public City getDestination() {
        return destination;
    }

    public void setDestination(City destination) {
        this.destination = destination;
    }

    public Ticket getFirstTicket() {
        return firstTicket;
    }

    public void setFirstTicket(Ticket firstTicket) {
        this.firstTicket = firstTicket;
    }

    public Ticket getSecondTicket() {
        return secondTicket;
    }

    public void setSecondTicket(Ticket secondTicket) {
        this.secondTicket = secondTicket;
    }

    public Price getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Price totalPrice) {
        this.totalPrice = totalPrice;
    }

}
