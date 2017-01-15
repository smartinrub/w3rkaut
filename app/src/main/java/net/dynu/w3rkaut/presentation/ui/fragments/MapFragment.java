package net.dynu.w3rkaut.presentation.ui.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MapStyleOptions;

import net.dynu.w3rkaut.R;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.removeItem(R.id.action_map);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container,
                false);
        initMap(savedInstanceState, rootView);
        return rootView;
    }

    private void initMap(Bundle savedInstanceState, View view) {
        mapView = (MapView) view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.style_json);
        googleMap.setMapStyle(style);
    }
}
