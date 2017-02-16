package net.dynu.w3rkaut.presentation.converter;


import net.dynu.w3rkaut.domain.model.Location;
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

    public static List<net.dynu.w3rkaut.presentation.Model.Location> convertRESTLocationToLocation
            (List<Location> locationList, Double lat, Double lng) {
        List<net.dynu.w3rkaut.presentation.Model.Location> locations = new ArrayList<>();

        if (locationList == null || locationList.isEmpty()) {
            return locations;
        }

        net.dynu.w3rkaut.presentation.Model.Location location;

        for (Location restLocation : locationList) {
            location = new net.dynu.w3rkaut.presentation.Model.Location();
            location.setImageUrl("https://graph.facebook.com/" + restLocation
                    .getUserId() + "/picture?type=large");
            location.setUserFirstName(restLocation.getUserFirstName());
            location.setUserLastName(restLocation.getUserLastName());

            Double distance = DistanceCalculator.CalcualteDistance(lat, lng,
                    restLocation.getLatitude(), restLocation.getLongitude());

            location.setDistance(distance);
            location.setTimeRemaining(restLocation.getTimeRemaining().substring(0, 5));
            location.setPostedAt(restLocation.getPostedAt());

            locations.add(location);
        }

        return locations;
    }
}
