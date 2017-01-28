package net.dynu.w3rkaut.network.converters;

import net.dynu.w3rkaut.network.model.RESTLocation;

/**
 * Class to get a location POJO for the REST service
 *
 * @author Sergio Martin Rubio
 */
public class RESTLocationConverter {
    public RESTLocation convertToRestModel(long id, Double
            latitude, Double longitude, String timeRemaining, String postedAt) {
        return new RESTLocation(id, latitude, longitude, timeRemaining,
                postedAt);
    }
}
