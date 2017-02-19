package net.dynu.w3rkaut.presentation.ui.fragments;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.dynu.w3rkaut.R;
import net.dynu.w3rkaut.presentation.model.Location;

import java.util.concurrent.TimeUnit;

public class LocationInfoFragment extends Fragment {

    private static final String TAG = LocationInfoFragment.class
            .getSimpleName();

    public LocationInfoFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_location_info,
                container, false);
        Bundle args = getArguments();
        final Location location = new Location(args.getString("url"), args
                .getString("first_name"), args.getString("last_name"), args
                .getString("time_remaining"), args.getDouble("distance"),
                args.getString("posted_at"), args.getDouble("latitude"), args
                .getDouble("longitude"));
        final TextView textView = (TextView) rootView.findViewById(R.id.tv_count_down);

        ImageView ivLocation = (ImageView) rootView.findViewById(R.id.iv_location);
        ivLocation.setImageResource(R.drawable.fz_97);

        int seconds = getSeconds(
                Integer.parseInt(location.getTimeRemaining().substring(6,8)),
                Integer.parseInt(location.getTimeRemaining().substring(3,5)),
                Integer.parseInt(location.getTimeRemaining().substring(0,2)));


        new CountDownTimer(seconds * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                textView.setText("seconds remaining: " + millisUntilFinished / 1000);
                textView.setText(String.format("%d:%d:%d",
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                                - TimeUnit.HOURS.toMinutes(TimeUnit
                                .MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                textView.setText("done!");
            }
        }.start();
        return rootView;
    }

    public int getSeconds(int seconds, int minutes, int hours) {
        return seconds + minutes * 60 + hours * 3600;
    }
}
