package it.univaq.mobileprogramming;

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
public class RequestService extends IntentService
{
    public RequestService() {
        super("RequestService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        int action = intent.getIntExtra("action", -1);
        switch (action){
            case 0:
                downloadData();
                break;
        }
    }

    private void downloadData(){

        String address = "http://www.bitesrl.it/test/bite/corso/script.php";

        URL url = null;
        try {
            url = new URL(address);
        } catch(MalformedURLException e) {
            e.printStackTrace();
        }

        if(url == null) return;

        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int code = connection.getResponseCode();
            if(code == HttpURLConnection.HTTP_OK){
                InputStream is = connection.getInputStream();

                BufferedInputStream bis = new BufferedInputStream(is);

                byte[] buffer = new byte[1024];
                int length = 0;

                StringBuilder sb = new StringBuilder();

                while((length = bis.read(buffer)) > 0){
                    sb.append(new String(buffer));
                }

                String response = sb.toString();
                if(!response.isEmpty()) {
                    Intent intent = new Intent("Pippo");
                    intent.putExtra("response", response);

                    LocalBroadcastManager.getInstance(getApplicationContext())
                            .sendBroadcast(intent);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(connection != null) connection.disconnect();
        }
    }
}
