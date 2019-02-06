package it.univaq.mobileprogramming.utility;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * MobileProgramming2018
 * Created by leonardo on 16/11/2018.
 */
public class RequestService extends IntentService {

    public static final int REQUEST_DOWNLOAD = 0;

    public static final String REQUEST_ACTION = "action";
    public static final String FILTER_REQUEST_DOWNLOAD = "filter_request_download";
    public static final String EXTRA_SERVER_RESPONSE = "extra_server_response";

    public RequestService() {
        super("RequestService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if(intent == null) return;
        int action = intent.getIntExtra(REQUEST_ACTION, -1);
        switch (action){
            case REQUEST_DOWNLOAD:
                downloadData();
                break;
        }
    }

    private void downloadData(){

        String address = "http://www.bitesrl.it/test/bite/corso/script.php";

        // Check if the address is an URL
        URL url = null;
        try {
            url = new URL(address);
        } catch(MalformedURLException e) {
            e.printStackTrace();
        }
        if(url == null) return;

        // Do the GET Request
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Read the response from the right stream
            int code = connection.getResponseCode();
            if(code == HttpURLConnection.HTTP_OK){

                InputStream is = connection.getInputStream();

                BufferedInputStream bis = new BufferedInputStream(is);
                byte[] buffer = new byte[1024];
                StringBuilder sb = new StringBuilder();
                while(bis.read(buffer) > 0) sb.append(new String(buffer));

                // Response contains the response of the server
                String response = sb.toString();
                if(!response.isEmpty()) {

                    // Send the response in broadcast to all components of only this application.
                    Intent intent = new Intent(FILTER_REQUEST_DOWNLOAD);
                    intent.putExtra(EXTRA_SERVER_RESPONSE, response);
                    LocalBroadcastManager.getInstance(getApplicationContext())
                            .sendBroadcast(intent);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Remember to disconnect the connection
            if(connection != null) connection.disconnect();
        }
    }
}
