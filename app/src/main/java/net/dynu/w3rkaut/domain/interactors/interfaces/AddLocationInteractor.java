package net.dynu.w3rkaut.domain.interactors.interfaces;


import android.content.Context;

import net.dynu.w3rkaut.services.interfaces.LocationService;

public interface AddLocationInteractor {

    void addLocation(long id,
                     Double latitude,
                     Double longitude,
                     String duration,
                     String postedAt,
                     LocationService.VolleyCallback volleyCallback,
                     Context context);
}
