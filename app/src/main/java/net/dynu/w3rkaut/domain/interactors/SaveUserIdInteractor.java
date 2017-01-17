package net.dynu.w3rkaut.domain.interactors;

public interface SaveUserIdInteractor {
    interface Callback {
        void onUserIdSaved();
    }

    void saveUserId(long id);
}
