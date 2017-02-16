package net.dynu.w3rkaut.domain.interactors.interfaces;

import android.content.Context;

import net.dynu.w3rkaut.services.interfaces.UserService;

public interface AddUserInteractor {

    void addUser(long userId, String email, String firstName, String lastName,
                 UserService.VolleyCallback callback, Context context);
}
