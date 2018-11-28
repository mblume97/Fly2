package com.example.fly2;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class City implements Parcelable {

    private String cityName;
//    private String cityCode;

    public City(String cityName) {
        this.cityName = cityName;
    }

    protected City(Parcel in) {
        cityName = in.readString();
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cityName);
    }

//    public String getCityCode() {
//        return cityCode;
//    }
//
//    public void setCityCode(String cityCode) {
//        this.cityCode = cityCode;
//    }
//
}
