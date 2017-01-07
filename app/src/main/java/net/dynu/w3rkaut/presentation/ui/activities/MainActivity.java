package net.dynu.w3rkaut.presentation.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;

import net.dynu.w3rkaut.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_main);

        if (AccessToken.getCurrentAccessToken() == null) {
            goToLoginScreen();
        }
    }

    private void goToLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
