package net.dynu.w3rkaut.presentation.ui.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import net.dynu.w3rkaut.R;
import net.dynu.w3rkaut.presentation.model.Location;
import net.dynu.w3rkaut.presentation.ui.adapters.LocationFragmentPagerAdapter;
import net.dynu.w3rkaut.presentation.ui.fragments.RecyclerViewFragment;

public class TabsActivity extends FragmentActivity {

    private static final String TAG = TabsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        Location location = (Location) getIntent().getSerializableExtra
                ("location");
        Log.e(TAG, location.getUserFirstName());

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new LocationFragmentPagerAdapter
                (getSupportFragmentManager(), TabsActivity.this));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
