package net.dynu.w3rkaut.presentation.ui.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import net.dynu.w3rkaut.R;
import net.dynu.w3rkaut.domain.model.Location;
import net.dynu.w3rkaut.presentation.converter.LocationsById;
import net.dynu.w3rkaut.presentation.converter.LocationsRestFormat;
import net.dynu.w3rkaut.presentation.presenters.interfaces.LocationListPresenter;
import net.dynu.w3rkaut.presentation.presenters.impl.LocationListPresenterImpl;
import net.dynu.w3rkaut.presentation.ui.adapters.MapWindowAdapter;

import java.util.HashMap;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * This class displays the content for the map. It is the view
 * of the MVP pattern.
 *
 * @author Sergio Martin Rubio
 */
public class MapFragment extends Fragment implements OnMapReadyCallback,
        LocationListPresenter.View, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int MY_PERMISSIONS_REQUEST_MAPS = 1;
    private static final String TAG = MapFragment.class.getSimpleName();

    private Double currLat;
    private Double currLng;

    private GoogleMap mGoogleMap;

    private List<Location> locations;

    private HashMap<String, net.dynu.w3rkaut.presentation.Model.Location> locationsById;
    private AdView mAdView;
    private MapView mapView;
    private GoogleApiClient mGoogleApiClient;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.removeItem(R.id.action_map);
        menu.removeItem(R.id.action_delete_location);
        menu.removeItem(R.id.action_refresh);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_map, container,
                false);

        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);

        mAdView = (AdView) rootView.findViewById(R.id.adViewMap);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mapView = (MapView) rootView.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();


        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        return rootView;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission
                .ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_MAPS);
            return;
        }
        android.location.Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            currLat = mLastLocation.getLatitude();
            currLng = mLastLocation.getLongitude();
            LocationListPresenterImpl presenter = new LocationListPresenterImpl(this, getActivity());
            presenter.getAllLocations();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "Google API connection failed");
    }

    @Override
    public void onLocationsRetrieved(List<Location> locations) {
        this.locations = locations;
        List<net.dynu.w3rkaut.presentation.Model.Location> newList = LocationsRestFormat.convertRESTLocationToLocation(locations, currLat, currLng);
        locationsById = LocationsById.getMapById(newList);
        mapView.getMapAsync(this);
    }

    @Override
    public void onConnectionFailed(String message) {
        if (message.indexOf("NoConnectionError") > 0) {
            Toast toast = Toast.makeText(getActivity(), R.string.connection_error,
                    Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, 300);
            toast.show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        MapStyleOptions style = MapStyleOptions
                .loadRawResourceStyle(getActivity(), R.raw.style_json);
        googleMap.setMapStyle(style);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission
                .ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission
                .ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_MAPS);
            return;
        }
        googleMap.setMyLocationEnabled(true);

        googleMap.setInfoWindowAdapter(new MapWindowAdapter(locationsById,
                getActivity().getLayoutInflater()));

        CameraPosition cameraPosition = new CameraPosition
                .Builder()
                .target(new LatLng(currLat, currLng))
                .zoom(14)
                .build();

        googleMap.addCircle(new CircleOptions()
                .center(new LatLng(currLat, currLng))
                .radius(800)
                .strokeColor(0x80000000)
                .fillColor(0x55C5E3BF)
                .strokeWidth(2));

        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setZoomControlsEnabled(true);

        createMarkers();
    }

    public void createMarkers() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) ContextCompat.getDrawable(getApplicationContext(),
                R.drawable.ic_fitness_center_black_48dp);
        Bitmap b = bitmapDrawable.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 80, 80, false);
        for (final Location location : locations) {
            mGoogleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(location.getLatitude(), location
                            .getLongitude()))
                    .title(location.getUserFirstName())
                    .snippet("https://graph.facebook.com/" +
                            location.getUserId() + "/picture?type=large")
                    .icon(BitmapDescriptorFactory.
                            fromBitmap(smallMarker)));
        }
    }

    @Override
    public void onPause() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission
                .ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission
                .ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_MAPS);
            return;
        }
        if (mGoogleMap != null) {
            mGoogleMap.setMyLocationEnabled(false);
        }
        mAdView.pause();
        super.onPause();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission
                .ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission
                .ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_MAPS);
            return;
        }
        if (mGoogleMap != null) {
            mGoogleMap.setMyLocationEnabled(false);
        }
        super.onStop();

    }

    @Override
    public void onResume() {
        mAdView.resume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mAdView.destroy();
        super.onDestroy();
    }

    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }
}
