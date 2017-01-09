package net.dynu.w3rkaut.domain.interactors.impl;


import net.dynu.w3rkaut.domain.executor.Executor;
import net.dynu.w3rkaut.domain.executor.MainThread;
import net.dynu.w3rkaut.domain.interactors.AddUserInteractor;
import net.dynu.w3rkaut.domain.interactors.base.AbstractInteractor;
import net.dynu.w3rkaut.domain.model.User;
import net.dynu.w3rkaut.domain.respository.UserRepository;

public class AddUserInteractorImpl extends AbstractInteractor implements AddUserInteractor {

    private Callback callback;
    private UserRepository userRepository;

    private User user;

    public AddUserInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                 UserRepository userRepository,
                                 Callback callback, User user) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.userRepository = userRepository;
        this.user = user;
    }

    @Override
    public void run() {
        // insert user
        userRepository.insert(user);

        // notify on the main thread that we have inserted the user
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUserAdded();
            }
        });
    }
}
