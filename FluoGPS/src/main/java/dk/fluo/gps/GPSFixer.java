package dk.fluo.gps;

import java.util.*;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.*;
import android.content.Context;
import android.util.Log;


/**
 * Created by hagbarth on 9/25/13.
 */
public class GPSFixer {

    /**
     * Properties
     */
    private int timePeriod;
    private int dist;
    private int speed;
    private LocationManager locationManager;
    private FluoLocationListener listener;
    private Activity activity;
    public boolean gpsRunning;

    /**
     * Constructors
     */
    public GPSFixer(int timePeriod, int dist, Activity activity){
        this.timePeriod = timePeriod;
        this.dist = dist;
        this.speed = 0;
        this.activity = activity;

        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        listener = new FluoLocationListener(activity, "time");
        gpsRunning = false;
    }

    /**
     * Setters and getters
     */
    public Activity getActivity(){
        return activity;
    }

    public int getTimePeriod(){
        return timePeriod;
    }

    public int getDist(){
        return dist;
    }

    public void setTimePeriod(int timePeriod){
        this.timePeriod = timePeriod;
    }

    public void setDist(int dist){
        this.dist = dist;
    }

    public void setSpeed(int value){
        speed = value;
    }

    /**
     * GPS related methods
     */

    public void startTimeGPS(){
        locationManager.removeUpdates(listener);
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                timePeriod,
                0,
                listener
        );
        gpsRunning = true;
    }

    public void startDistGPS(){
        locationManager.removeUpdates(listener);
        listener.setDist(dist);
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0,
                listener
        );
        gpsRunning = true;
    }

    public void startSpeedGPS(){
        locationManager.removeUpdates(listener);

        listener.setSpeed(speed);
        listener.setDist(dist);
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0,
                listener
        );
        gpsRunning = true;
    }

    public void stopGPS(){
        listener.setFirstFix(true);
        locationManager.removeUpdates(listener);
        gpsRunning = false;
        timePeriod = 0;
        dist = 0;
        speed = 0;
    }

    public void pauseGPS(){
        locationManager.removeUpdates(listener);
        gpsRunning = false;
        timePeriod = 0;
        dist = 0;
        speed = 0;
    }
}
