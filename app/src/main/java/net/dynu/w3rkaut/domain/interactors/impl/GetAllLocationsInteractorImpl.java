package net.dynu.w3rkaut.domain.interactors.impl;

import android.os.Handler;

import net.dynu.w3rkaut.domain.interactors.GetAllLocationsInteractor;
import net.dynu.w3rkaut.domain.respository.LocationRepository;
import net.dynu.w3rkaut.network.model.RESTLocation;

import java.util.List;

public class GetAllLocationsInteractorImpl implements GetAllLocationsInteractor {

    private Callback callback;
    private LocationRepository locationRepository;


    public GetAllLocationsInteractorImpl(LocationRepository locationRepository, Callback callback) {
        this.locationRepository = locationRepository;
        this.callback = callback;
    }

    @Override
    public void getAllLocations() {
        final List<RESTLocation> locations = locationRepository.getAll();

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                callback.onLocationsRetrieved(locations);
            }
        });
    }
}
