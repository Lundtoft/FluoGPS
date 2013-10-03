package dk.fluo.gps;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by hagabrth on 10/3/13.
 */
public class FluoLocationListener implements LocationListener {
       private Activity activity;

    public FluoLocationListener(Activity activity){
        this.activity = activity;
    }

    public void onLocationChanged(Location location) {
        String message = String.format(
                "New Location \n Longitude: %1$s \n Latitude: %2$s",
                location.getLongitude(), location.getLatitude()
        );

        Log.e("Location", message);

        ServerCom com = new ServerCom("http://users-cs.au.dk/lundtoft/pp/saveLocation.php", activity.getApplicationContext());

        com.sendFixToServer(location.getLongitude(), location.getLatitude(), location.getTime() / 1000);
    }

    public void onStatusChanged(String s, int i, Bundle b) {

    }

    public void onProviderDisabled(String s) {

    }

    public void onProviderEnabled(String s) {

    }

}
