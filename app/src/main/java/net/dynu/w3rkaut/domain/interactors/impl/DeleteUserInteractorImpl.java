package net.dynu.w3rkaut.domain.interactors.impl;

import net.dynu.w3rkaut.domain.executor.Executor;
import net.dynu.w3rkaut.domain.executor.MainThread;
import net.dynu.w3rkaut.domain.interactors.DeleteUserInteractor;
import net.dynu.w3rkaut.domain.interactors.base.AbstractInteractor;
import net.dynu.w3rkaut.domain.respository.UserRepository;

/**
 * This class contains the business logic of deleting an user
 *
 * @author Sergio Martin Rubio
 */
public class DeleteUserInteractorImpl extends AbstractInteractor implements
        DeleteUserInteractor{

    private Callback callback;
    private UserRepository userRepository;

    private long userId;

    public DeleteUserInteractorImpl(Executor threadExecutor, MainThread mainThread,
                                    UserRepository userRepository, Callback
                                            callback, long userId) {
        super(threadExecutor, mainThread);
        this.userRepository = userRepository;
        this.callback = callback;
        this.userId = userId;
    }

    @Override
    public void run() {
        final String response = userRepository.delete(userId);

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUserDeleted(response);
            }
        });
    }
}
