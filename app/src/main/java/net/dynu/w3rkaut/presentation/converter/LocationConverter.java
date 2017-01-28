package net.dynu.w3rkaut.presentation.converter;


import com.google.android.gms.maps.model.LatLng;

import net.dynu.w3rkaut.network.model.RESTLocation;
import net.dynu.w3rkaut.presentation.Model.Location;
import net.dynu.w3rkaut.utils.DistanceCalculator;

import java.util.ArrayList;
import java.util.List;

/**
 * This class convert the location POJO from the REST response and return a
 * location POJO
 *
 * @author Sergio Martin Rubio
 */
public class LocationConverter {

    public static List<Location> convertRESTLocationToLocation
            (List<RESTLocation> restLocationList, LatLng currentLatLng) {
        List<Location> locations = new ArrayList<>();

        if (restLocationList == null || restLocationList.isEmpty()) {
            return locations;
        }

        Location location;

        for (RESTLocation restLocation : restLocationList) {
            location = new Location();
            location.setImageUrl("https://graph.facebook.com/" + restLocation
                    .getUserId() + "/picture?type=large");
            location.setUserFirstName(restLocation.getUserFirstName());
            location.setUserLastName(restLocation.getUserLastName());

            Double distance = DistanceCalculator.CalcualteDistance(currentLatLng,
                    new LatLng(restLocation.getLatitude(),
                            restLocation.getLongitude()));

            location.setDistance(distance);
            location.setTimeRemaining(restLocation.getTimeRemaining().substring(0, 5));
            location.setPostedAt(restLocation.getPostedAt());

            locations.add(location);
        }

        return locations;
    }
}
