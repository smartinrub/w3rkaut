package net.dynu.w3rkaut.presentation.converter;

import net.dynu.w3rkaut.presentation.model.Location;

import java.util.HashMap;
import java.util.List;

public class LocationsById {

    public static HashMap<String, Location> getMapById(List<Location> locations) {
        HashMap<String, Location> locationHashMap = new HashMap<>();

        for (Location location : locations) {
            locationHashMap.put(location.getImageUrl(), location);
        }

        return locationHashMap;
    }
}
