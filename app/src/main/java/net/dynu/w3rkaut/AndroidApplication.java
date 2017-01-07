package net.dynu.w3rkaut;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by sergio on 08/01/2017.
 */

public class AndroidApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // enable logging
        Timber.plant(new Timber.DebugTree());
    }
}
