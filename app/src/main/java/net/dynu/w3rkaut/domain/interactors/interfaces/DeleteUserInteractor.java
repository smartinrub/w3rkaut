package net.dynu.w3rkaut.domain.interactors.interfaces;

import android.content.Context;

public interface DeleteUserInteractor {

    interface Callback {
        void onUserDeleted(String response);
    }

    void deleteUser(long userId, Callback callback, Context context);
}
