package net.dynu.w3rkaut.domain.interactors.impl;

import net.dynu.w3rkaut.domain.executor.Executor;
import net.dynu.w3rkaut.domain.executor.MainThread;
import net.dynu.w3rkaut.domain.interactors.AddLocationInteractor;
import net.dynu.w3rkaut.domain.interactors.base.AbstractInteractor;
import net.dynu.w3rkaut.domain.model.Location;
import net.dynu.w3rkaut.domain.respository.LocationRepository;
import net.dynu.w3rkaut.network.converters.RESTLocationConverter;
import net.dynu.w3rkaut.network.model.RESTLocation;

public class AddLocationInteractorImpl extends AbstractInteractor implements
        AddLocationInteractor {

    private Callback callback;
    private LocationRepository locationRepository;

    private long id;
    private Double latitude;
    private Double longitude;
    private Integer participants;
    private String postedAt;

    public AddLocationInteractorImpl(Executor threadExecutor, MainThread
            mainThread, LocationRepository locationRepository, Callback
            callback, long id, Double latitude, Double longitude, Integer
            participants, String postedAt) {
        super(threadExecutor, mainThread);
        this.locationRepository = locationRepository;
        this.callback = callback;
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.participants = participants;
        this.postedAt = postedAt;
    }

    @Override
    public void run() {
        Location location = new Location(id, participants);
        RESTLocationConverter converter = new RESTLocationConverter();
        RESTLocation restLocation = converter.convertToRestModel(
                location, latitude, longitude, postedAt);
        final String response = locationRepository.insert(restLocation);

        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onLocationAdded(response);
            }
        });
    }
}
