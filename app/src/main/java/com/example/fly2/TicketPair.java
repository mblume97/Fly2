package com.example.fly2;

import android.os.Parcel;
import android.os.Parcelable;

public class TicketPair implements Parcelable {

    private Ticket firstTicket;
    private Ticket secondTicket;
    private Double totalPrice;

    public TicketPair(Ticket firstTicket, Ticket secondTicket, Double totalPrice) {
        this.firstTicket = firstTicket;
        this.secondTicket = secondTicket;
        this.totalPrice = totalPrice;
    }

    public  TicketPair(Ticket firstTicket) {
        this.firstTicket = firstTicket;
        totalPrice = firstTicket.getPrice();
    }

    protected TicketPair(Parcel in) {
        firstTicket = in.readParcelable(Ticket.class.getClassLoader());
        secondTicket = in.readParcelable(Ticket.class.getClassLoader());
        totalPrice = in.readDouble();
    }

    public static final Creator<TicketPair> CREATOR = new Creator<TicketPair>() {
        @Override
        public TicketPair createFromParcel(Parcel in) {
            return new TicketPair(in);
        }

        @Override
        public TicketPair[] newArray(int size) {
            return new TicketPair[size];
        }
    };

    public void addSecondTicket(Ticket secondTicket) {
        this.secondTicket = secondTicket;
        totalPrice += secondTicket.getPrice();
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

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(firstTicket, flags);
        dest.writeParcelable(secondTicket, flags);
        dest.writeDouble(totalPrice);
    }
}
