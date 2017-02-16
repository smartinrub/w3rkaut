package net.dynu.w3rkaut.domain.interactors.interfaces;

import android.content.Context;

import net.dynu.w3rkaut.services.interfaces.UserService;

public interface DeleteUserInteractor {

    void deleteUser(long userId, UserService.VolleyCallback callback, Context context);
}
