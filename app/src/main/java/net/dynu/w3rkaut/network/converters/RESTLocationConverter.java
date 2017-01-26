package net.dynu.w3rkaut.network.converters;

import net.dynu.w3rkaut.network.model.RESTLocation;

public class RESTLocationConverter {
    public RESTLocation convertToRestModel(long id, Double
            latitude, Double longitude, String timeRemaining, String postedAt) {
        return new RESTLocation(id, latitude, longitude, timeRemaining,
                postedAt);
    }
}
