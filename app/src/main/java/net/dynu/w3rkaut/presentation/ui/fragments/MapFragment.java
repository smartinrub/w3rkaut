package net.dynu.w3rkaut.presentation.ui.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import net.dynu.w3rkaut.network.model.RESTLocation;
import net.dynu.w3rkaut.presentation.Model.Location;
import net.dynu.w3rkaut.presentation.converter.LocationsRestFormat;
import net.dynu.w3rkaut.presentation.converter.LocationsById;
import net.dynu.w3rkaut.presentation.presenters.LocationListPresenter;
import net.dynu.w3rkaut.presentation.presenters.impl.LocationListPresenterImpl;
import net.dynu.w3rkaut.presentation.ui.adapters.MapWindowAdapter;
import net.dynu.w3rkaut.utils.SimpleLocation;

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
        LocationListPresenter.View {

    private Double currLat;
    private Double currLng;

    private GoogleMap googleMap;

    private List<RESTLocation> locations;

    private HashMap<String, Location> locationsById;
    private AdView mAdView;
    private LocationListPresenterImpl presenter;

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

        getCurrentLocation();
        presenter = new LocationListPresenterImpl
                (this, getActivity());
        presenter.getAllLocations();
        initMap(savedInstanceState, rootView);

        return rootView;
    }

    private void initMap(Bundle savedInstanceState, View view) {
        MapView mapView = (MapView) view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
    }

    private void getCurrentLocation() {
        SimpleLocation mLocation = new SimpleLocation(getActivity());
        if (!mLocation.hasLocationEnabled()) {
            SimpleLocation.openSettings(getActivity());
        }
        currLat = mLocation.getLatitude();
        currLng = mLocation.getLongitude();
    }

    @Override
    public void onLocationsRetrieved(List<RESTLocation> locations) {
        this.locations = locations;
        List<Location> newList = LocationsRestFormat.convertRESTLocationToLocation(locations, currLat, currLng);
        locationsById = LocationsById.getMapById(newList);
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
        if(locations != null) {
            setupMap();
        }
    }

    private void setupMap() {
        googleMap.setInfoWindowAdapter(new MapWindowAdapter(locationsById,
                getActivity().getLayoutInflater()));
        createMarkers();
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
        Permissions permissions = new Permissions(getActivity());
        permissions.checkLocationPermission();
        googleMap.setMyLocationEnabled(false);
        mAdView.pause();
    }

    @Override
    public void onStop() {
        super.onStop();
        Permissions permissions = new Permissions(getActivity());
        permissions.checkLocationPermission();
        googleMap.setMyLocationEnabled(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdView.resume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdView.destroy();
    }
}
