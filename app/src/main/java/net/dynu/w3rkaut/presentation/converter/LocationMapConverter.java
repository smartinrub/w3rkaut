package net.dynu.w3rkaut.presentation.converter;

import net.dynu.w3rkaut.presentation.Model.Location;

import java.util.HashMap;
import java.util.List;

public class LocationMapConverter {

    public static HashMap<String, Location> getMapById (List<Location> locations) {
        HashMap<String, Location> locationHashMap = new HashMap<>();

        for (Location location: locations) {
            locationHashMap.put(location.getImageUrl(), location);
        }

        return locationHashMap;
    }
}
