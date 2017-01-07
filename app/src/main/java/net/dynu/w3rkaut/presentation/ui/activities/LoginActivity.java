package net.dynu.w3rkaut.presentation.ui.activities;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import net.dynu.w3rkaut.R;

public class LoginActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_login);
        AppEventsLogger.activateApp(getApplication());
    }
}
