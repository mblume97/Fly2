package com.example.fly2;

import android.os.Parcel;
import android.os.Parcelable;

public class Airport implements Parcelable {

    private String airportCode;
    private String airportName;

    public Airport(String airportCode, String airportName) {
        this.airportCode = airportCode;
        this.airportName = airportName;
    }

    public Airport(String airportCode) {
        this.airportCode = airportCode;
        // TODO: Map to JSON code because MUST have the object
    }

    protected Airport(Parcel in) {
        airportCode = in.readString();
        airportName = in.readString();
    }

    public static final Creator<Airport> CREATOR = new Creator<Airport>() {
        @Override
        public Airport createFromParcel(Parcel in) {
            return new Airport(in);
        }

        @Override
        public Airport[] newArray(int size) {
            return new Airport[size];
        }
    };

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(airportCode);
        dest.writeString(airportName);
    }
}
