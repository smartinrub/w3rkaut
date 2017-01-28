package net.dynu.w3rkaut;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;


public class Permissions extends Activity {

    private static final int MY_PERMISSIONS_REQUEST_MAPS = 1;

    private Context context;

    public Permissions(Context context) {
        this.context = context;
    }

    public void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission
                .ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, android.Manifest.permission
                .ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_MAPS);
        }
    }
}
