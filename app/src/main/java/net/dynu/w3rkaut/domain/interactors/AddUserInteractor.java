package net.dynu.w3rkaut.domain.interactors;


import net.dynu.w3rkaut.domain.model.User;

public interface AddUserInteractor {

    interface Callback {
        void onUserAdded();
    }

    void addUser(User user);
}
