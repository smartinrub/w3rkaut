package net.dynu.w3rkaut.domain.interactors.impl;

import android.os.Handler;

import net.dynu.w3rkaut.domain.interactors.AddLocationInteractor;
import net.dynu.w3rkaut.domain.respository.LocationRepository;

public class AddLocationInteractorImpl implements AddLocationInteractor {

    private Callback callback;
    private LocationRepository locationRepository;

    public AddLocationInteractorImpl(LocationRepository locationRepository,
                                     Callback callback) {
        this.locationRepository = locationRepository;
        this.callback = callback;
    }

    @Override
    public void addLocation(long id, Double latitude, Double longitude,
                            Integer participants, String postedAt) {
        final String response = locationRepository.insert(id, participants,
                latitude, longitude, postedAt);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if (response.indexOf("user already has a location") > 0) {
                    callback.onFail("Ya has publicado una localización");
                } else {
                    callback.onSuccess("Posicion añadida");
                }
            }
        });
    }
}
