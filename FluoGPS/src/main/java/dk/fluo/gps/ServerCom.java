package dk.fluo.gps;

import android.content.Context;
import android.net.http.*;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;

/**
 * Created by hagabrth on 9/25/13.
 */
public class ServerCom {

    /**
     * Properties
     */
    private String ipAddress;
    private int port;
    private Context context;

    /**
     * Constructors
     */
    public ServerCom(String ipAddress, int port, Context context){
        this.ipAddress = ipAddress;
        this.port = port;
        this.context = context;
    }

    /**
     * Com Methods
     */
    public void sendFixToServer(String position){
       new RequestTask().execute("http://api.greenticket.dk/events/27");
    }

    class RequestTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... uri) {
            /*HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            String responseString = null;*/
            /*
            try {
                response = httpclient.execute(new HttpGet("http://api.greenticket.dk/events/27"));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                out.close();
                responseString = out.toString();
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
            } catch (ClientProtocolException e) {
                //TODO Handle problems..
            } catch (IOException e) {
                //TODO Handle problems..
            }
            */

            //Toast.makeText(context, "Gps Disabled", Toast.LENGTH_LONG).show();

            return "hej";
        }

        @Override
        protected void onPostExecute(String result) {

        }
    }

}
