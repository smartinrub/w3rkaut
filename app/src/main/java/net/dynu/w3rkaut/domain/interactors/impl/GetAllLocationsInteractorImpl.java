package net.dynu.w3rkaut.domain.interactors.impl;

import net.dynu.w3rkaut.domain.executor.Executor;
import net.dynu.w3rkaut.domain.executor.MainThread;
import net.dynu.w3rkaut.domain.interactors.GetAllLocationsInteractor;
import net.dynu.w3rkaut.domain.interactors.base.AbstractInteractor;
import net.dynu.w3rkaut.network.model.RESTLocation;
import net.dynu.w3rkaut.domain.respository.LocationRepository;

import java.util.List;

import timber.log.Timber;

/**
 * This class contains the business logic of getting all locations
 *
 * @author Sergio Martin Rubio
 */
public class GetAllLocationsInteractorImpl extends AbstractInteractor implements
        GetAllLocationsInteractor {

    private Callback callback;
    private LocationRepository locationRepository;


    public GetAllLocationsInteractorImpl(Executor threadExecutor, MainThread
            mainThread, LocationRepository locationRepository, Callback callback) {
        super(threadExecutor, mainThread);
        this.locationRepository = locationRepository;
        this.callback = callback;
    }

    @Override
    public void run() {
        final List<RESTLocation> locations = locationRepository.getAll();

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onLocationsRetrieved(locations);
            }
        });
    }
}
