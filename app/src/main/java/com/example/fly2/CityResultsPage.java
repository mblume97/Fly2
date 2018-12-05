package com.example.fly2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.koushikdutta.ion.Ion;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

// results that allows users to pick what city to go to
public class CityResultsPage extends AppCompatActivity {

    private Double firstPrice;
    private Double secondPrice;
    private String city;
    private String airportCode;
    private String airportName;
    private String firstAirline;
    private String secondAirline;
    private String airline;
    private AsyncHttpClient client;
    private ImageView imageView;
    private RequestQueue mQueue;
    private TextView firstHotelView;
    private TextView secondHotelView;
    private TextView thirdHotelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_results_page);
        Bundle extras = getIntent().getExtras();

        mQueue = Volley.newRequestQueue(this);

        firstPrice = extras.getDouble("firstPrice");

        secondPrice = extras.getDouble("secondPrice");
        city = extras.getString("city");
        airportCode = extras.getString("code");
        airportName = extras.getString("name");
        firstAirline = extras.getString("firstAirline");
        secondAirline = extras.getString("secondAirline");


        TextView destinationTextView = (TextView)findViewById(R.id.destinationCity);
        destinationTextView.setText(city);

        TextView firstPriceView = (TextView)findViewById(R.id.firstPrice);
        firstPriceView.setText(String.format(Locale.US, "$%.2f", firstPrice));

        TextView secondPriceView = (TextView)findViewById(R.id.secondPrice);
        secondPriceView.setText(String.format(Locale.US, "$%.2f", secondPrice));

        TextView firstAirlineView = (TextView)findViewById(R.id.firstAirline);
        firstAirlineView.setText(firstAirline);

        TextView secondAirlineView = (TextView)findViewById(R.id.secondAirline);
        secondAirlineView.setText(secondAirline);

        firstHotelView = (TextView)findViewById(R.id.firstHotel);
        secondHotelView = (TextView)findViewById(R.id.secondHotel);
        thirdHotelView = (TextView)findViewById(R.id.thirdHotel);
        imageView = findViewById(R.id.imageView);

        // TODO: if either price is infinity, display there are no flights to any destination

        displayImage(city.split(",")[0]);
        getHotels(city);
    }


    private void displayImage(String city) {
        String url = "https://api.unsplash.com/search/photos?page=1&query=" + city + "&client_id=6651e9c60ee7bc00749d8ae180359ffcccda0500a4a49bde116b48d7c3407223";
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
        (Request.Method.GET, url, (String)null, new Response.Listener<JSONObject>() {

        @Override
        public void onResponse(JSONObject response) {
        try {
            JSONObject temp = response.getJSONArray("results").getJSONObject(0).getJSONObject("urls");
            String url = temp.getString("raw");
            Ion.with(imageView).placeholder(R.drawable.ic_launcher_background).load(url);
                System.out.println(url);
        } catch (JSONException e) {
        e.printStackTrace();
        }
        }
    }, new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
        }
    });
    mQueue.add(jsonObjectRequest);
    }


    private void getHotels(String city) {
        String CLIENT_ID = "ZRDWZXYGEC4UQS0YOVIHDJX3J5YW5PMLSPRVWGIORIU5J3VI";
        String CLIENT_SECRET = "TQXHIYG1WLMWB5GFLSPXPXRB4LTNV4XIYXZ5KMVYHYLXEOEA";
        String url = "https://api.foursquare.com/v2/venues/search?client_id=" + CLIENT_ID +
                "&client_secret=" + CLIENT_SECRET + "&near=" + city + "&query=hotel&limit=3&v=20181128";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, (String)null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray venues = response.getJSONObject("response").getJSONArray("venues");
                            ArrayList<String> temp = new ArrayList<>();
                            for (int i = 0; i < 3; i++) {
                                JSONObject venue = venues.getJSONObject(i);
                                String name = venue.getString("name");
                                temp.add(name);
                            }
                            firstHotelView.setText(temp.get(0));
                            secondHotelView.setText(temp.get(1));
                            thirdHotelView.setText(temp.get(2));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        mQueue.add(jsonObjectRequest);
    }


}
