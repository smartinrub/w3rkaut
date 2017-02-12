package net.dynu.w3rkaut.domain.interactors;


import android.content.Context;

public interface AddLocationInteractor {

    interface Callback {
        void onLocationAdded(String response);
    }

    void addLoction(long id,
                    Double latitude,
                    Double longitude,
                    String duration,
                    String postedAt,
                    Callback callback,
                    Context context);
}
