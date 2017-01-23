package net.dynu.w3rkaut.presentation.ui.fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import net.dynu.w3rkaut.Permissions;
import net.dynu.w3rkaut.R;
import net.dynu.w3rkaut.domain.executor.impl.ThreadExecutor;
import net.dynu.w3rkaut.domain.interactors.base.Interactor;
import net.dynu.w3rkaut.network.model.RESTLocation;
import net.dynu.w3rkaut.presentation.Model.Location;
import net.dynu.w3rkaut.presentation.converter.LocationConverter;
import net.dynu.w3rkaut.presentation.converter.LocationMapConverter;
import net.dynu.w3rkaut.presentation.presenters.LocationListPresenter;
import net.dynu.w3rkaut.presentation.presenters.impl.LocationListPresenterImpl;
import net.dynu.w3rkaut.presentation.ui.adapters.MapWindowAdapter;
import net.dynu.w3rkaut.storage.LocationRepositoryImpl;
import net.dynu.w3rkaut.storage.session.SharedPreferencesManager;
import net.dynu.w3rkaut.threading.MainThreadImpl;
import net.dynu.w3rkaut.utils.LocationHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import timber.log.Timber;

public class MapFragment extends BaseFragment implements OnMapReadyCallback,
        LocationListPresenter.View {

    private MapView mapView;
    private LocationHandler locationHandler;

    private Timer latLngTimer;

    private LocationListPresenterImpl presenter;

    private Double currLatitude;
    private Double currLongitude;

    private GoogleMap googleMap;

    private List<RESTLocation> locations;

    private Timer listTimer;
    private HashMap<String, Location> locationHashMap;
    private Marker marker;

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
        getCurrentLocation();

        initMap(savedInstanceState, rootView);
        return rootView;
    }

    private void initMap(Bundle savedInstanceState, View view) {
        mapView = (MapView) view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
    }

    public void getCurrentLocation() {
        locationHandler = LocationHandler.getInstance(getActivity());
        latLngTimer = new Timer();
        latLngTimer.schedule(new MapFragment.GetCurrentLocationTask(), 0, 50);
    }

    @Override
    public void showLocations(List<RESTLocation> locations) {
        this.locations = locations;
        showProgress();
        listTimer = new Timer();
        listTimer.schedule(new SetAllLocationsTask(), 200, 50);
    }

    class GetCurrentLocationTask extends TimerTask {
        @Override
        public void run() {
            Double latitude = locationHandler.getLatitude();
            Double longitude = locationHandler.getLongitude();
            if (latitude != null && longitude != null) {
                currLatitude = latitude;
                currLongitude = longitude;
                latLngTimer.cancel();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        init();
                    }
                });
            }
        }
    }

    private void init() {

        presenter = new LocationListPresenterImpl
                (ThreadExecutor.getInstance(), MainThreadImpl.getInstance(),
                        this,
                        new LocationRepositoryImpl(getActivity()), new LatLng
                        (currLatitude, currLongitude));
        presenter.getAllLocations();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        MapStyleOptions style = MapStyleOptions
                .loadRawResourceStyle(getActivity(), R.raw.style_json);
        googleMap.setMapStyle(style);
        Permissions permissions = new Permissions(getActivity());
        permissions.checkLocationPermission();
        googleMap.setMyLocationEnabled(true);
    }

    class SetAllLocationsTask extends TimerTask {

        @Override
        public void run() {
            if (locations != null) {
                List<Location> newList = LocationConverter
                        .convertRESTLocationToLocation(locations, new LatLng
                                (currLatitude, currLongitude));
                locationHashMap = LocationMapConverter
                        .getMapById(newList);

                listTimer.cancel();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setupMap();
                        hideProgress();
                    }
                });
            }
        }
    }

    private void setupMap() {
        googleMap.setInfoWindowAdapter(new MapWindowAdapter(locationHashMap,
                getActivity().getLayoutInflater()));
        createMarkers();
        CameraPosition cameraPosition = new CameraPosition
                .Builder()
                .target(new LatLng(currLatitude, currLongitude))
                .zoom(14)
                .build();
        googleMap.addCircle(new CircleOptions()
                .center(new LatLng(currLatitude, currLongitude))
                .radius(800)
                .strokeColor(0x80000000)
                .fillColor(0x55C5E3BF)
                .strokeWidth(2));
        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
        UiSettings uiSettings = googleMap.getUiSettings();
        uiSettings.setMyLocationButtonEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
    }

    public void createMarkers() {
        for (final RESTLocation location : locations) {
            marker = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(location.getLatitude(), location
                            .getLongitude()))
                    .title(location.getUserFirstName())
                    .snippet("https://graph.facebook.com/" +
                            location.getUserId() + "/picture?type=large"));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        locationHandler.getGoogleApiClient().disconnect();
        Permissions permissions = new Permissions(getActivity());
        permissions.checkLocationPermission();
        googleMap.setMyLocationEnabled(false);
    }

    @Override
    public void onStop() {
        super.onStop();
        locationHandler.getGoogleApiClient().disconnect();
        Permissions permissions = new Permissions(getActivity());
        permissions.checkLocationPermission();
        googleMap.setMyLocationEnabled(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        locationHandler.getGoogleApiClient().connect();
    }
}
