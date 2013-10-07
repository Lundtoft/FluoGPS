package dk.fluo.gps;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by hagabrth on 10/4/13.
 */
public class FluoSensorEventListener implements SensorEventListener{
    private static final float MIN_FORCE = 1;
    private GPSFixer fixer;
    private float lastX = 0,
                  lastY = 0,
                  lastZ = 0;
    private int numberOfLows;
    private Toast startToast;
    private Toast stopToast;

    /**
     * Constructors
     */
    public FluoSensorEventListener(GPSFixer fixer){
        this.fixer = fixer;
        fixer.setAcc(true);
        this.numberOfLows = 0;
        startToast = Toast.makeText(fixer.getActivity().getApplicationContext(), "GPS Accelerometer Started", Toast.LENGTH_SHORT);
        stopToast = Toast.makeText(fixer.getActivity().getApplicationContext(), "GPS Accelerometer Stopped", Toast.LENGTH_SHORT);
    }

    /**
     * Sensor event listener
     */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        //only if sensor is accelerometer
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            //get sensor values
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            //get absolute values of total movement since last change
            float totalMovement = Math.abs(x + y + z - lastX - lastY - lastZ);

            //if total movement since last change is larger than the predefined min force (High value)
            if (totalMovement > MIN_FORCE) {

                //reset number of lows
                numberOfLows = 0;

                //set last movement values
                lastX = x;
                lastY = y;
                lastZ = z;

                //if gps is not already runnning
                if (!fixer.gpsRunning) {
                    fixer.startDistGPS();
                    if (startToast.getView().isShown()) {
                        startToast.cancel();
                    } else {
                        startToast.show();
                    }
                }


            }
            //else (Low value)
            else {

                //if there hasn't been 10 Low values in a row
                if (numberOfLows < 10) {
                    numberOfLows++;
                }
                //else
                else {
                    //stop gps if running
                    if (fixer.gpsRunning) {
                        fixer.pauseGPS();

                        if (startToast.getView().isShown()) {
                            startToast.cancel();
                        }

                        if (stopToast.getView().isShown()) {
                            stopToast.cancel();
                        } else {
                            stopToast.show();
                        }
                    }
                }
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
