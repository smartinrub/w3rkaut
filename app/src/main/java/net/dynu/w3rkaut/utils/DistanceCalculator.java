package net.dynu.w3rkaut.utils;

/**
 * This uses Haversine formula to calculate great-circle
 * distences between two points on a sphere from their
 * longitudes and latitudes
 *
 * @author Sergio Martin Rubio
 */
public class DistanceCalculator {
    private final static int RADUIS_IN_KM = 6371;

    /**
     *
     * @param startLat start latitude position
     * @param startLng start longitude position
     * @param endLat end latitude position
     * @param endLng end longitude position
     * @return distance in metres
     */
    public static double CalcualteDistance (Double startLat, Double
            startLng, Double endLat, Double endLng){
        double latDistance = Math.toRadians(endLat - startLat);
        double lngDistance = Math.toRadians(endLng - startLng);
        double a = Math.pow(Math.sin(latDistance/2), 2)
                + Math.pow(Math.sin(lngDistance/2), 2)
                * Math.cos(Math.toRadians(startLat))
                * Math.cos(Math.toRadians(endLat));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return RADUIS_IN_KM * c * 1000;
    }
}
