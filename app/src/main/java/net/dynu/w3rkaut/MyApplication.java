package net.dynu.w3rkaut;

import android.app.Application;

import net.dynu.w3rkaut.network.RestClientModule;

import javax.inject.Inject;

import dagger.internal.DaggerCollections;
import timber.log.Timber;

/**
 * Created by sergio on 08/01/2017.
 */

public class MyApplication extends Application {

    @Inject
    public

    @Override
    public void onCreate() {
        super.onCreate();

        // enable logging
        Timber.plant(new Timber.DebugTree());

        restClientModule =
    }
}
