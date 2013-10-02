package dk.fluo.gps;

import java.util.*;

import android.app.Activity;
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
    private LocationManager locationManager;

    /**
     * Constructors
     */
    public GPSFixer(int timePeriod, int dist, Activity activity){
        this.timePeriod = timePeriod;
        this.dist = dist;

        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
    }

    /**
     * Setters and getters
     */
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

    /**
     * GPS related method
     */
    public ArrayList<Double> getPosition(){
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        ArrayList<Double> coordinates = new ArrayList<Double>();


        if (location != null) {
            coordinates.add( location.getLongitude() );
            coordinates.add( location.getLatitude() );
        }

        return coordinates;
    }
}
