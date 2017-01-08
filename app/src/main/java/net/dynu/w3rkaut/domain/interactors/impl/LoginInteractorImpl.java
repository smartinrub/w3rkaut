package net.dynu.w3rkaut.domain.interactors.impl;


import android.os.Handler;

import net.dynu.w3rkaut.domain.executor.Executor;
import net.dynu.w3rkaut.domain.executor.ThreadExecutor;
import net.dynu.w3rkaut.domain.interactors.LoginInteractor;
import net.dynu.w3rkaut.domain.model.User;
import net.dynu.w3rkaut.domain.respository.UserRepository;

/**
 * Created by sergio on 06/01/2017.
 */

public class LoginInteractorImpl implements LoginInteractor {


    @Override
    public void login(final long id, final String email, final String firstName,
                      final String lastName,
                      final Callback callback, final UserRepository
                                  userRepository, Executor threadExecutor) {

        threadExecutor.execute(new Runnable() {
            @Override
            public void run() {
                User user = new User(id, email, firstName, lastName);
                userRepository.insert(user);
            }
        });
    }
}
