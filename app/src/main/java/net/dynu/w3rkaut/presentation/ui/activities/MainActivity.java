package net.dynu.w3rkaut.presentation.ui.activities;

import android.Manifest;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import net.dynu.w3rkaut.R;
import net.dynu.w3rkaut.presentation.presenters.interfaces.MainPresenter;
import net.dynu.w3rkaut.presentation.presenters.impl.MainPresenterImpl;
import net.dynu.w3rkaut.presentation.ui.fragments.ButtonAddLocationFragment;
import net.dynu.w3rkaut.presentation.ui.fragments.MapFragment;
import net.dynu.w3rkaut.presentation.ui.fragments.RecyclerViewFragment;
import net.dynu.w3rkaut.utils.CurrentTime;
import net.dynu.w3rkaut.utils.SharedPreferencesManager;

import java.util.Date;

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
        TimePickerDialog.OnTimeSetListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    final static String RECYCLER_VIEW_FRAGMENT_TAG = "RV_FRAGMENT_TAG";
    private static final int MY_PERMISSIONS_REQUEST_MAPS = 1;
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String USER_AGREEMENT_URL =
            "https://w3rkaut.dynu.net/android/docs/user_agreement.html";
    private static final String PRIVACY_POLICY_URL =
            "https://w3rkaut.dynu.net/android/docs/privacy_policy.html";

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;

    private MainPresenterImpl presenter;
    private int hourOfDay;
    private int minute;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (AccessToken.getCurrentAccessToken() == null) {
            goToLoginScreen();
        }

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinator_layout_main);
        setupToolbar();
        setupDrawer();
        FloatingActionButton fabAddLocation = (FloatingActionButton) findViewById(R.id
                .fab_add_location);
        fabAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new ButtonAddLocationFragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

        if (savedInstanceState == null) {
            showRecyclerViewFragment();
        }
    }

    private void goToLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toolbar.setNavigationIcon(R.drawable.ic_settings_black_24dp);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_exit:
                exitApp();
                break;
            case R.id.nav_delete_account:
                deleteAccount();
                break;
            case R.id.nav_privacy_policy:
                Intent intentPrivacyPolicy;
                intentPrivacyPolicy = new Intent(Intent.ACTION_VIEW);
                intentPrivacyPolicy.setData(Uri.parse(PRIVACY_POLICY_URL));
                startActivity(intentPrivacyPolicy);
                break;
            case R.id.nav_user_agreement:
                Intent intentUserAgreement;
                intentUserAgreement = new Intent(Intent.ACTION_VIEW);
                intentUserAgreement.setData(Uri.parse(USER_AGREEMENT_URL));
                startActivity(intentUserAgreement);
                break;
        }
        drawerLayout.closeDrawer(navigationView);
        return true;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        this.hourOfDay = hourOfDay;
        this.minute = minute;

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_MAPS);
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            Date postedAt = CurrentTime.getNow();
            presenter = new MainPresenterImpl(this, this);
            presenter.addLocation(
                    SharedPreferencesManager.getInstance(getApplication()).getValue(),
                    mLastLocation.getLatitude(),
                    mLastLocation.getLongitude(),
                    hourOfDay + ":" + minute,
                    CurrentTime.formatTime(postedAt));
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "Google API connection failed");
    }

    @Override
    public void onLocationAdded(String message) {
        if (message.indexOf("location updated") > 0) {
            Snackbar.make(
                    coordinatorLayout,
                    R.string.position_updated,
                    Snackbar.LENGTH_SHORT).show();
        } else if (message.indexOf("successfully saved") > 0) {
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

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    private void deleteLocation() {
        presenter.deleteLocation(SharedPreferencesManager
                .getInstance(getApplication()).getValue());
    }

    @Override
    public void onLocationDeleted(String message) {
        if (message.indexOf("user do not have a location") > 0) {
            Toast.makeText(this, R.string.no_position, Toast.LENGTH_SHORT).show();
        } else if (message.indexOf("successfully deleted") > 0) {
            Toast.makeText(this, R.string.location_deleted, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.delete_position_error, Toast.LENGTH_SHORT).show();
        }

    }
}
