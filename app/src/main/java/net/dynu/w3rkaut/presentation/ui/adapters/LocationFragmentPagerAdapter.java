package net.dynu.w3rkaut.presentation.ui.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.dynu.w3rkaut.presentation.model.Location;
import net.dynu.w3rkaut.presentation.ui.fragments.HistoryFragment;
import net.dynu.w3rkaut.presentation.ui.fragments.LocationInfoFragment;

public class LocationFragmentPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "Info", "Frecuentes" };
    private Context context;
    private Location location;

    public LocationFragmentPagerAdapter(FragmentManager fm, Context context,
                                        Location location) {
        super(fm);
        this.context = context;
        this.location = location;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        Bundle args = new Bundle();

        switch (position){
            case 0:
                fragment = new LocationInfoFragment();
                args.putString("url", location.getImageUrl());
                args.putString("first_name", location.getUserFirstName());
                args.putString("last_name", location.getUserLastName());
                args.putString("time_remaining", location.getTimeRemaining());
                args.putDouble("distance", location.getDistance());
                args.putString("posted_at", location.getPostedAt());
                args.putDouble("latitude", location.getLatitude());
                args.putDouble("longitude", location.getLongitude());
                fragment.setArguments(args);
                break;
            case 1:
                fragment = new HistoryFragment();
                args.putString("url", location.getImageUrl());
                fragment.setArguments(args);
                break;
        }


        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
