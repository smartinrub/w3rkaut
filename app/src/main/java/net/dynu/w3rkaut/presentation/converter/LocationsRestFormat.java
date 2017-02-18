package net.dynu.w3rkaut.presentation.converter;


import net.dynu.w3rkaut.domain.model.LocationRest;
import net.dynu.w3rkaut.presentation.model.Location;
import net.dynu.w3rkaut.utils.DistanceCalculator;

import java.util.ArrayList;
import java.util.List;

/**
 * This class convert the location POJO from the REST response and return a
 * location POJO
 *
 * @author Sergio Martin Rubio
 */
public class LocationsRestFormat {

    public static List<Location> convertRESTLocationToLocation
            (List<LocationRest> locationList, Double lat, Double lng) {
        List<Location> locations = new ArrayList<>();

        if (locationList == null || locationList.isEmpty()) {
            return locations;
        }

        Location location;

        for (LocationRest restLocation : locationList) {
            location = new Location();
            location.setImageUrl("https://graph.facebook.com/" + restLocation
                    .getUserId() + "/picture?type=large");
            location.setUserFirstName(restLocation.getUserFirstName());
            location.setUserLastName(restLocation.getUserLastName());

            Double distance = DistanceCalculator.CalcualteDistance(lat, lng,
                    restLocation.getLatitude(), restLocation.getLongitude());

            location.setDistance(distance);
            location.setTimeRemaining(restLocation.getTimeRemaining().substring(0, 5));
            location.setPostedAt(restLocation.getPostedAt());
            location.setLatitude(restLocation.getLatitude());
            location.setLongitude(restLocation.getLongitude());

            locations.add(location);
        }

        return locations;
    }
}
