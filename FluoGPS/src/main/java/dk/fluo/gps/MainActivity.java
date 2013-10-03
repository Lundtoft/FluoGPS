package dk.fluo.gps;

import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
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
        fixer = new GPSFixer(10, 10, this);
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

        Toast.makeText(this, distTextInput.getText(), Toast.LENGTH_LONG).show();
    }

    public void setTimeBtnClicked(View v){
        timerFuture = executorService.submit(timer);
        handler = new Handler();
        handler.post(timer);
    }

    public void stopTimeBtnClicked(View v){
        timerFuture.cancel(true);
        handler.removeCallbacks(timer);
    }

    /**
     * Runnables
     */

    {
        timer = new Runnable() {

            @Override
            public void run(){
                //Send position to server every user defines interval
                ArrayList<Double> position = fixer.getPosition();
                com.sendFixToServer(position.get(0), position.get(1), System.currentTimeMillis() / 1000L);

                EditText timeTextInput = (EditText) findViewById(R.id.timeInput);
                int delay = Integer.parseInt(timeTextInput.getText().toString());
                handler.postDelayed(this, delay);
            };

        };
    }
}
