package net.dynu.w3rkaut.presentation.ui.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import net.dynu.w3rkaut.R;
import net.dynu.w3rkaut.network.model.RESTLocation;
import net.dynu.w3rkaut.presentation.Model.Location;
import net.dynu.w3rkaut.presentation.converter.LocationsRestFormat;
import net.dynu.w3rkaut.presentation.presenters.LocationListPresenter;
import net.dynu.w3rkaut.presentation.presenters.impl.LocationListPresenterImpl;
import net.dynu.w3rkaut.presentation.ui.adapters.RecyclerBindingAdapter;
import net.dynu.w3rkaut.utils.SharedPreferencesManager;
import net.dynu.w3rkaut.utils.SimpleDividerItemDecoration;

import java.util.Comparator;
import java.util.List;

/**
 * This class displays the content for the recyclerview. It is the view
 * of the MVP pattern.
 *
 * @author Sergio Martin Rubio
 */
public class RecyclerViewFragment extends Fragment implements
        LocationListPresenter.View, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int MY_PERMISSIONS_REQUEST_MAPS = 1;
    private static final String TAG = RecyclerViewFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private RecyclerBindingAdapter recyclerBindingAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AdView mAdView;
    private TextView tvNoLocations;

    private LocationListPresenterImpl presenter;

    private Double currLat;
    private Double currLng;

    private List<Location> locations;

    private static final Comparator<Location> DISTANCE_COMPARATOR =
            new Comparator<Location>() {
                @Override
                public int compare(Location a, Location b) {
                    return Double.compare(a.getDistance(), b.getDistance());
                }
            };
    private GoogleApiClient mGoogleApiClient;


    public RecyclerViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.removeItem(R.id.action_recycler_view);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout
                        .fragment_recycler_view_locations,
                container, false);
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdView = (AdView) rootView.findViewById(R.id.adViewRecyclerView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        tvNoLocations = (TextView) rootView
                .findViewById(R.id.text_view_no_locations);
        tvNoLocations.setVisibility(View.GONE);

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
            presenter = new LocationListPresenterImpl(this, getActivity());
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
    public void onLocationsRetrieved(List<RESTLocation> locations) {
        recyclerBindingAdapter = new RecyclerBindingAdapter(getActivity(),
                DISTANCE_COMPARATOR, new RecyclerBindingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Location item) {
                Toast toast = Toast.makeText(getContext(), getString(R.string.posted_at) +
                        item.getPostedAt().substring(11, 13) +
                        ":" +
                        item.getPostedAt().substring(14, 16) +
                        getString(R.string.on) +
                        item.getPostedAt().substring(0, 10), Toast
                        .LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 300);
                toast.show();
            }
        });
        this.locations = LocationsRestFormat.convertRESTLocationToLocation
                (locations, currLat, currLng);
        recyclerBindingAdapter.add(this.locations);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getActivity(), R.string.updating, Toast
                        .LENGTH_SHORT).show();
                if (currLng != null && currLat != null) {
                    presenter.getAllLocations();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        recyclerView.setAdapter(recyclerBindingAdapter);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        if (locations.size() == 0) {
            tvNoLocations.setVisibility(View.VISIBLE);
        } else {
            tvNoLocations.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                presenter.getAllLocations();
                break;
            case R.id.action_delete_location:
                long id = SharedPreferencesManager.getInstance(getContext())
                        .getValue();
                for (Location l : locations) {
                    if (l.getImageUrl().equals("https://graph.facebook.com/" +
                            id + "/picture?type=large")) {
                        recyclerBindingAdapter.remove(l);
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
        mAdView.pause();
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

    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
}
