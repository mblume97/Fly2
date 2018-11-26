# Fly2
EE461L Project


Possible things to add:

Unsplash API for pictures - gives you a url for a picture
https://unsplash.com/documentation

Example - place inside a volley GET request:

 String url = "https://api.unsplash.com/search/photos?page=1&query=Houston&client_id=6651e9c60ee7bc00749d8ae180359ffcccda0500a4a49bde116b48d7c3407223";
 JSONObject temp = response.getJSONArray("results").getJSONObject(0).getJSONObject("urls");
 String url = temp.getString("raw");
 Ion.with(imageView)
    .load(url);


This is using ion to display images
https://github.com/koush/ion


I tried to mess around with the Flickr API - can still use that i just couldnt get it


private void getPicture() {
        String url = "https://api.unsplash.com/search/photos?page=1&query=Houston&client_id=6651e9c60ee7bc00749d8ae180359ffcccda0500a4a49bde116b48d7c3407223";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, (String)null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject temp = response.getJSONArray("results").getJSONObject(0).getJSONObject("urls");
                            String url = temp.getString("raw");
                            Ion.with(imageView)
                                    .load(url);
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