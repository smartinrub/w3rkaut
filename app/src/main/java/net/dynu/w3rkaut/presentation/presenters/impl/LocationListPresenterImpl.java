package net.dynu.w3rkaut.presentation.presenters.impl;

import android.content.Context;

import net.dynu.w3rkaut.domain.interactors.GetAllLocationsInteractor;
import net.dynu.w3rkaut.domain.interactors.impl.GetAllLocationsInteractorImpl;
import net.dynu.w3rkaut.network.model.RESTLocation;
import net.dynu.w3rkaut.presentation.presenters.LocationListPresenter;

import java.util.List;

/**
 * Presenter implementation which acts like a bridge between the interactors
 * for the recyclerview and the recyclerview fragment
 *
 * @author Sergio Martin Rubio
 */
public class LocationListPresenterImpl implements
        LocationListPresenter, GetAllLocationsInteractor.Callback{

    private Context context;

    private LocationListPresenter.View view;

    public LocationListPresenterImpl(View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void getAllLocations() {
        GetAllLocationsInteractor interactor = new
                GetAllLocationsInteractorImpl();
        interactor.getAllLocation(this, context);
    }

    @Override
    public void onLocationsRetrieved(List<RESTLocation> locationList) {
        view.onLocationsRetrieved(locationList);
    }
}
