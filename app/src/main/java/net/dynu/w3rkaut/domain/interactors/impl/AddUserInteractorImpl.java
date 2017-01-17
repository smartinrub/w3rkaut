package net.dynu.w3rkaut.domain.interactors.impl;


import android.os.Handler;

import net.dynu.w3rkaut.domain.interactors.AddUserInteractor;
import net.dynu.w3rkaut.domain.model.User;
import net.dynu.w3rkaut.domain.respository.UserRepository;

public class AddUserInteractorImpl implements AddUserInteractor {

    private Callback callback;
    private UserRepository userRepository;


    public AddUserInteractorImpl(UserRepository userRepository, Callback callback) {
        this.callback = callback;
        this.userRepository = userRepository;
    }

    @Override
    public void addUser(User user) {
        userRepository.insert(user);

        // notify on the main thread that we have inserted the user
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                callback.onUserAdded();
            }
        });
    }
}
