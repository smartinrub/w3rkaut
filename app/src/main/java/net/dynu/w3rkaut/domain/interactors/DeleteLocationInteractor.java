package net.dynu.w3rkaut.domain.interactors;


public interface DeleteLocationInteractor {

    interface Callback {
        void onSuccess(String response);
        void onFail(String message);
    }

    void deleteLocation(long id);
}
