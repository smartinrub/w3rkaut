package net.dynu.w3rkaut.domain.interactors.impl;

import android.content.Context;

import net.dynu.w3rkaut.domain.interactors.interfaces.GetAllLocationsInteractor;
import net.dynu.w3rkaut.services.impl.LocationServiceImpl;
import net.dynu.w3rkaut.services.interfaces.LocationService;

/**
 * This class contains the business logic of getting all locations
 *
 * @author Sergio Martin Rubio
 */
public class GetAllLocationsInteractorImpl implements GetAllLocationsInteractor {

    @Override
    public void getAllLocation(final LocationService.VolleyCallback callback, Context context) {
        LocationService locationService = new LocationServiceImpl(callback, context);
        locationService.getAll();
    }
}
