package net.dynu.w3rkaut.domain.interactors.impl;

import net.dynu.w3rkaut.domain.executor.Executor;
import net.dynu.w3rkaut.domain.executor.MainThread;
import net.dynu.w3rkaut.domain.interactors.InsertUserInteractor;
import net.dynu.w3rkaut.domain.interactors.base.AbstractInteractor;
import net.dynu.w3rkaut.domain.model.User;
import net.dynu.w3rkaut.domain.repository.UserRepository;

/**
 * Created by sergio on 06/01/2017.
 */

public class InsertUserInteractorImpl extends AbstractInteractor implements InsertUserInteractor {
    private InsertUserInteractor.Callback callback;
    private UserRepository userRepository;

    private int userId;
    private String email;
    private String firstName;
    private String lastName;

    public InsertUserInteractorImpl(Executor threadExecutor, MainThread
            mainThread, Callback callback, UserRepository
            userRepository, int userId, String email, String firstName, String lastName) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.userRepository = userRepository;
        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public void run() {
        User user = new User(userId, email, firstName, lastName);
        userRepository.insert(user);

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUserInserted();
            }
        });

    }
}
