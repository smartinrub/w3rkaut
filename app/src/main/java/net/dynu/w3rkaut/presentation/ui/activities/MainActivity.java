package net.dynu.w3rkaut.presentation.ui.activities;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;

import net.dynu.w3rkaut.Permissions;
import net.dynu.w3rkaut.R;
import net.dynu.w3rkaut.domain.executor.impl.ThreadExecutor;
import net.dynu.w3rkaut.presentation.presenters.MainPresenter;
import net.dynu.w3rkaut.presentation.presenters.impl.MainPresenterImpl;
import net.dynu.w3rkaut.presentation.ui.fragments.ButtonAddLocationFragment;
import net.dynu.w3rkaut.presentation.ui.fragments.MapFragment;
import net.dynu.w3rkaut.presentation.ui.fragments.RecyclerViewFragment;
import net.dynu.w3rkaut.storage.LocationRepositoryImpl;
import net.dynu.w3rkaut.storage.UserRepositoryImpl;
import net.dynu.w3rkaut.storage.session.SharedPreferencesManager;
import net.dynu.w3rkaut.threading.MainThreadImpl;
import net.dynu.w3rkaut.utils.CurrentTime;
import net.dynu.w3rkaut.utils.LocationHandler;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * This class contains all the shared method between the recyclerview
 * fragment and the map fragment and it is in charge of showing the GUI.It is
 * the view
 * of the MVP pattern
 *
 * @author Sergio Martin Rubio
 */
public class MainActivity extends AppCompatActivity implements MainPresenter
        .View, NavigationView.OnNavigationItemSelectedListener,
        TimePickerDialog.OnTimeSetListener {

    final static String RECYCLER_VIEW_FRAGMENT_TAG = "RV_FRAGMENT_TAG";

    @Bind(R.id.coordinator_layout_main)
    CoordinatorLayout coordinatorLayout;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private MainPresenterImpl presenter;
    private LocationHandler locationHandler;

    private Timer timer;
    private ProgressDialog progressDialog;

    private int timerMinutes;
    private int timerHours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (AccessToken.getCurrentAccessToken() == null) {
            goToLoginScreen();
        }

        ButterKnife.bind(this);

        init();


        if (savedInstanceState == null) {
            showRecyclerViewFragment();
        }

    }

    private void goToLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void init() {
        setupToolbar();
        setupDrawer();

        presenter = new MainPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new LocationRepositoryImpl(this),
                new UserRepositoryImpl(this),
                SharedPreferencesManager.getInstance(getApplicationContext())
        );

        Permissions permissions = new Permissions(this);
        permissions.checkLocationPermission();
    }


    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_exit:
                exitApp();
                break;
            case R.id.nav_delete_account:
                deleteAccount();
                break;
        }
        drawerLayout.closeDrawer(navigationView);
        return true;
    }

    private void deleteAccount() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.delete_account_message)
                .setPositiveButton(R.string.delete,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                presenter.deleteUser(SharedPreferencesManager
                                        .getInstance(getApplication()).getValue());
                                exitApp();
                            }
                        }).setNegativeButton(R.string.cancel, new DialogInterface
                .OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.create().show();
    }


    @Override
    public void onUserDeleted(String message) {
        if (message.indexOf("successfully deleted") > 0) {
            Toast.makeText(this, R.string.account_deleted, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.error_deleting_account, Toast.LENGTH_SHORT).show();
        }
    }


    public void exitApp() {
        LoginManager.getInstance().logOut();
        SharedPreferencesManager.getInstance(this).clear();
        finish();
    }

    @OnClick(R.id.fab_add_location)
    public void submitAddLocationButton(View view) {
        showTimePickerDialog(view);
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new ButtonAddLocationFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        timerMinutes = minute;
        timerHours = hourOfDay;
        getLocation();
    }

    public void getLocation() {
        locationHandler = LocationHandler.getInstance(this);
        showProgress();
        timer = new Timer();
        timer.schedule(new GetLocationTask(), 300, 50);
    }

    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.publishing));
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
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(navigationView)) {
                    drawerLayout.closeDrawer(navigationView);
                } else {
                    drawerLayout.openDrawer(navigationView);
                }
                break;
            case R.id.action_map:
                showMapFragment();
                break;
            case R.id.action_recycler_view:
                showRecyclerViewFragment();
                break;
            case R.id.action_delete_location:
                deleteLocation();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showMapFragment() {
        MapFragment mapFragment = new MapFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.push_left_in, R.anim
                .push_left_out);
        fragmentTransaction.replace(R.id.fragment_holder, mapFragment,
                "map_fragment")
                .commit();
    }

    private void showRecyclerViewFragment() {
        RecyclerViewFragment recyclerViewFragment = new RecyclerViewFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.push_right_out, R.anim
                .push_right_in);
        fragmentTransaction.replace(R.id.fragment_holder,
                recyclerViewFragment, RECYCLER_VIEW_FRAGMENT_TAG).commit();
    }

    private void deleteLocation() {
        presenter.deleteLocation(SharedPreferencesManager
                .getInstance(getApplication()).getValue());
    }

    @Override
    public void onLocationAdded(String message) {
        if (message.indexOf("user already has a location") > 0) {
            Snackbar.make(
                    coordinatorLayout,
                    R.string.no_more_than_one_location,
                    Snackbar.LENGTH_SHORT).show();
        } else if(message.indexOf("successfully saved") > 0) {
            Snackbar.make(
                    coordinatorLayout,
                    R.string.position_added,
                    Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(
                    coordinatorLayout,
                    getString(R.string.add_location_error),
                    Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLocationDeleted(String message) {
        if(message.indexOf("user do not have a location") > 0){
            Toast.makeText(this, R.string.no_position, Toast.LENGTH_SHORT).show();
        }
        else if(message.indexOf("successfully deleted") > 0) {
            Toast.makeText(this, R.string.location_deleted, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.delete_position_error, Toast.LENGTH_SHORT).show();
        }

    }

    class GetLocationTask extends TimerTask {
        @Override
        public void run() {
            Double latitude = locationHandler.getLatitude();
            Double longitude = locationHandler.getLongitude();
            if (latitude != null && longitude != null) {
                Date postedAt = CurrentTime.getNow();
                presenter.addLocation(
                        latitude,
                        longitude,
                        timerHours + ":" + timerMinutes,
                        CurrentTime.formatTime(postedAt));
                timer.cancel();
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideProgress();
                    }
                });
            }
        }
    }
}
