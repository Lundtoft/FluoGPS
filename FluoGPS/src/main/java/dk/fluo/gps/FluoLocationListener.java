package dk.fluo.gps;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by hagabrth on 10/3/13.
 */
public class FluoLocationListener implements LocationListener {
    private Activity activity;
    private boolean firstFix;
    private String title;
    private String type;
    private int dist, speed, time;
    private Location lastLocation;

    /**
     *
     * @param activity the activity which instantiates this object
     * @param type dist, time, speed or accelerometer
     */
    public FluoLocationListener(Activity activity, String type) {
        this.activity = activity;
        this.type = type;
        dist = 0;
        speed = 0;
        lastLocation = null;
        firstFix = true;
    }

    /**
     * Setters
     */
    public void setFirstFix(boolean value) {
        firstFix = value;
    }

    public void setType(String value) {
        type = value;
    }

    public void setDist(int value) {
        dist = value;
    }

    public void setSpeed(int value) {
        speed = value;
    }

    /**
     * LocationListener methods
     */
    @Override
    public void onLocationChanged(Location location) {
        ServerCom com = new ServerCom("http://users-cs.au.dk/lundtoft/pp/saveLocation.php", activity.getApplicationContext());
        long timestamp = location.getTime() / 1000;

        //Set title of file to be saved if this is the first fix of the process
        if (firstFix) {
            title = Long.toString(timestamp);
            firstFix = false;
        }

        //if distance is more than zero, do distance based positioning
        if (dist > 0){
            //if not first location compare to last location
            if (lastLocation != null) {
                if (lastLocation.distanceTo(location) >= dist){
                    //if speed is more than zero, only send position if actual speed is more than the given min speed
                    if (speed > 0) {
                        if (location.getSpeed() < speed) {
                            com.sendFixToServer(location.getLongitude(), location.getLatitude(), timestamp, title, type);
                            lastLocation = location;
                        }
                    } else {
                        com.sendFixToServer(location.getLongitude(), location.getLatitude(), timestamp, title, type);
                        lastLocation = location;
                    }
                }
            } else {
                com.sendFixToServer(location.getLongitude(), location.getLatitude(), timestamp, title, type);
                lastLocation = location;
            }
        //else assume that it is a time based positioning
        } else {
            com.sendFixToServer(location.getLongitude(), location.getLatitude(), timestamp, title, type);
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle b) {

    }
    @Override
    public void onProviderDisabled(String s) {

    }
    @Override
    public void onProviderEnabled(String s) {

    }
}
