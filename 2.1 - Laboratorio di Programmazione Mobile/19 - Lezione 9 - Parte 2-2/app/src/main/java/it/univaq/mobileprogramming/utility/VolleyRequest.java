package it.univaq.mobileprogramming.utility;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * MobileProgramming2018
 * Created by leonardo on 30/11/2018.
 */
public class VolleyRequest {

    private RequestQueue queue;

    private static VolleyRequest instance = null;

    public static VolleyRequest getInstance(Context context){
        return instance == null ? instance = new VolleyRequest(context) : instance;
    }

    private VolleyRequest(Context context){

        queue = Volley.newRequestQueue(context);
    }

    public void downloadCities(Response.Listener<String> listener){

        StringRequest request = new StringRequest(
                StringRequest.Method.GET,
                "http://www.bitesrl.it/test/bite/corso/script.php",
                 listener,
                null);
        queue.add(request);
    }
}
