package net.dynu.w3rkaut.presentation.presenters.impl;


import net.dynu.w3rkaut.domain.executor.Executor;
import net.dynu.w3rkaut.domain.executor.MainThread;
import net.dynu.w3rkaut.domain.interactors.GetAllLocationsInteractor;
import net.dynu.w3rkaut.domain.interactors.impl.GetAllLocationsInteractorImpl;
import net.dynu.w3rkaut.domain.respository.LocationRepository;
import net.dynu.w3rkaut.network.model.RESTLocation;
import net.dynu.w3rkaut.presentation.presenters.LocationListPresenter;
import net.dynu.w3rkaut.presentation.presenters.base.AbstractPresenter;

import java.util.List;

/**
 * Presenter implementation which acts like a bridge between the interactors
 * for the recyclerview and the recyclerview fragment
 *
 * @author Sergio Martin Rubio
 */
public class LocationListPresenterImpl extends AbstractPresenter implements
        LocationListPresenter, GetAllLocationsInteractor.Callback{

    private LocationRepository locationRepository;
    private LocationListPresenter.View view;

    public LocationListPresenterImpl(Executor executor, MainThread
            mainThread, View view, LocationRepository locationRepository) {
        super(executor, mainThread);
        this.view = view;
        this.locationRepository = locationRepository;
    }

    @Override
    public void getAllLocations() {
        GetAllLocationsInteractor interactor = new
                GetAllLocationsInteractorImpl(mExecutor, mMainThread,
                locationRepository, this);
        interactor.execute();
    }

    @Override
    public void onLocationsRetrieved(List<RESTLocation> locationList) {
        view.showLocations(locationList);
    }
}
