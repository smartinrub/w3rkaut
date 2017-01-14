package net.dynu.w3rkaut.domain.interactors.impl;


import net.dynu.w3rkaut.domain.executor.Executor;
import net.dynu.w3rkaut.domain.executor.MainThread;
import net.dynu.w3rkaut.domain.interactors.DeleteLocationInteractor;
import net.dynu.w3rkaut.domain.interactors.base.AbstractInteractor;
import net.dynu.w3rkaut.domain.respository.LocationRepository;

public class DeleteLocationInteractorImpl extends AbstractInteractor
        implements DeleteLocationInteractor {

    private Callback callback;
    private LocationRepository locationRepository;

    private long userId;

    public DeleteLocationInteractorImpl(Executor threadExecutor, MainThread
            mainThread, LocationRepository locationRepository, Callback
            callback, long userId) {
        super(threadExecutor, mainThread);
        this.locationRepository = locationRepository;
        this.callback = callback;
        this.userId = userId;
    }

    @Override
    public void run() {
        final String response = locationRepository.delete(userId);
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onLocationDeleted(response);
            }
        });
    }
}
