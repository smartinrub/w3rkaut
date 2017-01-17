package net.dynu.w3rkaut.domain.interactors;

import net.dynu.w3rkaut.network.model.RESTLocation;

import java.util.List;

public interface GetAllLocationsInteractor {

    interface Callback {
        void onLocationsRetrieved(List<RESTLocation> locationList);
        void onEmptyList(String message);
    }

    void getAllLocations();

}
