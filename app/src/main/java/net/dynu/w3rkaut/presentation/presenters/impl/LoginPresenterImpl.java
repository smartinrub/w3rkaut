package net.dynu.w3rkaut.presentation.presenters.impl;

import net.dynu.w3rkaut.domain.executor.Executor;
import net.dynu.w3rkaut.domain.executor.MainThread;
import net.dynu.w3rkaut.domain.interactors.AddUserInteractor;
import net.dynu.w3rkaut.domain.interactors.SaveUserIdInteractor;
import net.dynu.w3rkaut.domain.interactors.impl.AddUserInteractorImpl;
import net.dynu.w3rkaut.domain.interactors.impl.SaveUserIdInteractorImpl;
import net.dynu.w3rkaut.domain.model.User;
import net.dynu.w3rkaut.domain.respository.UserRepository;
import net.dynu.w3rkaut.presentation.presenters.LoginPresenter;
import net.dynu.w3rkaut.presentation.presenters.base.AbstractPresenter;

/**
 * Presenter implementation which acts like a bridge between the interactors
 * for the login and the facebook fragment
 *
 * @author Sergio Martin Rubio
 */
public class LoginPresenterImpl extends AbstractPresenter implements LoginPresenter,
        AddUserInteractor.Callback, SaveUserIdInteractor.Callback {

    private UserRepository userRepository;

    public LoginPresenterImpl(Executor executor, MainThread mainThread,
                               UserRepository userRepository) {
        super(executor, mainThread);
        this.userRepository = userRepository;
    }

    @Override
    public void saveCredentials(long id, String email, String firstName, String
            lastName) {
        User user = new User(id, email, firstName, lastName);
        AddUserInteractor addUserInteractor = new AddUserInteractorImpl(
                mExecutor,
                mMainThread,
                userRepository,
                this,
                user);
        addUserInteractor.execute();
    }

    @Override
    public void saveUserId(long id) {
        SaveUserIdInteractor saveUserIdInteractor = new SaveUserIdInteractorImpl(
                mExecutor,
                mMainThread,
                userRepository,
                this,
                id);
        saveUserIdInteractor.execute();
    }

    @Override
    public void onUserIdSaved() {

    }

    @Override
    public void onUserAdded() {

    }
}
