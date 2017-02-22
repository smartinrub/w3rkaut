package net.dynu.w3rkaut.domain.interactors.impl;

import android.content.Context;

import net.dynu.w3rkaut.domain.interactors.interfaces.GetAllUserLocationsInteractor;
import net.dynu.w3rkaut.services.impl.LocationServiceImpl;
import net.dynu.w3rkaut.services.interfaces.LocationService;

public class GetAllUserLocationsInteractorImpl implements GetAllUserLocationsInteractor {

    @Override
    public void getAllUserLocations(long userId, LocationService
            .VolleyCallback callback, Context context) {
        LocationService locationService = new LocationServiceImpl(callback, context);
        locationService.getUserLocations(userId);
    }
}
