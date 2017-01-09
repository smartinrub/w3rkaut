package net.dynu.w3rkaut.presentation.presenters.impl;

import net.dynu.w3rkaut.domain.executor.Executor;
import net.dynu.w3rkaut.domain.executor.MainThread;
import net.dynu.w3rkaut.domain.interactors.AddLocationInteractor;
import net.dynu.w3rkaut.domain.interactors.impl.AddLocationInteractorImpl;
import net.dynu.w3rkaut.domain.model.Location;
import net.dynu.w3rkaut.domain.respository.LocationRepository;
import net.dynu.w3rkaut.presentation.presenters.MainPresenter;
import net.dynu.w3rkaut.presentation.presenters.base.AbstractPresenter;



public class MainPresenterImpl extends AbstractPresenter implements
        MainPresenter, AddLocationInteractor.Callback {

    private LocationRepository locationRepository;

    public MainPresenterImpl(Executor executor, MainThread mainThread,
                             LocationRepository locationRepository) {
        super(executor, mainThread);
        this.locationRepository = locationRepository;
    }

    @Override
    public void addLocation(Location location) {
        AddLocationInteractor addLocationInteractor = new AddLocationInteractorImpl(
                mExecutor,
                mMainThread,
                locationRepository,
                this,
                location.getUserId(),
                0.0,
                0.0,
                location.getParticipants(),
                location.getPostedAt()
        );
        addLocationInteractor.execute();
    }

    @Override
    public String onLocationAdded() {
        return null;
    }
}
