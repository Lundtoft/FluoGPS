package dk.fluo.gps;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by hagabrth on 10/4/13.
 */
public class FluoSensorEventListener implements SensorEventListener{
    private static final int MIN_FORCE = 10;
    private int dist;
    private float lastX = 0,
                  lastY = 0,
                  lastZ = 0;
    private onMoveListener onMoveListener;

    /**
     * Interface to communicate when move
     */
    public interface onMoveListener {
        void onMove();
    }

    /**
     * Setters
     */
    public void setOnMoveListener(onMoveListener value){
        onMoveListener = value;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[SensorManager.AXIS_X];
            float y = sensorEvent.values[SensorManager.AXIS_Y];
            float z = sensorEvent.values[SensorManager.AXIS_Z];

            float totalMovement = Math.abs(x + y + z - lastX - lastY - lastZ);

            if (totalMovement > MIN_FORCE) {
                lastX = x;
                lastY = y;
                lastZ = z;

                onMoveListener.onMove();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
