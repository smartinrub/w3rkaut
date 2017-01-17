package net.dynu.w3rkaut.presentation.ui.adapters;

import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

import net.dynu.w3rkaut.R;
import net.dynu.w3rkaut.presentation.Model.Location;

import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import timber.log.Timber;

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
        TextView tvLastName = ((TextView) myContentsView.findViewById(R.id
                .text_view_map_fragment_last_name));
        TextView tvPostedAt = ((TextView) myContentsView.findViewById(R.id
                .text_view_map_fragment_posted_at));
        TextView tvDistance = ((TextView) myContentsView.findViewById(R.id
                .text_view_map_fragment_distance));
        TextView tvParticipants = ((TextView) myContentsView.findViewById(R.id
                .text_view_map_fragment_participants));


        Picasso.with(myContentsView.getContext())
                .load(locations.get(marker.getSnippet()).getImageUrl())
                .error(R.mipmap.ic_launcher)
                .into(ivImageUser);
        tvFirstName.setText(locations.get(marker.getSnippet()).getUserFirstName());
        tvLastName.setText(locations.get(marker.getSnippet()).getUserLastName());
        tvPostedAt.setText(locations.get(marker.getSnippet()).getPostedAt());
        Double distance = locations.get(marker
                .getSnippet())
                .getDistance();
        if (distance < 1){
            distance *= 1000;
            tvDistance.setText(String.format(Locale.ITALY,"%.0f m",distance));
        } else {
            tvDistance.setText(String.format(Locale.ITALY,"%.1f km",distance));
        }

        tvParticipants.setText(String.format(Locale.ITALY,"%d",locations.get
                (marker.getSnippet())
                .getParticipants()));

        return myContentsView;
    }
}
