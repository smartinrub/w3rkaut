package net.dynu.w3rkaut.domain.interactors.impl;

import android.content.Context;

import net.dynu.w3rkaut.domain.interactors.interfaces.DeleteUserInteractor;
import net.dynu.w3rkaut.services.impl.UserServiceImpl;
import net.dynu.w3rkaut.services.interfaces.UserService;
import net.dynu.w3rkaut.utils.SharedPreferencesManager;

/**
 * This class contains the business logic of deleting an user
 *
 * @author Sergio Martin Rubio
 */
public class DeleteUserInteractorImpl implements DeleteUserInteractor {

    @Override
    public void deleteUser(final long userId,
                           final UserService.VolleyCallback callback,
                           Context context) {

        SharedPreferencesManager.getInstance(context).setValue(userId);

        UserService userService = new UserServiceImpl(callback, context);
        userService.delete(userId);
    }
}
