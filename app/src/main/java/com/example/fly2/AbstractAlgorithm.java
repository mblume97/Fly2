package com.example.fly2;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractAlgorithm {

    // This mQueue needs to be placed inside an actual activity
    private RequestQueue mQueue;


    public abstract TicketPair findMin(TicketPair min, TicketPair current);

    private ArrayList<TicketPair> makeTicketPairs() {
        mQueue = Volley.newRequestQueue(this); // Needs to be placed inside actual activity.. or pass context?
        return null;
    }


    public TicketPair findOptimal() {
        ArrayList<TicketPair> pairs = makeTicketPairs(); // Makes the TravelPayout API calls
        TicketPair min = pairs.get(0);
        for (int i = 1; i < pairs.size(); i++) {
            min = findMin(min, pairs.get(i)); // Calls the concrete class’ findMin()
        }
        return min;
    }



    // Using volley with the SkyScanner Rapid API to get cached flights
    private void jsonparse() {
        String url = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/browsequotes/v1.0/US/USD/en-US/PHX-sky/MIA-sky/2018-12-01/%20";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, (String)null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String temp = response.getString("title");
                            System.out.println(temp);
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

}
