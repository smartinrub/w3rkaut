package net.dynu.w3rkaut.utils;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import net.dynu.w3rkaut.Permissions;

/**
 * This class initialize the Google Maps API
 */
public class LocationHandler extends FragmentActivity implements GoogleApiClient
        .ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static LocationHandler instance;

    private static Context context;
    private static GoogleApiClient googleApiClient;

    private Double latitude;
    private Double longitude;
    private Location lastLocation;

    public LocationHandler(Context context) {
        this.context = context;
        buildGoogleApiClient();
    }

    private void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    private void getMostRecentLocation() {
        Permissions permissions = new Permissions(context);
        permissions.checkLocationPermission();
        lastLocation =  LocationServices.FusedLocationApi.getLastLocation
                (googleApiClient);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        getMostRecentLocation();
        if(lastLocation != null) {
            latitude = lastLocation.getLatitude();
            longitude = lastLocation.getLongitude();
        }
    }

    public static LocationHandler getInstance(Context context) {
        if(instance == null) {
            instance = new LocationHandler(context);
        }
        return instance;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public GoogleApiClient getGoogleApiClient() {
        return googleApiClient;
    }
}
