package com.example.fly2;

import android.os.Parcel;
import android.os.Parcelable;

public class Ticket implements Parcelable {

    private Flight flight;
    private double price;

    public Ticket(Flight flight, double price) {
        this.flight = flight;
        this.price = price;
    }

    protected Ticket(Parcel in) {
        flight = in.readParcelable(Flight.class.getClassLoader());
        price = in.readDouble();
    }

    public static final Creator<Ticket> CREATOR = new Creator<Ticket>() {
        @Override
        public Ticket createFromParcel(Parcel in) {
            return new Ticket(in);
        }

        @Override
        public Ticket[] newArray(int size) {
            return new Ticket[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(price);
        dest.writeParcelable(flight, flags);
    }
}
