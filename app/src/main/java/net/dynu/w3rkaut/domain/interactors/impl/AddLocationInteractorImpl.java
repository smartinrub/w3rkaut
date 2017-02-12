package net.dynu.w3rkaut.domain.interactors.impl;

import net.dynu.w3rkaut.domain.interactors.AddLocationInteractor;
import net.dynu.w3rkaut.network.VolleySingleton;

import android.content.Context;
import android.os.Handler;

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
public class AddLocationInteractorImpl implements AddLocationInteractor, Response.Listener<String>, Response.ErrorListener {

    private static final String REST_API_URL = "https://w3rkaut.dynu.net/" +
            "android/php/insert_location.php";

    private long userId;
    private Double latitude;
    private Double longitude;
    private String duration;
    private String postedAt;

    private Context context;
    private String response;

    @Override
    public void addLoction(long userId,
                           Double latitude,
                           Double longitude,
                           String duration,
                           String postedAt,
                           final Callback callback,
                           Context context) {
        this.context = context;
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.duration = duration;
        this.postedAt = postedAt;

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                callback.onLocationAdded(saveLocation());
            }
        });
    }

    private String saveLocation() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                REST_API_URL, this, this){
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
        return response;
    }

    @Override
    public void onResponse(String response) {
        this.response = response;
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }


}
