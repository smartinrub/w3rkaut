package net.dynu.w3rkaut.domain.interactors.impl;

import android.content.Context;

import net.dynu.w3rkaut.domain.interactors.interfaces.DeleteLocationInteractor;
import net.dynu.w3rkaut.services.impl.LocationServiceImpl;
import net.dynu.w3rkaut.services.interfaces.LocationService;

/**
 * This class contains the business logic of deleting a location
 *
 * @author Sergio Martin Rubio
 */
public class DeleteLocationInteractorImpl implements DeleteLocationInteractor {

    @Override
    public void deleteLocation(final long userId,
                               final LocationService.VolleyCallback callback,
                               Context context) {

        LocationServiceImpl locationService =
                new LocationServiceImpl(callback, context);
        locationService.delete(userId);
    }
}
