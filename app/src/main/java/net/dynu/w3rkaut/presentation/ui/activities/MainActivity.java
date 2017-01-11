package net.dynu.w3rkaut.presentation.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import net.dynu.w3rkaut.Permissions;
import net.dynu.w3rkaut.R;
import net.dynu.w3rkaut.domain.executor.impl.ThreadExecutor;
import net.dynu.w3rkaut.domain.model.Location;
import net.dynu.w3rkaut.presentation.presenters.MainPresenter;
import net.dynu.w3rkaut.presentation.presenters.impl.MainPresenterImpl;
import net.dynu.w3rkaut.storage.LocationRepositoryImpl;
import net.dynu.w3rkaut.storage.session.SharedPreferencesManager;
import net.dynu.w3rkaut.threading.MainThreadImpl;
import net.dynu.w3rkaut.utils.CurrentTime;
import net.dynu.w3rkaut.utils.LocationHandler;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements MainPresenter.View, NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.coordinator_layout_main)
    CoordinatorLayout coordinatorLayout;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private MainPresenterImpl presenter;
    private LocationHandler locationHandler;

    private Double latitude;
    private Double longitude;
    private Timer timer;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_main);

        if (AccessToken.getCurrentAccessToken() == null) {
            goToLoginScreen();
        }

        ButterKnife.bind(this);

        init();

    }

    private void goToLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void init() {
        setupToolbar();
        setupDrawer();
        setupFloatingButton();

        presenter = new MainPresenterImpl(
                ThreadExecutor.getInstance(),
                MainThreadImpl.getInstance(),
                this,
                new LocationRepositoryImpl(this),
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
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_settings:
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(navigationView);
        return true;
    }

    private void setupFloatingButton() {
        FloatingActionButton fabAddLocation = (FloatingActionButton)
                findViewById(R.id.fab_add_location);
        fabAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationHandler = new LocationHandler(MainActivity.this);
                showProgress();
                timer = new Timer();
                timer.schedule(new GetLocationTask(), 500, 200);
            }
        });
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
                break;
            case R.id.action_recycler_view:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationAdded(String message) {
        String msg = message;
        Timber.e(message);
        Snackbar.make(
                coordinatorLayout,
                msg,
                Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Publicando...");
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
    public void showError(String message) {

    }

    class GetLocationTask extends TimerTask {
        @Override
        public void run() {
            latitude = locationHandler.getLatitude();
            longitude = locationHandler.getLongitude();
            Timber.e(String.valueOf(locationHandler.getLatitude()));
            if (latitude != null && longitude != null) {
                Date time = CurrentTime.getNow();
                presenter.addLocation(
                        latitude,
                        longitude,
                        CurrentTime.formatTime(time));
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
