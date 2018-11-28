package com.example.fly2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LoadingActivity extends AppCompatActivity {
    private RequestQueue mQueue;
    private ArrayList<TicketPair> ticketPairs;
    private HashMap<String, TicketPair> arrivalAirportToPair;
    private int numberProcessed;
    Airport airport1;
    Airport airport2;
    Date departureDate;
    Date returnDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        mQueue = Volley.newRequestQueue(this);
        Bundle extras = getIntent().getExtras();
        airport1 =  extras.getParcelable("airport1");
        airport2 = extras.getParcelable("airport2");
        departureDate = (Date) extras.get("departureDate");
        returnDate = (Date) extras.get("returnDate");
        findOptimal(airport1, airport2, departureDate, returnDate);
    }

    public TicketPair findMin(TicketPair min, TicketPair current) {
        if (min.getTotalPrice() < current.getTotalPrice()) {
            return min;
        }
        else
            return current;
    }
    // Makes the ticket pairs for the cheapest flights from the two departure airports to the 60 possible destinations
    // Makes the ticket pairs for the cheapest flights from the two departure airports to the 60 possible destinations
    // At the end of this call, ticketPairs will be populated with 60 ticket pairs
    private void makeTicketPairs(Airport firstDepartureAirport, Airport secondDepartureAirport, Date departureDate, Date returnDate) {
        ticketPairs = new ArrayList<>();
        numberProcessed = 0;
        arrivalAirportToPair = new HashMap<>();

        // loading the local json
        String codeToCity = Helpers.loadJSONFromAsset(this, "airport_code_to_city.json"); // Pass context if possible?? place inside activity?
        String codeToAirportName = Helpers.loadJSONFromAsset(this, "airport_code_to_name.json");
        try {
            JSONObject codeToCityJSON = new JSONObject(codeToCity);
            JSONObject codeToAirportNameJSON = new JSONObject(codeToAirportName);
//            firstDepartureAirport.setAirportName(codeToAirportNameJSON.getString(firstDepartureAirport.getAirportCode()));
//            secondDepartureAirport.setAirportName(codeToAirportNameJSON.getString(secondDepartureAirport.getAirportCode()));
            // String temp = obj.getString("ATL"); // How to receive one of the values
            Iterator<String> iter = codeToCityJSON.keys();
            while (iter.hasNext()) {
                String airportCode = iter.next(); // Looping through all of the airport codes
                // call the api
                Airport arrivalAirport = new Airport(airportCode, codeToAirportNameJSON.getString(airportCode));
                City destinationCity = new City(codeToCityJSON.getString(airportCode));
                Flight firstFlight = new Flight(firstDepartureAirport, arrivalAirport, departureDate, returnDate, destinationCity);
                getMinFlightPrice(firstFlight);
                Flight secondFlight = new Flight(secondDepartureAirport, arrivalAirport, departureDate,returnDate, destinationCity);
                getMinFlightPrice(secondFlight);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // Makes the ticketPairs
    // Returns the minimum/cheapest pair that was created
    // TODO: decide if we want to return one, or like top 3?
    public void findOptimal(Airport firstDepartureAirport, Airport secondDepartureAirport, Date departureDate, Date returnDate) {
        makeTicketPairs(firstDepartureAirport, secondDepartureAirport, departureDate, returnDate); // Makes the SkyScanner API calls
    }

    // Using volley with the SkyScanner Rapid API to get flight prices
    private void getMinFlightPrice(final Flight flight)  {
        // 2018-12-01 <-- this is how the date needs to be formatted
        String url = createURL(flight);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, (String)null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // TODO: need to finish the processResponse callback
                        try {
                            double minPrice = Double.POSITIVE_INFINITY; // No quote = highest price
                            String airlineName = "N/A";
                            // Check to see if there are no quotes
                            // TODO: Extract the airline as well.
                            if (response.getJSONArray("Quotes").length() != 0) {
                                JSONObject quotes = response.getJSONArray("Quotes").getJSONObject(0);
                                minPrice = quotes.getDouble("MinPrice");
                                int airline = quotes.getJSONObject("OutboundLeg").getJSONArray("CarrierIds").getInt(0);
                                JSONArray carriers = response.getJSONArray("Carriers");
                                for (int i = 0; i < carriers.length(); i++) {
                                    JSONObject carrier = carriers.getJSONObject(i);
                                    if (carrier.getInt("CarrierId") == airline) {
                                        airlineName = carrier.getString("Name");
                                        break;
                                    }
                                }
                            }
                            System.out.println(minPrice);
                            // callback function
                            processResponse(flight, minPrice, airlineName);
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
//                headers.put("X-Mashape-Key", "91rhzu8E36mshrcePFFwhHA5sgHmp1q8X6yjsnxJqCaMtEwv3Y");
//                headers.put("X-Mashape-Host", "skyscanner-skyscanner-flight-search-v1.p.rapidapi.com");
                headers.put("X-RapidAPI-Key", "91rhzu8E36mshrcePFFwhHA5sgHmp1q8X6yjsnxJqCaMtEwv3Y");
                return headers;
            }
        };

        mQueue.add(jsonObjectRequest);
    }

    // Callback function, once the response is made
    private void processResponse(Flight flight, double minPrice, String airline) {
        // This is where we create the ticketPairs
        Ticket ticket = new Ticket(flight, minPrice);
        String key = flight.getArrivalAirport().getAirportCode();
        numberProcessed++;

        // Already have a ticketPair with this arrival airport, this is the second ticket
        if (arrivalAirportToPair.containsKey(key)) {
            TicketPair pair = arrivalAirportToPair.get(key);
            pair.addSecondTicket(ticket);
        } else {
            TicketPair pair = new TicketPair(ticket);
            arrivalAirportToPair.put(key, pair);
            ticketPairs.add(pair);
        }

        // Processed 20 tickets already, now can find the min one
        if (numberProcessed == 4) {
            TicketPair min = ticketPairs.get(0);
            for (int i = 1; i < ticketPairs.size(); i++) {
                min = findMin(min, ticketPairs.get(i)); // Calls the concrete class’ findMin()
            }
            // Start a new activity with the min.
            Intent intent = new Intent(this, CityResultsPage.class);
            intent.putExtra("price", min.getTotalPrice());
            intent.putExtra("city", min.getSecondTicket().getFlight().getDestinationCity().getCityName());
            intent.putExtra("code", min.getSecondTicket().getFlight().getArrivalAirport().getAirportCode());
            intent.putExtra("name", min.getSecondTicket().getFlight().getArrivalAirport().getAirportName());
            intent.putExtra("airline", airline);
            startActivity(intent);
        }
    }

    public static String createURL(Flight flight) {
        Airport departureAirport = flight.getDepartureAirport();
        Airport arrivalAirport = flight.getArrivalAirport();
        Date departureDate = flight.getDepartureDate();
        String formattedDepartureDate = new SimpleDateFormat("yyyy-MM-dd").format(departureDate);
        Date returnDate = flight.getReturnDate();
        String formattedReturnDate = new SimpleDateFormat("yyyy-MM-dd").format(returnDate);

        String url = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/browsequotes" +
                "/v1.0/US/USD/en-US/" + departureAirport.getAirportCode() + "-sky/" +
                arrivalAirport.getAirportCode() + "-sky/" + formattedDepartureDate + "/" + formattedReturnDate;

        return url;
    }
}
