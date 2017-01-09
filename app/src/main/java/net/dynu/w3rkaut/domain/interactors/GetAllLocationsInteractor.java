package net.dynu.w3rkaut.domain.interactors;

import net.dynu.w3rkaut.domain.interactors.base.Interactor;
import net.dynu.w3rkaut.network.model.RESTLocation;

import java.util.List;

/**
 * Created by sergio on 09/01/2017.
 */

public interface GetAllLocationsInteractor extends Interactor {

    interface Callback {

        void onLocationsRetrieved(List<RESTLocation> locationList);
    }
}
