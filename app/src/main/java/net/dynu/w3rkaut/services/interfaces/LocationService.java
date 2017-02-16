package net.dynu.w3rkaut.services.interfaces;

import com.android.volley.VolleyError;

import net.dynu.w3rkaut.network.model.RESTLocation;

public interface LocationService {

    interface VolleyCallback {

        void notifySuccess(String response);

        void notifyError(VolleyError error);
    }

    void insert(RESTLocation location);

    void delete(long userId);

    void getAll();
}
