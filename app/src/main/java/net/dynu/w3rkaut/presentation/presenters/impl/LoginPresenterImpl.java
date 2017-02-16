package net.dynu.w3rkaut.presentation.presenters.impl;

import android.content.Context;

import net.dynu.w3rkaut.domain.interactors.interfaces.LoginInteractor;
import net.dynu.w3rkaut.domain.interactors.impl.LoginInteractorImpl;
import net.dynu.w3rkaut.presentation.presenters.interfaces.LoginPresenter;

/**
 * Presenter implementation which acts like a bridge between the interactors
 * for the login and the facebook fragment
 *
 * @author Sergio Martin Rubio
 */
public class LoginPresenterImpl implements LoginPresenter, LoginInteractor.Callback {

    private Context context;

    public LoginPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void saveCredentials(long id, String email, String firstName, String
            lastName) {
        LoginInteractor loginInteractor = new LoginInteractorImpl();
        loginInteractor.login(id, email, firstName, lastName, this, context);
    }

    @Override
    public void onUserAdded(String response) {

    }
}
