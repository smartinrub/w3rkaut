package net.dynu.w3rkaut.utils;

import com.google.android.gms.maps.model.LatLng;

/**
 * This uses Haversine formula to calculate great-circle
 * distences between two points on a sphere from their
 * longitudes and latitudes
 *
 * @author Sergio Martin Rubio
 */
public class DistanceCalculator {
    private final static int RADUIS_IN_KM = 6371;


    public static double CalcualteDistance (LatLng latLngStart, LatLng
            latLngEnd){
        double latStart = latLngStart.latitude;
        double latEnd = latLngEnd.latitude;
        double lngStart = latLngStart.longitude;
        double lngEnd = latLngEnd.longitude;
        double latDistance = Math.toRadians(latEnd - latStart);
        double lngDistance = Math.toRadians(lngEnd - lngStart);
        double a = Math.pow(Math.sin(latDistance/2), 2) + Math.pow(Math.sin(lngDistance/2), 2)
                * Math.cos(Math.toRadians(latStart)) * Math.cos(Math.toRadians(latEnd));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return RADUIS_IN_KM * c;
    }
}