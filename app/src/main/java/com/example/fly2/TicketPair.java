package com.example.fly2;

public class TicketPair {

    private Ticket firstTicket;
    private Ticket secondTicket;
    private Price totalPrice;


    public TicketPair(Ticket firstTicket, Ticket secondTicket, Price totalPrice) {
        this.firstTicket = firstTicket;
        this.secondTicket = secondTicket;
        this.totalPrice = totalPrice;
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
