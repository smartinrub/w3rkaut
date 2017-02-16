package net.dynu.w3rkaut.domain.interactors.impl;

import android.content.Context;

import net.dynu.w3rkaut.domain.interactors.interfaces.AddUserInteractor;
import net.dynu.w3rkaut.domain.model.User;
import net.dynu.w3rkaut.services.impl.UserServiceImpl;
import net.dynu.w3rkaut.services.interfaces.UserService;
import net.dynu.w3rkaut.utils.SharedPreferencesManager;

/**
 * This class contains the business logic of saving the addUser
 *
 * @author Sergio Martin Rubio
 */
public class AddUserInteractorImpl implements AddUserInteractor {

    @Override
    public void addUser(final long userId, final String email, final String
            firstName, final String lastName, final UserService.VolleyCallback callback,
                        Context context) {
        SharedPreferencesManager.getInstance(context).setValue(userId);

        UserService userService = new UserServiceImpl(callback, context);
        User user = new User(userId, email,firstName, lastName);
        userService.insert(user);
    }
}
