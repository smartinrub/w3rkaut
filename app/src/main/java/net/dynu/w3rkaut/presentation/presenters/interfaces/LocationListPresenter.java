package net.dynu.w3rkaut.presentation.presenters.interfaces;

import net.dynu.w3rkaut.domain.model.Location;

import java.util.List;

public interface LocationListPresenter {

    interface View {
        void onLocationsRetrieved(List<Location> locations);

        void onConnectionFailed(String message);
    }

    void getAllLocations();
}
