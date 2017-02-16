package net.dynu.w3rkaut.services.impl;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import net.dynu.w3rkaut.domain.model.User;
import net.dynu.w3rkaut.network.VolleySingleton;
import net.dynu.w3rkaut.services.interfaces.UserService;

import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements UserService {

    private static final String TAG = UserServiceImpl.class.getSimpleName();
    private static final String REST_API_URL = "https://w3rkaut.dynu.net/" +
            "android/php/";

    private VolleyCallback callback;
    private Context context;

    public UserServiceImpl(VolleyCallback callback, Context context) {
        this.callback = callback;
        this.context = context;
    }

    @Override
    public void insert(final User user) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                REST_API_URL + "login.php",
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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(user.getUserId()));
                params.put("email", user.getEmail());
                params.put("first_name", user.getFirstName());
                params.put("last_name", user.getLastName());
                return params;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    @Override
    public void delete(final long userId) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                REST_API_URL + "delete_user.php",
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
}
