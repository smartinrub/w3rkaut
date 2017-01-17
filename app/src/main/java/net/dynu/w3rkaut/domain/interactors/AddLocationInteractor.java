package net.dynu.w3rkaut.domain.interactors;


public interface AddLocationInteractor {

    interface Callback {
        void onFail(String response);
        void onSuccess(String response);
    }

    void addLocation(long id, Double latitude, Double longitude, Integer
            participants, String postedAt);
}
