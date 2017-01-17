package net.dynu.w3rkaut.domain.interactors.impl;

import net.dynu.w3rkaut.domain.interactors.SaveUserIdInteractor;
import net.dynu.w3rkaut.domain.respository.UserRepository;

public class SaveUserIdInteractorImpl implements SaveUserIdInteractor {

    private Callback callback;
    private UserRepository userRepository;

    public SaveUserIdInteractorImpl(UserRepository userRepository,
                                    Callback callback) {
        this.callback = callback;
        this.userRepository = userRepository;
    }

    @Override
    public void saveUserId(long id) {
        userRepository.saveId(id);
        callback.onUserIdSaved();
    }
}
