package net.dynu.w3rkaut.domain.interactors.impl;


import net.dynu.w3rkaut.domain.executor.Executor;
import net.dynu.w3rkaut.domain.interactors.LoginInteractor;
import net.dynu.w3rkaut.domain.model.User;
import net.dynu.w3rkaut.domain.respository.UserRepository;

/**
 * Created by sergio on 06/01/2017.
 */

public class LoginInteractorImpl implements LoginInteractor {

    private LoginInteractor.Callback callback;
    private UserRepository userRepository;
    private Executor threadExecutor;

    public LoginInteractorImpl(Callback callback, UserRepository
            userRepository, Executor threadExecutor) {
        this.callback = callback;
        this.userRepository = userRepository;
        this.threadExecutor = threadExecutor;
    }

    @Override
    public void login(final User user) {

        threadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userRepository.insert(user);
            }
        });
    }
}
