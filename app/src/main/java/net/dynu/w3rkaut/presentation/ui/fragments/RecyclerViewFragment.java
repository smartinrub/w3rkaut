package net.dynu.w3rkaut.presentation.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;

import net.dynu.w3rkaut.R;
import net.dynu.w3rkaut.domain.executor.impl.ThreadExecutor;
import net.dynu.w3rkaut.domain.respository.LocationRepository;
import net.dynu.w3rkaut.presentation.Model.Location;
import net.dynu.w3rkaut.presentation.presenters.LocationListPresenter;
import net.dynu.w3rkaut.presentation.presenters.impl.LocationListPresenterImpl;
import net.dynu.w3rkaut.presentation.ui.adapters.RecyclerBindingAdapter;
import net.dynu.w3rkaut.storage.LocationRepositoryImpl;
import net.dynu.w3rkaut.threading.MainThreadImpl;

import java.util.Comparator;
import java.util.List;

public class RecyclerViewFragment extends Fragment implements
        LocationListPresenter.View, RecyclerBindingAdapter.OnItemClickListener, RecyclerBindingAdapter.OnItemLongClickListener {

    private double latitude;
    private double longitude;
    private LocationListPresenterImpl presenter;
    private RecyclerView recyclerView;
    private RecyclerBindingAdapter recyclerBindingAdapter;
    private View rootView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private static final Comparator<Location> DISTANCE_COMPARATOR = new
            Comparator<Location>() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout
                .fragment_recycler_view_locations, container, false);

        latitude = getArguments().getDouble("latitude");
        longitude = getArguments().getDouble("longitude");

        init();

        return rootView;
    }

    private void init() {
        presenter = new LocationListPresenterImpl
                (ThreadExecutor.getInstance(), MainThreadImpl.getInstance(),
                        this,
                        new LocationRepositoryImpl(getActivity()), new LatLng
                        (latitude, longitude));

        setupRecyclerView(rootView);
    }

    private void setupRecyclerView(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id
                .swipe_refresh_layout);
        // Empty recyclerView to avoid layout error
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
    public void onLocationsRetrieved(final List<Location> locations) {
        recyclerBindingAdapter = new RecyclerBindingAdapter
                (getContext(), DISTANCE_COMPARATOR, this, this);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        recyclerBindingAdapter.replaceAll(locations);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
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

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {

    }
}
