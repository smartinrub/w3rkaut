package net.dynu.w3rkaut.domain.interactors.impl;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import net.dynu.w3rkaut.domain.interactors.DeleteLocationInteractor;
import net.dynu.w3rkaut.network.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contains the business logic of deleting a location
 *
 * @author Sergio Martin Rubio
 */
public class DeleteLocationInteractorImpl implements DeleteLocationInteractor {

    private static final String REST_API_URL = "https://w3rkaut.dynu.net/" +
            "android/php/delete_location.php";

    @Override
    public void deleteLocation(final long userId, final Callback callback, Context context) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                REST_API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onLocationDeleted(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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
