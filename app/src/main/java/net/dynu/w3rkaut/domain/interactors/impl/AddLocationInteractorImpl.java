package net.dynu.w3rkaut.domain.interactors.impl;

import net.dynu.w3rkaut.domain.interactors.interfaces.AddLocationInteractor;
import net.dynu.w3rkaut.network.VolleySingleton;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contains the business logic of adding a new location
 *
 * @author Sergio Martin Rubio
 */
public class AddLocationInteractorImpl implements AddLocationInteractor {

    private static final String REST_API_URL = "https://w3rkaut.dynu.net/" +
            "android/php/insert_location.php";

    @Override
    public void addLocation(final long userId,
                            final Double latitude,
                            final Double longitude,
                            final String duration,
                            final String postedAt,
                            final Callback callback,
                            Context context) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                REST_API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onLocationAdded(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(userId));
                params.put("latitude", String.valueOf(latitude));
                params.put("longitude", String.valueOf(longitude));
                params.put("duration", duration);
                params.put("posted_at", postedAt);
                return params;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }
}
