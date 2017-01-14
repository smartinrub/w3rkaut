package net.dynu.w3rkaut.presentation.ui.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import net.dynu.w3rkaut.R;
import net.dynu.w3rkaut.domain.executor.impl.ThreadExecutor;
import net.dynu.w3rkaut.presentation.Model.Location;
import net.dynu.w3rkaut.presentation.presenters.LocationListPresenter;
import net.dynu.w3rkaut.presentation.presenters.impl.LocationListPresenterImpl;
import net.dynu.w3rkaut.presentation.ui.adapters.RecyclerBindingAdapter;
import net.dynu.w3rkaut.storage.LocationRepositoryImpl;
import net.dynu.w3rkaut.threading.MainThreadImpl;
import net.dynu.w3rkaut.utils.LocationHandler;

import java.util.Comparator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RecyclerViewFragment extends Fragment implements
        LocationListPresenter.View, RecyclerBindingAdapter.OnItemClickListener, RecyclerBindingAdapter.OnItemLongClickListener {

    private View rootView;

    private LocationHandler locationHandler;

    private RecyclerView recyclerView;
    private RecyclerBindingAdapter recyclerBindingAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private LocationListPresenterImpl presenter;

    private Timer listTimer;
    private Timer latLngTimer;
    private ProgressDialog progressDialog;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
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

    private void init() {

        presenter = new LocationListPresenterImpl
                (ThreadExecutor.getInstance(), MainThreadImpl.getInstance(),
                        this,
                        new LocationRepositoryImpl(getActivity()), new LatLng
                        (currLatitude, currLongitude));
        presenter.getAllLocations();

        setupRecyclerView();
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
    public void onItemClick(Location item) {

    }

    @Override
    public boolean onItemLongClick(Location item) {
        return false;
    }

    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showLocations(List<Location> locations) {
        this.locations = locations;
        showProgress();
        listTimer = new Timer();
        listTimer.schedule(new GetAllLocationsTask(), 200, 50);
    }

    @Override
    public void onClickDeleteLocation(Location location) {

    }

    @Override
    public void onLocationDeleted() {

    }


    public void getCurrentLocation() {
        locationHandler = new LocationHandler(getActivity());
        latLngTimer = new Timer();
        latLngTimer.schedule(new GetCurrentLocationTask(), 0, 50);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                presenter.getAllLocations();
                recyclerBindingAdapter.replaceAll(locations);
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


    class GetAllLocationsTask extends TimerTask implements
            RecyclerBindingAdapter.OnItemClickListener,
            RecyclerBindingAdapter.OnItemLongClickListener {
        @Override
        public void run() {
            if(locations != null) {
                recyclerBindingAdapter = new RecyclerBindingAdapter(getContext(),
                        DISTANCE_COMPARATOR, this, this);
                recyclerBindingAdapter.add(locations);
                swipeRefreshLayout.setOnRefreshListener(
                        new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                presenter.getAllLocations();
                                recyclerBindingAdapter.replaceAll(locations);
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }
                );
                listTimer.cancel();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(recyclerBindingAdapter);
                        hideProgress();
                    }
                });
            }
        }

        @Override
        public void onItemClick(Location item) {
            Toast.makeText(getActivity(), item.getUserFirstName(),
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onItemLongClick(Location item) {
            return false;
        }
    }
}
