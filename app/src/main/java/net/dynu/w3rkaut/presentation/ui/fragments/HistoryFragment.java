package net.dynu.w3rkaut.presentation.ui.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import net.dynu.w3rkaut.R;
import net.dynu.w3rkaut.domain.model.LocationRest;
import net.dynu.w3rkaut.presentation.presenters.impl.UserLocationsPresenterImpl;
import net.dynu.w3rkaut.presentation.presenters.interfaces.UserLocationsPresenter;

import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class HistoryFragment extends Fragment implements OnMapReadyCallback,
        UserLocationsPresenter.View {

    private static final String TAG = LocationInfoFragment.class
            .getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_MAPS = 1;

    private GoogleMap mGoogleMap;
    private GoogleApiClient mGoogleApiClient;

    private List<LocationRest> locations;
    private MapView mapView;

    public HistoryFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history,
                container, false);

        Bundle args = getArguments();
        String[] url = args.getString("url").split("/");
        long userId = Long.parseLong(url[3]);

        mapView = (MapView) rootView.findViewById(R.id.map_view_history_fragment);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addApi(LocationServices.API)
                    .build();
        }

        UserLocationsPresenterImpl presenter = new UserLocationsPresenterImpl
                (this, getActivity());
        presenter.getAllUserLocations(userId);

        return rootView;
    }

    @Override
    public void onLocationsRetrieved(List<LocationRest> locations) {
        this.locations = locations;
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

        createMarkers();
    }

    public void createMarkers() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) ContextCompat.getDrawable(getApplicationContext(),
                R.drawable.ic_beenhere_black_24dp);
        Bitmap b = bitmapDrawable.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 80, 80, false);
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        if (locations != null) {
            for (final LocationRest location : locations) {
                LatLng latLng = new LatLng(location.getLatitude(), location
                        .getLongitude());
                mGoogleMap.addCircle(new CircleOptions()
                        .center(latLng)
                        .radius(100)
                        .strokeColor(0x80000000)
                        .fillColor(0x8CC5E3BF)
                        .strokeWidth(3));

                mGoogleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .icon(BitmapDescriptorFactory.
                                fromBitmap(smallMarker)));
                builder.include(latLng);
            }

            try {
                LatLngBounds bounds = builder.build();
                int width = getResources().getDisplayMetrics().widthPixels;
                int height = getResources().getDisplayMetrics().heightPixels;
                int padding = (int) (width * 0.10); // offset from edges of the map 12% of screen
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
                mGoogleMap.animateCamera(cu);
            } catch (IllegalStateException e) {
                Log.e(TAG, "No locations found");
            }
        }
    }
}
