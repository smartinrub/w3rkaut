package net.dynu.w3rkaut.presentation.ui.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
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

import net.dynu.w3rkaut.Permissions;
import net.dynu.w3rkaut.R;
import net.dynu.w3rkaut.domain.executor.impl.ThreadExecutor;
import net.dynu.w3rkaut.network.model.RESTLocation;
import net.dynu.w3rkaut.presentation.Model.Location;
import net.dynu.w3rkaut.presentation.converter.LocationConverter;
import net.dynu.w3rkaut.presentation.converter.LocationMapConverter;
import net.dynu.w3rkaut.presentation.presenters.LocationListPresenter;
import net.dynu.w3rkaut.presentation.presenters.impl.LocationListPresenterImpl;
import net.dynu.w3rkaut.presentation.ui.adapters.MapWindowAdapter;
import net.dynu.w3rkaut.storage.LocationRepositoryImpl;
import net.dynu.w3rkaut.threading.MainThreadImpl;
import net.dynu.w3rkaut.utils.LocationHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * This class displays the content for the map. It is the view
 * of the MVP pattern.
 *
 * @author Sergio Martin Rubio
 */
public class MapFragment extends BaseFragment implements OnMapReadyCallback,
        LocationListPresenter.View {

    private LocationHandler locationHandler;

    private Timer latLngTimer;

    private Double currLatitude;
    private Double currLongitude;

    private GoogleMap googleMap;

    private List<RESTLocation> locations;

    private Timer listTimer;
    private HashMap<String, Location> locationHashMap;

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
//        AdView mAdView = (AdView) rootView.findViewById(R.id.adViewMap);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
        getCurrentLocation();

        initMap(savedInstanceState, rootView);
        return rootView;
    }

    private void initMap(Bundle savedInstanceState, View view) {
        MapView mapView = (MapView) view.findViewById(R.id.map_view);
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

        LocationListPresenterImpl presenter = new LocationListPresenterImpl
                (ThreadExecutor.getInstance(), MainThreadImpl.getInstance(),
                        this,
                        new LocationRepositoryImpl(getActivity()));
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
                Activity activity = getActivity();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setupMap();
                            hideProgress();
                        }
                    });
                }
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
        BitmapDrawable bitmapDrawable = (BitmapDrawable) ContextCompat.getDrawable(getApplicationContext(),
                R.drawable.ic_fitness_center_black_48dp);
        Bitmap b = bitmapDrawable.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 80, 80, false);
        for (final RESTLocation location : locations) {
            googleMap.addMarker(new MarkerOptions()
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
