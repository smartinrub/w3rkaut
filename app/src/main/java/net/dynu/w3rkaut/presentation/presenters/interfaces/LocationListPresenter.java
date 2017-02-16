package net.dynu.w3rkaut.presentation.presenters.interfaces;

import net.dynu.w3rkaut.network.model.RESTLocation;

import java.util.List;

public interface LocationListPresenter {

    interface View {
        void onLocationsRetrieved(List<RESTLocation> locations);
    }

    void getAllLocations();
}
