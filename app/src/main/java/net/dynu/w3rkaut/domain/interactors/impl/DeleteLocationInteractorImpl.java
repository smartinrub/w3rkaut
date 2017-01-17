package net.dynu.w3rkaut.domain.interactors.impl;


import net.dynu.w3rkaut.domain.interactors.DeleteLocationInteractor;
import net.dynu.w3rkaut.domain.respository.LocationRepository;
import android.os.Handler;


public class DeleteLocationInteractorImpl implements DeleteLocationInteractor {

    private Callback callback;
    private LocationRepository locationRepository;

    private long userId;

    public DeleteLocationInteractorImpl(LocationRepository locationRepository, Callback
            callback, long userId) {
        this.locationRepository = locationRepository;
        this.callback = callback;
        this.userId = userId;
    }


    @Override
    public void deleteLocation(long id) {
        final String response = locationRepository.delete(userId);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if(response.indexOf("successfully deleted") > 0) {
                    callback.onFail("Localización eliminada con éxito");
                } else {
                    callback.onSuccess("No se ha podido eliminar la localización");
                }
                callback.onSuccess(response);
            }
        });
    }
}
