package it.univaq.mobileprogramming.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import it.univaq.mobileprogramming.activity.adapter.AdapterRecycler;
import it.univaq.mobileprogramming.R;
import it.univaq.mobileprogramming.utility.RequestService;
import it.univaq.mobileprogramming.utility.Settings;
import it.univaq.mobileprogramming.database.Database;
import it.univaq.mobileprogramming.model.City;

public class MainActivity extends AppCompatActivity {

    private List<City> cities = new ArrayList<>();

    private AdapterRecycler adapter;

    // The Broadcast Receiver can receive the sent intent by LocaleBroadcastManager.
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent == null) return;
            String response = intent.getStringExtra("response");
            if(response == null) return;

            // Parsing of JSON response:
            // [{"id":0,"name":"Ancona","region":"Marche","lat":43.615,"lon":13.515}, ...]
            try {
                JSONArray jsonRoot = new JSONArray(response);
                for(int i = 0; i < jsonRoot.length(); i++){

                    JSONObject item = jsonRoot.getJSONObject(i);

                    String name = item.getString("name");
                    String region = item.getString("region");

                    City city = new City();
                    city.setName(name);
                    city.setRegion(region);

                    // Save on Database every city
                    Database.getInstance(getApplicationContext()).save(city);
                    cities.add(city);
                }

            } catch (JSONException e){
                e.printStackTrace();
            }

            // Refresh list because the adapter data are changed
            if(adapter != null) adapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new AdapterRecycler(cities);
        RecyclerView list = findViewById(R.id.main_list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        TextView text = findViewById(R.id.main_text);
        long time = Settings.loadLong(getApplicationContext(), Settings.LAST_ACCESS, -1);
        if(time != -1){

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS", Locale.getDefault());
            String date = format.format(new Date(time));
            text.setText(date);
        }
        Settings.save(getApplicationContext(), Settings.LAST_ACCESS, System.currentTimeMillis());
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Registering the receiver
        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(myReceiver, new IntentFilter(RequestService.FILTER_REQUEST_DOWNLOAD));

        // If is the first time you open the app, do a HTTP request to download the data
        if(Settings.loadBoolean(getApplicationContext(), Settings.FIRST_TIME, true)){
            Intent intentService = new Intent(getApplicationContext(), RequestService.class);
            intentService.putExtra(RequestService.REQUEST_ACTION, RequestService.REQUEST_DOWNLOAD);
            startService(intentService);
        } else {
            // If is not the first time you open the app, get all saved data from Database
            cities.addAll(Database.getInstance(getApplicationContext()).getAllCities());

            if(adapter != null) adapter.notifyDataSetChanged();
        }
        Settings.save(getApplicationContext(), Settings.FIRST_TIME, false);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Unregistering the receiver
        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(myReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        menu.add(0, 1, 0, R.string.main_demo);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.main_exit:
                showExitDialog();
                return true;

            case 1: // OptionItem "Demo" defined in Java
                Toast.makeText(MainActivity.this, R.string.toast_demo, Toast.LENGTH_SHORT).show();
                return true;

            default:
                return false;
        }
    }

    /**
     * Show a dialog when the user click on exit
     */
    private void showExitDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.dialog_title);
        builder.setMessage(R.string.dialog_message);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}