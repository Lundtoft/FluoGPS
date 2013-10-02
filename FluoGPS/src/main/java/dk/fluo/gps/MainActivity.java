package dk.fluo.gps;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ServerCom com = new ServerCom("http://users-cs.au.dk/lundtoft/pp/saveLocation.php", getApplicationContext());
        GPSFixer fixer = new GPSFixer(10, 10, this);
        ArrayList<Double> position = fixer.getPosition();


        com.sendFixToServer(position.get(0), position.get(1), System.currentTimeMillis() / 1000L);

        Log.e("Lon: ", "" + position.get(0));
        Log.e("Lat: ", "" + position.get(1));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
