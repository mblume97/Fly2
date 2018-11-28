package com.example.fly2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

// results that allows users to pick what city to go to
public class CityResultsPage extends AppCompatActivity {

    private double firstPrice;
    private double secondPrice;
    private String city;
    private String airportCode;
    private String airportName;
    private String firstAirline;
    private String secondAirline;
    private String airline;
    String url;
    AsyncHttpClient client;
    ImageView imageView;
    private RequestQueue mQueue;

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

        System.out.print("");

        this.client = new AsyncHttpClient();
        url = PhotoClient.createURL("atlanta");
        imageView = findViewById(R.id.imageView);
        displayImage();
    }


    private void displayImage() {
        String url = "https://api.unsplash.com/search/photos?page=1&query=Houston&client_id=6651e9c60ee7bc00749d8ae180359ffcccda0500a4a49bde116b48d7c3407223";
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


}
