package net.dynu.w3rkaut.domain.interactors.interfaces;

import android.content.Context;

import net.dynu.w3rkaut.network.model.RESTLocation;
import net.dynu.w3rkaut.services.interfaces.LocationService;

import java.util.List;

public interface GetAllLocationsInteractor  {

    void getAllLocation(LocationService.VolleyCallback volleyCallback, Context context);
}
