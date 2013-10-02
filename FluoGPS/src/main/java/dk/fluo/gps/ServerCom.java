package dk.fluo.gps;

import android.content.Context;
import android.net.http.*;
import android.os.AsyncTask;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hagabrth on 9/25/13.
 */
public class ServerCom {

    /**
     * Properties
     */
    private String uri;
    private Context context;

    /**
     * Constructors
     */
    public ServerCom(String uri, Context context){
        this.uri = uri;
        this.context = context;
    }

    /**
     * Com Methods
     */
    public void sendFixToServer(double lon, double lat, long timestamp){
       Log.e("timestamp", "" + timestamp);
       new RequestTask().execute(uri, Double.toString(lon), Double.toString(lat), Long.toString(timestamp));
    }

    /**
     * Async task
     */
    class RequestTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... parameters) {
            Looper.prepare();
            String responseString = null;
            String uri = parameters[0];
            String lon = parameters[1];
            String lat = parameters[2];
            String timestamp = parameters[3];

            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;
            HttpPost post = new HttpPost(uri);
            try {
                List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                pairs.add(new BasicNameValuePair("lon", lon));
                pairs.add(new BasicNameValuePair("lat", lat));
                pairs.add(new BasicNameValuePair("timestamp", timestamp));
                post.setEntity(new UrlEncodedFormEntity(pairs ));

                response = httpclient.execute(post);
                StatusLine statusLine = response.getStatusLine();
                if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    response.getEntity().writeTo(out);
                    out.close();
                    responseString = out.toString();
                } else{
                    //Closes the connection.
                    response.getEntity().getContent().close();
                    responseString = statusLine.toString();
                }
            } catch (ClientProtocolException e) {
                //TODO Handle problems..
            } catch (IOException e) {
                //TODO Handle problems..
            }

            return responseString;
        }

        @Override
        protected void onPostExecute(String result) {
            if( result != null ){
                if(result.equals("success")) Toast.makeText(context, "Position Sent", Toast.LENGTH_SHORT).show();
                else Toast.makeText(context, "An error happened", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(context, "An error happened", Toast.LENGTH_SHORT).show();

        }
    }

}
