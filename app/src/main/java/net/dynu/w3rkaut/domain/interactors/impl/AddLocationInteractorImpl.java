package net.dynu.w3rkaut.domain.interactors.impl;

import net.dynu.w3rkaut.domain.executor.Executor;
import net.dynu.w3rkaut.domain.executor.MainThread;
import net.dynu.w3rkaut.domain.interactors.AddLocationInteractor;
import net.dynu.w3rkaut.domain.interactors.base.AbstractInteractor;
import net.dynu.w3rkaut.domain.respository.LocationRepository;

public class AddLocationInteractorImpl extends AbstractInteractor implements
        AddLocationInteractor {

    private Callback callback;
    private LocationRepository locationRepository;

    private long id;
    private Double latitude;
    private Double longitude;
    private Integer participants;
    private String timeRemaining;

    public AddLocationInteractorImpl(Executor threadExecutor, MainThread
            mainThread, LocationRepository locationRepository, Callback
            callback, long id, Double latitude, Double longitude, Integer
            participants, String timeRemaining) {
        super(threadExecutor, mainThread);
        this.locationRepository = locationRepository;
        this.callback = callback;
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.participants = participants;
        this.timeRemaining = timeRemaining;
    }

    @Override
    public void run() {
        final String response = locationRepository.insert(id, participants, latitude, longitude, timeRemaining);
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onLocationAdded(response);
            }
        });
    }
}
