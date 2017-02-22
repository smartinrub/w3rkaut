package net.dynu.w3rkaut.services.impl;


import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import net.dynu.w3rkaut.network.VolleySingleton;
import net.dynu.w3rkaut.domain.model.LocationRest;
import net.dynu.w3rkaut.services.interfaces.LocationService;

import java.util.HashMap;
import java.util.Map;

public class LocationServiceImpl implements LocationService {

    private static final String TAG = LocationServiceImpl.class.getSimpleName();
    private static final String REST_API_URL = "https://w3rkaut.dynu.net/" +
            "android/php/";

    private VolleyCallback callback;
    private Context context;

    public LocationServiceImpl(VolleyCallback callback, Context context) {
        this.callback = callback;
        this.context = context;
    }

    @Override
    public void insert(final LocationRest location) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                REST_API_URL + "insert_location.php",
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "INSERT VOLLEY RESPONSE OK");
                callback.notifySuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "INSERT VOLLEY ERROR");
                callback.notifyError(error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(location.getUserId()));
                params.put("latitude", String.valueOf(location.getLatitude()));
                params.put("longitude", String.valueOf(location.getLongitude()));
                params.put("duration", location.getTimeRemaining());
                params.put("posted_at", location.getPostedAt());
                return params;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    @Override
    public void delete(final long userId) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                REST_API_URL + "delete_location.php",
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "DELETE VOLLEY RESPONSE OK");
                callback.notifySuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "DELETE VOLLEY ERROR");
                callback.notifyError(error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(userId));
                return params;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    @Override
    public void getAll() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                REST_API_URL + "locations.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "GET ALL LOCATIONS VOLLEY RESPONSE OK");
                        callback.notifySuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "GET ALL LOCATIONS VOLLEY ERROR");
                callback.notifyError(error);
            }
        });
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    @Override
    public void getUserLocations(final long userId) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                REST_API_URL + "user_locations.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "GET ALL USER LOCATIONS VOLLEY RESPONSE OK");
                        callback.notifySuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "GET ALL USER LOCATIONS VOLLEY ERROR");
                callback.notifyError(error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(userId));
                return params;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }
}
