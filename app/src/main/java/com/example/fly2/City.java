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
