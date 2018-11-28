package com.example.fly2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

// results that allows users to pick what city to go to
public class CityResultsPage extends AppCompatActivity {

    private double firstPrice;
    private double secondPrice;
    private String city;
    private String airportCode;
    private String airportName;
    private String firstAirline;
    private String secondAirline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_results_page);
        Bundle extras = getIntent().getExtras();

        firstPrice = extras.getDouble("firstPrice");
        secondPrice = extras.getDouble("secondPrice");
        city = extras.getString("city");
        airportCode = extras.getString("code");
        airportName = extras.getString("name");
        firstAirline = extras.getString("firstAirline");
        secondAirline = extras.getString("secondAirline");

        System.out.print("");
    }
}
