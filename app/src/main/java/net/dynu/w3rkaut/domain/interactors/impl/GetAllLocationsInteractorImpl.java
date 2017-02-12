package net.dynu.w3rkaut.domain.interactors.impl;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import net.dynu.w3rkaut.domain.interactors.GetAllLocationsInteractor;
import net.dynu.w3rkaut.network.VolleySingleton;
import net.dynu.w3rkaut.network.model.RESTLocation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains the business logic of getting all locations
 *
 * @author Sergio Martin Rubio
 */
public class GetAllLocationsInteractorImpl implements GetAllLocationsInteractor {

    private static final String REST_API_URL = "https://w3rkaut.dynu.net/android/php/locations.php";

    @Override
    public void getAllLocation(final Callback callback, Context context) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                REST_API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    List<RESTLocation> locations = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        long userId = jsonObject.getLong("user_id");
                        String firstName = jsonObject.getString("first_name");
                        String lastName = jsonObject.getString("last_name");
                        double lat = Double.parseDouble(jsonObject.getString("latitude"));
                        double lng = Double.parseDouble(jsonObject.getString("longitude"));
                        String timeRemaining = jsonObject.getString("time_remaining");
                        String postedAt = jsonObject.getString("posted_at");
                        RESTLocation location = new RESTLocation(userId, firstName,
                                lastName, lat, lng, timeRemaining, postedAt);

                        locations.add(location);
                    }
                    callback.onLocationsRetrieved(locations);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("error: ", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LocationsInteractor", String.valueOf(error));
            }
        });
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }
}
