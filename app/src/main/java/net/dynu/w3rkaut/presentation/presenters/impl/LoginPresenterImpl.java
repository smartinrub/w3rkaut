package net.dynu.w3rkaut.presentation.presenters.impl;

import android.content.Context;

import com.android.volley.VolleyError;

import net.dynu.w3rkaut.domain.interactors.interfaces.AddUserInteractor;
import net.dynu.w3rkaut.domain.interactors.impl.AddUserInteractorImpl;
import net.dynu.w3rkaut.presentation.presenters.interfaces.LoginPresenter;
import net.dynu.w3rkaut.services.interfaces.UserService;

/**
 * Presenter implementation which acts like a bridge between the interactors
 * for the addUser and the facebook fragment
 *
 * @author Sergio Martin Rubio
 */
public class LoginPresenterImpl implements LoginPresenter, UserService.VolleyCallback {

    private Context context;

    public LoginPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void saveCredentials(long id, String email, String firstName, String
            lastName) {
        AddUserInteractor addUserInteractor = new AddUserInteractorImpl();
        addUserInteractor.addUser(id, email, firstName, lastName, this, context);
    }

    @Override
    public void notifySuccess(String response) {

    }

    @Override
    public void notifyError(VolleyError error) {

    }
}
