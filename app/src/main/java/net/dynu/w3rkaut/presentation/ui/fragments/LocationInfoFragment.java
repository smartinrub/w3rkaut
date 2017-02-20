package net.dynu.w3rkaut.presentation.ui.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import net.dynu.w3rkaut.R;
import net.dynu.w3rkaut.databinding.FragmentLocationInfoBinding;
import net.dynu.w3rkaut.presentation.model.Location;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static com.facebook.FacebookSdk.getApplicationContext;

public class LocationInfoFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = LocationInfoFragment.class
            .getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_MAPS = 1;

    private Location location;

    private GoogleMap mGoogleMap;
    private GoogleApiClient mGoogleApiClient;

    public LocationInfoFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final FragmentLocationInfoBinding binding = DataBindingUtil.inflate
                (inflater, R.layout.fragment_location_info, container, false);
        View rootView = binding.getRoot();
        Bundle args = getArguments();
        location = new Location(args.getString("url"), args
                .getString("first_name"), args.getString("last_name"), args
                .getString("time_remaining"), args.getDouble("distance"),
                args.getString("posted_at"), args.getDouble("latitude"), args
                .getDouble("longitude"));
        final TextView tvCountDown = (TextView) rootView.findViewById(R.id
                .tv_count_down);

        binding.tvFirstName.setText(location.getUserFirstName());

        Picasso.with(getActivity())
                .load("https://maps.googleapis" +
                        ".com/maps/api/streetview?size=800x400&location="
                        + location.getLatitude() + "," + location
                        .getLongitude() +"&heading=191" +
                        ".78&pitch=-0" +
                        ".76&key=AIzaSyBJVaWzG5vXrpJ_hxeqwETsX-KqF-E6j0g")
                .error(R.drawable.ic_map_black_24dp)
                .into(binding.ivLocation);


        binding.tvNameInfo.setText(location.getUserFirstName());
        binding.ivNameIcon.setImageResource(R.drawable.ic_face_black_24dp);
        binding.tvLocationInfo.setText(getAddress(location.getLatitude(), location
                .getLongitude()));
        binding.ivLocationIcon.setImageResource(R.drawable
                .ic_person_pin_circle_black_24dp);
        binding.tvPostedAtInfo.setText(location.getPostedAt());
        binding.ivPostedAtIcon.setImageResource(R.drawable.ic_access_time_black_24dp);
        binding.tvDistanceInfo.setText(String.format(Locale.ITALY, "%f", location
                .getDistance()));
        binding.ivDistanceIcon.setImageResource(R.drawable.ic_directions_walk_black_24dp);

        Picasso.with(getActivity())
                .load(location.getImageUrl())
                .error(R.drawable.ic_face_black_24dp)
                .into(binding.ivProfilePicInfo);

        int seconds = getSeconds(
                Integer.parseInt(location.getTimeRemaining().substring(6, 8)),
                Integer.parseInt(location.getTimeRemaining().substring(3, 5)),
                Integer.parseInt(location.getTimeRemaining().substring(0, 2)));


        new CountDownTimer(seconds * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                tvCountDown.setText(String.format(Locale.ITALY,
                        "%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                                - TimeUnit.HOURS.toMinutes(TimeUnit
                                .MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                tvCountDown.setText("done!");
            }
        }.start();

        MapView mapView = (MapView) rootView.findViewById(R.id.map_view_info);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addApi(LocationServices.API)
                    .build();
        }

        binding.fabGoToLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapsDirections();
            }
        });

        return rootView;
    }

    public int getSeconds(int seconds, int minutes, int hours) {
        return seconds + minutes * 60 + hours * 3600;
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

        CameraPosition cameraPosition = new CameraPosition
                .Builder()
                .target(new LatLng(location.getLatitude(), location.getLongitude()))
                .zoom(14)
                .build();

        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
        googleMap.getUiSettings().setScrollGesturesEnabled(false);
        createMarkers();
    }

    public void createMarkers() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) ContextCompat.getDrawable(getApplicationContext(),
                R.drawable.ic_fitness_center_black_48dp);
        Bitmap b = bitmapDrawable.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 80, 80, false);

        mGoogleMap.addMarker(new MarkerOptions()
                .position(new LatLng(location.getLatitude(), location.getLongitude()))
                .title(location.getUserFirstName())
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
    }

    public void mapsDirections() {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" +
                location.getLatitude() + ","
                + location.getLongitude() + "&mode=w");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    private String getAddress(double latitude, double longitude) {
        StringBuilder result = new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                result.append(address.getAddressLine(0)).append("\n");
                result.append(address.getLocality())
                        .append("(")
                        .append(address.getCountryName())
                        .append(")");
            }
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }

        return result.toString();
    }
}
