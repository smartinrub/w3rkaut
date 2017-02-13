package net.dynu.w3rkaut.presentation.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

import net.dynu.w3rkaut.R;
import net.dynu.w3rkaut.presentation.Model.Location;

import java.util.HashMap;
import java.util.Locale;

/**
 * This class is an adapter which set content in the info windows of all
 * the markers
 *
 * @author Sergio Martin Rubio
 */
public class MapWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View myContentsView;

    private HashMap<String, Location> locations;

    public MapWindowAdapter(HashMap<String, Location> locations, LayoutInflater
            inflater){
        myContentsView = inflater.inflate(R.layout.item_map, null);
        this.locations = locations;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        ImageView ivImageUser = ((ImageView) myContentsView.findViewById(R.id
                .image_view_map_fragment_profile_pic));
        TextView tvFirstName = ((TextView) myContentsView.findViewById(R.id
                .text_view_map_fragment_first_name));
        TextView tvTimeRemaining = ((TextView) myContentsView.findViewById(R.id
                .text_view_map_fragment_time_remaining));
        TextView tvDistance = ((TextView) myContentsView.findViewById(R.id
                .text_view_map_fragment_distance));

        Picasso.with(myContentsView.getContext())
                .load(locations.get(marker.getSnippet()).getImageUrl())
                .error(R.mipmap.ic_launcher)
                .into(ivImageUser);
        tvFirstName.setText(locations.get(marker.getSnippet()).getUserFirstName());
        tvTimeRemaining.setText(locations.get(marker.getSnippet())
                .getTimeRemaining());
        Double distance = locations.get(marker
                .getSnippet())
                .getDistance();
        if (distance >= 1000){
            distance /= 1000;
            tvDistance.setText(String.format(Locale.ITALY,"%.1f km",distance));
        } else {
            tvDistance.setText(String.format(Locale.ITALY,"%.0f m",distance));
        }

        return myContentsView;
    }
}
