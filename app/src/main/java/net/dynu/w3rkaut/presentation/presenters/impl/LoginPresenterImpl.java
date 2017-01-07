package net.dynu.w3rkaut.presentation.presenters.impl;

import net.dynu.w3rkaut.domain.executor.Executor;
import net.dynu.w3rkaut.domain.executor.MainThread;
import net.dynu.w3rkaut.domain.interactors.GetUserIteractor;
import net.dynu.w3rkaut.domain.interactors.InsertUserInteractor;
import net.dynu.w3rkaut.domain.model.User;
import net.dynu.w3rkaut.domain.repository.UserRepository;
import net.dynu.w3rkaut.presentation.presenters.base.AbstractPresenter;
import net.dynu.w3rkaut.presentation.presenters.LoginPresenter;

/**
 * Created by sergio on 07/01/2017.
 */

public class LoginPresenterImpl extends AbstractPresenter implements
        LoginPresenter, GetUserIteractor.Callback, InsertUserInteractor.Callback {

    private LoginPresenter.View view;
    private UserRepository userRepository;

    public LoginPresenterImpl(Executor executor, MainThread mainThread, View
            view, UserRepository userRepository) {
        super(executor, mainThread);
        this.view = view;
        this.userRepository = userRepository;
    }


    @Override
    public void onUserRetrieved(User user) {
    }

    @Override
    public void onUserInserted() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onError(String message) {

    }
}
