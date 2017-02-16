package net.dynu.w3rkaut.services.interfaces;

import com.android.volley.VolleyError;

import net.dynu.w3rkaut.domain.model.User;

public interface UserService {

    interface VolleyCallback {

        void notifySuccess(String response);

        void notifyError(VolleyError error);
    }

    void insert(User user);

    void delete(long userId);
}
