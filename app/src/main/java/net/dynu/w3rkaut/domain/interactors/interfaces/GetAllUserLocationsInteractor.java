package net.dynu.w3rkaut.domain.interactors.interfaces;

import android.content.Context;

import net.dynu.w3rkaut.services.interfaces.LocationService;

public interface GetAllUserLocationsInteractor {

    void getAllUserLocations(long userId, LocationService.VolleyCallback
            volleyCallback, Context context);

}
