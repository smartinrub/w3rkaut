package net.dynu.w3rkaut.domain.interactors.interfaces;

import android.content.Context;

import net.dynu.w3rkaut.services.interfaces.LocationService;

public interface GetAllLocationsInteractor  {

    void getAllLocations(LocationService.VolleyCallback volleyCallback, Context context);
}
