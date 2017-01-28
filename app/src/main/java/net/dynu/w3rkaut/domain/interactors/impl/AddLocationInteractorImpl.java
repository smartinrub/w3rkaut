package net.dynu.w3rkaut.domain.interactors.impl;

import net.dynu.w3rkaut.domain.executor.Executor;
import net.dynu.w3rkaut.domain.executor.MainThread;
import net.dynu.w3rkaut.domain.interactors.AddLocationInteractor;
import net.dynu.w3rkaut.domain.interactors.base.AbstractInteractor;
import net.dynu.w3rkaut.domain.respository.LocationRepository;

/**
 * This class contains the business logic of adding a new location
 *
 * @author Sergio Martin Rubio
 */
public class AddLocationInteractorImpl extends AbstractInteractor implements
        AddLocationInteractor {

    private Callback callback;
    private LocationRepository locationRepository;

    private long id;
    private Double latitude;
    private Double longitude;
    private String timeRemaining;
    private String postedAt;

    public AddLocationInteractorImpl(Executor threadExecutor,
                                     MainThread mainThread,
                                     LocationRepository locationRepository,
                                     Callback callback,
                                     long id,
                                     Double latitude,
                                     Double longitude,
                                     String timeRemaining,
                                     String postedAt) {
        super(threadExecutor, mainThread);
        this.locationRepository = locationRepository;
        this.callback = callback;
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeRemaining = timeRemaining;
        this.postedAt = postedAt;
    }

    @Override
    public void run() {
        final String response = locationRepository.insert(id, latitude,
                longitude, timeRemaining, postedAt);
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onLocationAdded(response);
            }
        });
    }
}
