package net.dynu.w3rkaut.services.interfaces;

import com.android.volley.VolleyError;

import net.dynu.w3rkaut.domain.model.Location;

public interface LocationService {

    interface VolleyCallback {

        void notifySuccess(String response);

        void notifyError(VolleyError error);
    }

    void insert(Location location);

    void delete(long userId);

    void getAll();
}
