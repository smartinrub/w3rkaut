package net.dynu.w3rkaut.domain.interactors.interfaces;


import android.content.Context;

public interface DeleteLocationInteractor {

    interface Callback {
        void onLocationDeleted(String response);
    }

    void deleteLocation(long userId, Callback callback, Context context);
}
