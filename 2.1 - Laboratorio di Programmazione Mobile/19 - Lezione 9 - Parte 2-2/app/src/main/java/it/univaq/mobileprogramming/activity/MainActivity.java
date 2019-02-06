package it.univaq.mobileprogramming.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

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
import it.univaq.mobileprogramming.database.RDatabase;
import it.univaq.mobileprogramming.utility.RequestService;
import it.univaq.mobileprogramming.utility.Settings;
import it.univaq.mobileprogramming.database.Database;
import it.univaq.mobileprogramming.model.City;
import it.univaq.mobileprogramming.utility.VolleyRequest;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private SwipeRefreshLayout swipeRefreshLayout;

    private List<City> cities = new ArrayList<>();

    private AdapterRecycler adapter;

    // The Broadcast Receiver can receive the sent intent by LocaleBroadcastManager.
    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(intent == null) return;
            String response = intent.getStringExtra(RequestService.EXTRA_SERVER_RESPONSE);
            if(response == null) return;

            // Parsing of JSON response:
            // [{"id":0,"name":"Ancona","region":"Marche","lat":43.615,"lon":13.515}, ...]
            try {
                JSONArray jsonRoot = new JSONArray(response);
                for(int i = 0; i < jsonRoot.length(); i++){

                    JSONObject item = jsonRoot.getJSONObject(i);

                    String name = item.getString("name");
                    String region = item.getString("region");
                    double latitude = item.getDouble("lat");
                    double longitude = item.getDouble("lon");

                    City city = new City();
                    city.setName(name);
                    city.setRegion(region);
                    city.setLatitude(latitude);
                    city.setLongitude(longitude);

                    // Save on Database every city
                    saveDataInDB(city);
                    cities.add(city);
                }

            } catch (JSONException e){
                e.printStackTrace();
            }

            swipeRefreshLayout.setRefreshing(false);

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

        swipeRefreshLayout = findViewById(R.id.main_swipe);
        swipeRefreshLayout.setOnRefreshListener(this);

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

        // If is the first time you open the app, do a HTTP request to download the data
        if(Settings.loadBoolean(getApplicationContext(), Settings.FIRST_TIME, true)){

            clearDataFromDB();

            downloadData();

        } else {
            // If is not the first time you open the app, get all saved data from Database

            loadDataFromDB();
        }
        Settings.save(getApplicationContext(), Settings.FIRST_TIME, false);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(Settings.loadBoolean(getApplicationContext(), Settings.SWITCH_HTTP, true)) {
            // Unregistering the receiver
            LocalBroadcastManager.getInstance(getApplicationContext())
                    .unregisterReceiver(myReceiver);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        menu.add(0, 1, 1, R.string.main_demo);
        menu.add(0, 2, 0, R.string.main_refresh);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.main_exit:
                showExitDialog();
                return true;

            case R.id.main_settings:
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
                return true;

            case 1: // OptionItem "Demo" defined in Java
                Toast.makeText(MainActivity.this, R.string.toast_demo, Toast.LENGTH_SHORT).show();
                return true;

            case 2: // OptionItem "Resfesh" defined in Java
                clearDataFromDB();

                downloadData();
                return true;

            default:
                return false;
        }
    }

    @Override
    public void onRefresh() {

        clearDataFromDB();

        downloadData();
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


    /**
     * Download Data by Volley
     */
    private void downloadData(){

        swipeRefreshLayout.setRefreshing(true);
        if(Settings.loadBoolean(getApplicationContext(), Settings.SWITCH_HTTP, true)) {

            // Registering the receiver
            LocalBroadcastManager.getInstance(getApplicationContext())
                    .registerReceiver(myReceiver, new IntentFilter(RequestService.FILTER_REQUEST_DOWNLOAD));

            // Http request by URLConnection
            Intent intentService = new Intent(getApplicationContext(), RequestService.class);
            intentService.putExtra(RequestService.REQUEST_ACTION, RequestService.REQUEST_DOWNLOAD);
            startService(intentService);
        } else {

            // Http request by Volley
            VolleyRequest.getInstance(getApplicationContext())
                    .downloadCities(new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonRoot = new JSONArray(response);
                                for (int i = 0; i < jsonRoot.length(); i++) {

                                    JSONObject item = jsonRoot.getJSONObject(i);

                                    String name = item.getString("name");
                                    String region = item.getString("region");
                                    double latitude = item.getDouble("lat");
                                    double longitude = item.getDouble("lon");

                                    City city = new City();
                                    city.setName(name);
                                    city.setRegion(region);
                                    city.setLatitude(latitude);
                                    city.setLongitude(longitude);

                                    saveDataInDB(city);
                                    cities.add(city);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            swipeRefreshLayout.setRefreshing(false);

                            // Refresh list because the adapter data are changed
                            if (adapter != null) adapter.notifyDataSetChanged();
                        }
                    });
        }
    }

    /**
     * Save city in database. The preference defines if you use SQLiteOpenHelper or RoomDatabase
     * @param city to save
     */
    private void saveDataInDB(final City city){

        // Save by SQLiteOpenHelper
        Database.getInstance(getApplicationContext()).save(city);

        // Save by RoomDatabase
        new Thread(new Runnable() {
            @Override
            public void run() {
                RDatabase.getInstance(getApplicationContext())
                        .getCityDao().save(city);
            }
        }).start();
    }

    /**
     * Load all cities from database. The preference defines if you use SQLiteOpenHelper or RoomDatabase
     */
    private void loadDataFromDB(){

        if(Settings.loadBoolean(getApplicationContext(), Settings.SWITCH_DB, true)) {
            // Get by SQLiteOpenHelper
            cities.addAll(Database.getInstance(getApplicationContext()).getAllCities());
            if(adapter != null) adapter.notifyDataSetChanged();

        } else {
            // Get by RoomDatabase

            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<City> data = RDatabase.getInstance(getApplicationContext())
                            .getCityDao().getAllCities();
                    cities.addAll(data);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(adapter != null) adapter.notifyDataSetChanged();
                        }
                    });
                }
            }).start();
        }
    }

    /**
     * Clear data from database. The preference defines if you use SQLiteOpenHelper or RoomDatabase
     */
    private void clearDataFromDB(){

        cities.clear();
        if(adapter != null) adapter.notifyDataSetChanged();

        if(Settings.loadBoolean(getApplicationContext(), Settings.SWITCH_DB, true)) {

            // Delete by SQLiteOpenHelper
            Database.getInstance(getApplicationContext()).delete();

        } else {
            // Delete by RoomDatabase

            new Thread(new Runnable() {
                @Override
                public void run() {
                    RDatabase.getInstance(getApplicationContext())
                            .getCityDao().deleteAll();
                }
            }).start();
        }
    }
}