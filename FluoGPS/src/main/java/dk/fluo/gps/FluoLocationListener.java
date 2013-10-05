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
public class FluoLocationListener implements LocationListener, FluoSensorEventListener.onMoveListener {
    private Activity activity;
    private boolean firstFix;
    private String title;
    private String type;
    private int dist, speed, time;
    private Location lastLocation;
    private boolean movedSinceLastLocation;

    public FluoLocationListener(Activity activity, String type){
        this.activity = activity;
        this.type = type;
        dist = 0;
        speed = 0;
        lastLocation = null;
        firstFix = true;
        movedSinceLastLocation = false;
    }

    /**
     *
     * Setters
     */
    public void setFirstFix(boolean value){
        firstFix = value;
    }

    public void setType(String value){
        type = value;
    }

    public void setDist(int value){
        dist = value;
    }

    public void setSpeed(int value){
        speed = value;
    }

    /**
     * onMoveListener interface
     */
    @Override
    public void onMove() {
        movedSinceLastLocation = true;
    }

    /**
     *
     * LocationListener methods
     */
    public void onLocationChanged(Location location) {
        ServerCom com = new ServerCom("http://users-cs.au.dk/lundtoft/pp/saveLocation.php", activity.getApplicationContext());
        long timestamp = location.getTime() / 1000;

        if (firstFix) {
            title = Long.toString(timestamp);
            firstFix = false;
        }

        if (dist > 0){
            type = "dist";
            if (lastLocation != null) {
                if (lastLocation.distanceTo(location) >= dist){
                    if (speed > 0) {
                        type = "speed";
                        if (location.getSpeed() < speed) {
                            com.sendFixToServer(location.getLongitude(), location.getLatitude(), timestamp, title, type);
                            lastLocation = location;
                        }
                    } else if (type.equals("accelerometer") && movedSinceLastLocation){
                        com.sendFixToServer(location.getLongitude(), location.getLatitude(), timestamp, title, type);
                        lastLocation = location;
                        movedSinceLastLocation = false;
                    } else {
                        com.sendFixToServer(location.getLongitude(), location.getLatitude(), timestamp, title, type);
                        lastLocation = location;
                    }
                }
            } else {
                com.sendFixToServer(location.getLongitude(), location.getLatitude(), timestamp, title, type);
                lastLocation = location;
            }
        } else {
            type = "time";
            com.sendFixToServer(location.getLongitude(), location.getLatitude(), timestamp, title, type);
        }
    }

    public void onStatusChanged(String s, int i, Bundle b) {

    }

    public void onProviderDisabled(String s) {

    }

    public void onProviderEnabled(String s) {

    }
}
