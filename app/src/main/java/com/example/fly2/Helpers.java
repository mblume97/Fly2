package com.example.fly2;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class Helpers {

    // Reading the local json from the assets folder
    // Returns a string
    public static String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("airport_code_to_city.json"); // Name of json file in assets folder
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    // This would be placed inside an activity class.
    // Example of how to use the above function
    /*
    String json = loadJSONFromAsset(this);
    try {
        JSONObject obj = new JSONObject(json);
        String temp = obj.getString("ATL"); // how to receive one of the keys.

        Iterator<String> iter = obj.keys();
        while (iter.hasNext()) {
            String key = iter.next(); // Looping through all of the keys.. getting all the keys!
            try {
                Object value = obj.get(key); // Getting the value of the key. aka the city name
            } catch (JSONException e) {
                // Something went wrong!
            }
        }
    } catch (JSONException e) {
        e.printStackTrace();
    }
    */

}
