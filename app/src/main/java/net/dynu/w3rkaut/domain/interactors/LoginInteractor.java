package net.dynu.w3rkaut.domain.interactors;

import net.dynu.w3rkaut.domain.model.User;

/**
 * Created by sergio on 06/01/2017.
 */

public interface LoginInteractor {
    interface Callback {
        void onSuccess();
        void onCancel();
        void onError();
    }

    void login(User user);
}
