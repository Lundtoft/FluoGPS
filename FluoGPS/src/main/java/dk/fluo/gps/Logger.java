package dk.fluo.gps;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

/**
 * Created by hagbarth on 9/25/13.
 */
public class Logger {

    /**
     * Properties
     */
    private Context context;
    private Activity activity;

    /**
     * Constructors
     */
    public Logger(Activity activity){
        this.activity = activity;
        this.context = activity.getApplicationContext();
    }


    /**
     * IO Methods
     */
    public void writeFile(String data, String filename){
        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(data.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void readFile(String filename){
        for (File file : context.getFilesDir().listFiles()) {
            Log.e("File:", file.toString());
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.openFileInput(filename)));
            String fileContents = "";
            String line;
            while ((line = reader.readLine()) != null) {
                fileContents += line;
            }

            AlertDialog.Builder alert = new AlertDialog.Builder(activity);
            alert.setTitle("Number of fixes");
            alert.setMessage(fileContents);
            alert.setPositiveButton("OK", null);
            alert.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
