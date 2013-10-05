package dk.fluo.gps;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainActivity extends Activity {
    private Handler handler;
    private ServerCom com;
    private GPSFixer fixer;
    private Runnable timer;
    private Future timerFuture;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        executorService = Executors.newSingleThreadExecutor();



        com = new ServerCom("http://users-cs.au.dk/lundtoft/pp/saveLocation.php", getApplicationContext());
        fixer = new GPSFixer(0, 0, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Button listeners
     */
    public void setDistBtnClicked(View v){
        EditText distTextInput = (EditText) findViewById(R.id.distInput);
        CheckBox accCheck = (CheckBox) findViewById(R.id.accelerometerCheck);
        int dist = Integer.parseInt(distTextInput.getText().toString());

        fixer.setDist(dist);
        if (accCheck.isChecked()) {
            fixer.startAccGPS();
            Toast.makeText(this, "GPS Accelerometer Started", Toast.LENGTH_SHORT).show();
        } else {
            fixer.startDistGPS();
            Toast.makeText(this, "GPS Dist Started", Toast.LENGTH_SHORT).show();
        }
    }

    public void setTimeBtnClicked(View v){
        EditText timeTextInput = (EditText) findViewById(R.id.timeInput);
        int delay = Integer.parseInt(timeTextInput.getText().toString());

        fixer.setTimePeriod(delay);
        fixer.startTimeGPS();
        Toast.makeText(this, "GPS Time Started", Toast.LENGTH_SHORT).show();
    }

    public void setSpeedBtnClicked(View v){
        EditText distTextInput = (EditText) findViewById(R.id.distInput);
        EditText maxTextInput = (EditText) findViewById(R.id.maxInput);
        int dist = Integer.parseInt(distTextInput.getText().toString());
        int speed = Integer.parseInt(maxTextInput.getText().toString());

        fixer.setDist(dist);
        fixer.setSpeed(speed);
        fixer.startSpeedGPS();
        Toast.makeText(this, "GPS Speed Started", Toast.LENGTH_SHORT).show();
    }

    public void stopTimeBtnClicked(View v){
        fixer.stopGPS();
        Toast.makeText(this, "GPS Stopped", Toast.LENGTH_SHORT).show();
    }
}
