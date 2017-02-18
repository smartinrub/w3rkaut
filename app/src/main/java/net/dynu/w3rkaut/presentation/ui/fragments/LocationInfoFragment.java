package net.dynu.w3rkaut.presentation.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.dynu.w3rkaut.R;
import net.dynu.w3rkaut.presentation.model.Location;

public class LocationInfoFragment extends Fragment {

    public LocationInfoFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page,
                container, false);
        Bundle args = getArguments();
        Location location = new Location(args.getString("url"), args
                .getString("first_name"), args.getString("last_name"), args
                .getString("time_remaining"), args.getDouble("distance"),
                args.getString("posted_at"), args.getDouble("latitude"), args
                .getDouble("longitude"));
        TextView textView = (TextView) rootView;
        textView.setText("Fragment #" + location.getUserFirstName());
        return rootView;
    }
}
