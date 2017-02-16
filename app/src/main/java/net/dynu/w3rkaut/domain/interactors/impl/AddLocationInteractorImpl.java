package net.dynu.w3rkaut.domain.interactors.impl;

import android.content.Context;

import net.dynu.w3rkaut.domain.interactors.interfaces.AddLocationInteractor;
import net.dynu.w3rkaut.network.model.RESTLocation;
import net.dynu.w3rkaut.services.impl.LocationServiceImpl;
import net.dynu.w3rkaut.services.interfaces.LocationService;

/**
 * This class contains the business logic of adding a new location
 *
 * @author Sergio Martin Rubio
 */
public class AddLocationInteractorImpl implements AddLocationInteractor {

    @Override
    public void addLocation(final long userId,
                            final Double latitude,
                            final Double longitude,
                            final String duration,
                            final String postedAt,
                            final LocationService.VolleyCallback callback,
                            Context context) {

        LocationServiceImpl locationService = new LocationServiceImpl(callback, context);

        RESTLocation location =
                new RESTLocation(userId, latitude, longitude, duration, postedAt);

        locationService.insert(location);
    }
}
