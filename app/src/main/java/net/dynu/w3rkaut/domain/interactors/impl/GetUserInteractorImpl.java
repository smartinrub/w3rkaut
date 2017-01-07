package net.dynu.w3rkaut.domain.interactors.impl;

import net.dynu.w3rkaut.domain.executor.Executor;
import net.dynu.w3rkaut.domain.executor.MainThread;
import net.dynu.w3rkaut.domain.interactors.GetUserIteractor;
import net.dynu.w3rkaut.domain.interactors.base.AbstractInteractor;
import net.dynu.w3rkaut.domain.model.User;
import net.dynu.w3rkaut.domain.repository.UserRepository;

/**
 * Created by sergio on 06/01/2017.
 */

public class GetUserInteractorImpl extends AbstractInteractor implements GetUserIteractor{

    private GetUserIteractor.Callback callback;
    private UserRepository userRepository;

    public GetUserInteractorImpl(Executor threadExecutor, MainThread
            mainThread, Callback callback, UserRepository userRepository) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.userRepository = userRepository;
    }

    @Override
    public void run() {
        final User user = userRepository.get();

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUserRetrieved(user);
            }
        });

    }
}
