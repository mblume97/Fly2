package com.example.fly2;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Flight implements Parcelable {

    private Airport departureAirport;
    private Airport arrivalAirport;
    private Date departureDate;
    private Date returnDate;
    private City destinationCity;

    public Flight(Airport departureAirport, Airport arrivalAirport, Date departureDate, Date returnDate, City destinationCity) {
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.destinationCity = destinationCity;
    }

    protected Flight(Parcel in) {
        departureAirport = in.readParcelable(Airport.class.getClassLoader());
        arrivalAirport = in.readParcelable(Airport.class.getClassLoader());
        destinationCity = in.readParcelable(City.class.getClassLoader());
    }

    public static final Creator<Flight> CREATOR = new Creator<Flight>() {
        @Override
        public Flight createFromParcel(Parcel in) {
            return new Flight(in);
        }

        @Override
        public Flight[] newArray(int size) {
            return new Flight[size];
        }
    };

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDateDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public City getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(City destinationCity) {
        this.destinationCity = destinationCity;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(departureAirport, flags);
        dest.writeParcelable(arrivalAirport, flags);
        dest.writeLong(departureDate.getTime()); // REMEMBER TO CONVERT IT BACK TO A DATE OBJECT
        dest.writeLong(returnDate.getTime());
        dest.writeParcelable(destinationCity, flags);
    }

}
