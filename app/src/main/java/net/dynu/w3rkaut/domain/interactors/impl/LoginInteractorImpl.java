package net.dynu.w3rkaut.domain.interactors.impl;

import net.dynu.w3rkaut.domain.interactors.LoginInteractor;
import net.dynu.w3rkaut.network.VolleySingleton;
import net.dynu.w3rkaut.utils.SharedPreferencesManager;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contains the business logic of saving the login
 *
 * @author Sergio Martin Rubio
 */
public class LoginInteractorImpl implements LoginInteractor {

    private static final String REST_API_URL = "https://w3rkaut.dynu.net/" +
            "android/php/login.php";

    @Override
    public void login(final long userId, final String email, final String
            firstName, final String lastName, final Callback callback,
                      Context context) {


        SharedPreferencesManager.getInstance(context).setValue(userId);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                REST_API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onUserAdded(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", String.valueOf(userId));
                params.put("email", email);
                params.put("first_name", firstName);
                params.put("last_name", lastName);
                return params;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }
}
