package net.dynu.w3rkaut.domain.interactors.impl;

import net.dynu.w3rkaut.domain.executor.Executor;
import net.dynu.w3rkaut.domain.executor.MainThread;
import net.dynu.w3rkaut.domain.interactors.SaveUserIdInteractor;
import net.dynu.w3rkaut.domain.interactors.base.AbstractInteractor;
import net.dynu.w3rkaut.domain.respository.UserRepository;

/**
 * Created by sergio on 08/01/2017.
 */

public class SaveUserIdInteractorImpl extends AbstractInteractor implements SaveUserIdInteractor {

    private Callback callback;
    private UserRepository userRepository;

    private long id;

    public SaveUserIdInteractorImpl(Executor threadExecutor, MainThread
            mainThread,UserRepository userRepository, Callback callback,
                                    long id) {
        super(threadExecutor, mainThread);
        this.callback = callback;
        this.userRepository = userRepository;
        this.id = id;
    }

    @Override
    public void run() {
        userRepository.saveUserId(id);
        callback.onUserIdSaved();
    }
}
