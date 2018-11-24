package com.example.fly2;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public abstract class AbstractAlgorithm {

    // This mQueue needs to be placed inside an actual activity? not sure
    private Context context;
    private RequestQueue mQueue;
    private ArrayList<TicketPair> ticketPairs;

    public AbstractAlgorithm(Context context) {
        this.context = context;
        mQueue = Volley.newRequestQueue(context); // Needs to be placed inside actual activity.. or pass context?
    }


    public abstract TicketPair findMin(TicketPair min, TicketPair current);


    private void makeTicketPairs(Airport departureAirport, Date departureDate) {
        ticketPairs = new ArrayList<>();

        // loading the local json
        String codeToCity = Helpers.loadJSONFromAsset(context, "airport_code_to_city.json"); // Pass context if possible?? place inside activity?
        String codeToAirport = Helpers.loadJSONFromAsset(context, "airport_code_to_name.json");
        try {
            JSONObject codeToCityJSON = new JSONObject(codeToCity);
            JSONObject codeToAirportJSON = new JSONObject(codeToAirport);
           // String temp = obj.getString("ATL"); // How to receive one of the keys
            Iterator<String> iter = codeToCityJSON.keys();
            while (iter.hasNext()) {
                String airportCode = iter.next(); // Looping through all of the airport codes
                // call the api
                Airport arrivalAirport = new Airport(airportCode, codeToAirportJSON.getString(airportCode));
                City destinationCity = new City(codeToCityJSON.getString(airportCode));
                Flight flight = new Flight(departureAirport, arrivalAirport, departureDate, destinationCity);
                getMinFlightPrice(flight);
                try {
                    String cityName = codeToCityJSON.getString(airportCode); // Getting the city name
                } catch (JSONException e) {
                    // Something went wrong!
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public TicketPair findOptimal(Airport departureAirport, Date date) {
        makeTicketPairs(departureAirport, date); // Makes the SkyScnnaer Rapid API calls
        TicketPair min = ticketPairs.get(0);
        for (int i = 1; i < ticketPairs.size(); i++) {
            min = findMin(min, ticketPairs.get(i)); // Calls the concrete class’ findMin()
        }
        return min;
    }


    // Using volley with the SkyScanner Rapid API to get flight prices
    private void getMinFlightPrice(final Flight flight)  {
        // 2018-12-01 <-- this is how the date needs to be formatted
        // TODO: format the departure date
        Airport departureAirport = flight.getDepartureAirport();
        Airport arrivalAirport = flight.getArrivalAirport();
        Date departureDate = flight.getDepartureDate();

        String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(departureDate);
        String url = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/browsequotes/v1.0/US/USD/en-US/"
                + departureAirport.getAirportCode() + "-sky/" + arrivalAirport.getAirportCode() +
                "-sky/" + formattedDate + "/%20";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, (String)null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // TODO: need to finish the processResponse callback
                        try {
                            double minPrice = Double.POSITIVE_INFINITY;
                            // Check to see if there are no quotes
                            if (response.getJSONArray("Quotes").length() != 0) {
                                JSONObject quotes = response.getJSONArray("Quotes").getJSONObject(0);
                                minPrice = quotes.getDouble("MinPrice");
                            }
                            System.out.println(minPrice);
                            // callback function
                            processResponse(flight, minPrice);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                // Basic Authentication
                headers.put("X-Mashape-Key", "91rhzu8E36mshrcePFFwhHA5sgHmp1q8X6yjsnxJqCaMtEwv3Y");
                headers.put("X-Mashape-Host", "skyscanner-skyscanner-flight-search-v1.p.rapidapi.com");
                return headers;
            }
        };

        mQueue.add(jsonObjectRequest);
    }

    // Callback function, once the response is made
    private void processResponse(Flight flight, double minPrice) {
        // This is where we create the ticketPairs??
        // TODO: this should have firstticket, secondticket
        // TODO: figure out how to process that

        Airport departureAirport = flight.getDepartureAirport();
        City destinationCity = flight.getDestinationCity();

        Price firstTicketPrice = new Price(minPrice);
        Ticket ticket = new Ticket(flight, firstTicketPrice);

        double combinedCost = ticket.getPrice().getPrice() + ticket.getPrice().getPrice();
        Price combinedPrice = new Price(combinedCost);


        TicketPair pair = new TicketPair(ticket, ticket, combinedPrice);
        ticketPairs.add(pair);
    }

}
