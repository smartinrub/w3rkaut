package net.dynu.w3rkaut.presentation.presenters.impl;

import net.dynu.w3rkaut.domain.executor.ThreadExecutor;
import net.dynu.w3rkaut.domain.interactors.LoginInteractor;
import net.dynu.w3rkaut.domain.interactors.impl.LoginInteractorImpl;
import net.dynu.w3rkaut.domain.respository.UserRepository;
import net.dynu.w3rkaut.presentation.LoginView;
import net.dynu.w3rkaut.presentation.presenters.LoginPresenter;

/**
 * Created by sergio on 07/01/2017.
 */

public class LoginPresenterImpl implements LoginPresenter, LoginInteractor.Callback {

    private LoginView loginView;
    private LoginInteractor loginInteractor;
    private UserRepository userRepository;

    public LoginPresenterImpl(LoginView loginView, UserRepository userRepository) {
        this.loginView = loginView;
        this.loginInteractor = new LoginInteractorImpl();
        this.userRepository = userRepository;
    }

    @Override
    public void saveCredentials(long id, String email, String firstName, String
            lastName) {
        loginInteractor.login(id, email, firstName, lastName, this,
                userRepository, new ThreadExecutor());
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError() {

    }
}
