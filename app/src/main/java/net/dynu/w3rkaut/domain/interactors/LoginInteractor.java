package net.dynu.w3rkaut.domain.interactors;

import net.dynu.w3rkaut.domain.model.User;
import net.dynu.w3rkaut.domain.respository.UserRepository;

/**
 * Created by sergio on 06/01/2017.
 */

public interface LoginInteractor {
    interface Callback {
        void onSuccess();
        void onCancel();
        void onError();
    }

    void login(long id, String email, String firstName, String lastName,
               Callback callback, UserRepository userRepository);
}
