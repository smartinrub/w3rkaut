package net.dynu.w3rkaut.domain.interactors.interfaces;

import android.content.Context;

public interface LoginInteractor {

    interface Callback {
        void onUserAdded(String response);
    }

    void login(long userId, String email, String firstName, String lastName,
               Callback callback, Context context);
}
