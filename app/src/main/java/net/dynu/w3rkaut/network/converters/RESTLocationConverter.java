package net.dynu.w3rkaut.network.converters;


import net.dynu.w3rkaut.domain.model.Location;
import net.dynu.w3rkaut.network.model.RESTLocation;

public class RESTLocationConverter {
    public RESTLocation convertToRestModel(Location location, Double
            latitude, Double longitude, String postedAt) {

        long id = Long.parseLong(location.getUserId());
        int participants = location.getParticipants();

        return new RESTLocation(id, latitude, longitude, postedAt,
                participants);
    }
}
