package net.dynu.w3rkaut.domain.interactors.interfaces;


import android.content.Context;

import net.dynu.w3rkaut.services.interfaces.LocationService;

public interface DeleteLocationInteractor {

    void deleteLocation(long userId, final LocationService.VolleyCallback callback, Context context);
}
