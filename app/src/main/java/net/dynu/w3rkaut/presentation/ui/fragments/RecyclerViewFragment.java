package net.dynu.w3rkaut.presentation.ui.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import net.dynu.w3rkaut.R;
import net.dynu.w3rkaut.domain.executor.impl.ThreadExecutor;
import net.dynu.w3rkaut.network.model.RESTLocation;
import net.dynu.w3rkaut.presentation.Model.Location;
import net.dynu.w3rkaut.presentation.converter.DateTimeConverter;
import net.dynu.w3rkaut.presentation.converter.LocationConverter;
import net.dynu.w3rkaut.presentation.presenters.LocationListPresenter;
import net.dynu.w3rkaut.presentation.presenters.impl.LocationListPresenterImpl;
import net.dynu.w3rkaut.presentation.ui.adapters.RecyclerBindingAdapter;
import net.dynu.w3rkaut.storage.LocationRepositoryImpl;
import net.dynu.w3rkaut.storage.session.SharedPreferencesManager;
import net.dynu.w3rkaut.threading.MainThreadImpl;
import net.dynu.w3rkaut.utils.LocationHandler;
import net.dynu.w3rkaut.utils.SimpleDividerItemDecoration;

import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import timber.log.Timber;


public class RecyclerViewFragment extends BaseFragment implements
        LocationListPresenter.View {

    private View rootView;

    private LocationHandler locationHandler;

    private RecyclerView recyclerView;
    private RecyclerBindingAdapter recyclerBindingAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private LocationListPresenterImpl presenter;

    private Timer listTimer;
    private Timer latLngTimer;

    private Double currLatitude;
    private Double currLongitude;

    private List<Location> locations;

    private static final Comparator<Location> DISTANCE_COMPARATOR =
            new Comparator<Location>() {
                @Override
                public int compare(Location a, Location b) {
                    return Double.compare(a.getDistance(), b.getDistance());
                }
            };


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
        rootView = inflater.inflate(R.layout.fragment_recycler_view_locations,
                container, false);
        getCurrentLocation();

        return rootView;
    }

    private void setupRecyclerView() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id
                .recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id
                .swipe_refresh_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return null;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            }

            @Override
            public int getItemCount() {
                return 0;
            }
        });
    }

    @Override
    public void showLocations(List<RESTLocation> locations) {

        this.locations = LocationConverter
                .convertRESTLocationToLocation(locations,
                        new LatLng(currLatitude, currLongitude));

        showProgress();
        listTimer = new Timer();
        listTimer.schedule(new SetAllLocationsTask(), 200, 50);
    }

    public void getCurrentLocation() {
        locationHandler = LocationHandler.getInstance(getActivity());
        latLngTimer = new Timer();
        latLngTimer.schedule(new GetCurrentLocationTask(), 0, 50);
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

    class SetAllLocationsTask extends TimerTask implements
            RecyclerBindingAdapter.OnItemClickListener,
            RecyclerBindingAdapter.OnItemLongClickListener {
        @Override
        public void run() {
            if (locations != null) {
                recyclerBindingAdapter = new RecyclerBindingAdapter(getContext(),
                        DISTANCE_COMPARATOR, this, this);
                recyclerBindingAdapter.add(locations);
                swipeRefreshLayout.setOnRefreshListener(
                        new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                presenter.getAllLocations();
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }
                );
                listTimer.cancel();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(recyclerBindingAdapter);
                        recyclerView.addItemDecoration(new
                                SimpleDividerItemDecoration(getContext()));
                        recyclerBindingAdapter.replaceAll(locations);
                        hideProgress();
                    }
                });
            }
        }

        @Override
        public void onItemClick(Location item) {
            Toast toast = Toast.makeText(getContext(), DateTimeConverter
                    .convert(item.getPostedAt()), Toast
                    .LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 300);
            toast.show();
        }

        @Override
        public boolean onItemLongClick(final Location item) {

            return true;
        }
    }

    private void init() {

        presenter = new LocationListPresenterImpl
                (ThreadExecutor.getInstance(), MainThreadImpl.getInstance(),
                        this,
                        new LocationRepositoryImpl(getActivity()), new LatLng
                        (currLatitude, currLongitude));
        presenter.getAllLocations();

        setupRecyclerView();
    }

    @Override
    public void onPause() {
        super.onPause();
        locationHandler.getGoogleApiClient().disconnect();
    }

    @Override
    public void onStop() {
        super.onStop();
        locationHandler.getGoogleApiClient().disconnect();
    }


    @Override
    public void onResume() {
        super.onResume();
        locationHandler.getGoogleApiClient().connect();
    }

}
