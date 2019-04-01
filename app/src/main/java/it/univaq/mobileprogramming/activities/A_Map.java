package it.univaq.mobileprogramming.activities;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import it.univaq.mobileprogramming.R;
import it.univaq.mobileprogramming.utility.U_Location2;
import it.univaq.mobileprogramming.utility.U_Vars;

public class A_Map extends FragmentActivity implements OnMapReadyCallback, U_Location2.LocationListener
{
    private GoogleMap mMap;
    private Marker marker;
    private final int notification_id = 1;
    private MyListener listener = new MyListener();
    private U_Location2 locationService;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);
        
        System.out.println("SONO NELLA MAPPAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if(mapFragment != null)
        {
            mapFragment.getMapAsync(this);
        }
        else
        {
            System.out.println("HA RITORNATO null DI NUOVOOOoooooOOOoOooOOooooOOOOooooOOOooOoOOoooOOooOoOoO!");
        }
        
        
        System.out.println("STUPIDA CITTà: " + U_Vars.userCity);
        System.out.println("STUPIDA LAT: " + U_Vars.farmacieUtente.get(0).getLat_Double());
        System.out.println("STUPIDA LON: " + U_Vars.farmacieUtente.get(0).getLon_Double());
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    
//        if(U_Vars.loadBoolean(getApplicationContext(), U_Vars.SWITCH_LOCATION, true))
//        {
//            startGPS();
//            System.out.println("Avviato il GPS!!!");
//            System.out.println("Avviata la Location2!!! P1");
////            locationService = new U_Location2();
////            locationService.onCreate(this, this);
////            locationService.requestLocationUpdates(this);
//        }
//        else
//        {
//            System.out.println("Avviata la Location2!!! P2");
////            locationService = new U_Location2();
////            locationService.onCreate(this, this);
//            locationService.requestLocationUpdates(this);
//        }
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        
//        if(U_Vars.loadBoolean(getApplicationContext(), U_Vars.SWITCH_LOCATION, true)) {
//            stopGPS();
//        }
//        else
//        {
//            locationService.stopLocationUpdates(this);
//            locationService.unregisterReceiver();
//        }
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        if(requestCode == 1)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                startGPS();
            }
            else
            {
                finish();
            }
        }
    }
    
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
    
//        String city = getIntent().getStringExtra("cityName");
//        String region = getIntent().getStringExtra("regionName");
//        double latitude = getIntent().getDoubleExtra("latitude", 0);
//        double longitude = getIntent().getDoubleExtra("longitude", 0);
        String city = U_Vars.userCity;
        
        double latitude = U_Vars.farmacieUtente.get(0).getLat_Double();
        double longitude = U_Vars.farmacieUtente.get(0).getLon_Double();
        System.out.println("HO TROVATO UNA DANNATA CITTà DA MOSTRARE SULLA MAPPA!!!");
        System.out.println("DANNATA CITTà: " + city);
        System.out.println("DANNATA LAT: " + latitude);
        System.out.println("DANNATA LON" + longitude);
        
        if(city == null) return;
        
        LatLng position = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(position).title(city));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
    }
    
    /**
     *  This is the lister used only by GooglePlayService
     * @param location
     */
    @Override
    public void onLocationChanged(Location location)
    {
        if(marker == null)
        {
            marker = mMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(location.getLatitude(), location.getLongitude()))
                                            .title("My Location")
            );
        }
        else
        {
            marker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
        }
        notifyLocation(location.getLatitude() + ", " + location.getLongitude());
    }
    
    /**
     * Start Location Service by GPS and Network provider.
     */
    private void startGPS()
    {
        System.out.println("QUI NON CI DOVRESTI ARRIVARE PERCHè ATTIVI IL GPS FARLOCCO!");
        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        
        int check = ContextCompat
                .checkSelfPermission(getApplicationContext(),
                                     Manifest.permission.ACCESS_FINE_LOCATION);
        if(check == PackageManager.PERMISSION_GRANTED) {
            
            notifyLocation("GPS ATTIVO");
            if(manager != null) {
                manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                               0,
                                               0,
                                               listener);
                
                manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                                               0,
                                               0,
                                               listener);
            }
        }
        else
        {
            ActivityCompat.requestPermissions(A_Map.this,
                                              new String[]{ Manifest.permission.ACCESS_FINE_LOCATION }, 1);
        }
    }
    
    /**
     * Stop Location service.
     */
    private void stopGPS()
    {
        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if(manager != null) manager.removeUpdates(listener);
        
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if(notificationManager != null) notificationManager.cancel(notification_id);
    }
    
    /**
     * Publish a notify.
     *
     * @param message
     */
    private void notifyLocation(String message) {
        
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel("myChannel", "Il Mio Canale", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setLightColor(Color.argb(255, 255, 0, 0));
            if(notificationManager != null) notificationManager.createNotificationChannel(channel);
        }
        
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                getApplicationContext(), "myChannel");
        builder.setContentTitle(getString(R.string.app_name));
//        builder.setSmallIcon(R.drawable.ic_stat_name);
        builder.setContentText(message);
        builder.setAutoCancel(true);
        
        Intent intent = new Intent(getApplicationContext(), A_Map.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                getApplicationContext(), 0, intent, 0);
        
        builder.setContentIntent(pendingIntent);
        
        Notification notify = builder.build();
        if(notificationManager != null) notificationManager.notify(notification_id, notify);
    }
    
    /**
     * Location Listener
     */
    private class MyListener implements LocationListener
    {
        @Override
        public void onLocationChanged(Location location)
        {
            if(marker == null)
            {
                marker = mMap.addMarker(new MarkerOptions()
                                                .position(new LatLng(location.getLatitude(), location.getLongitude()))
                                                .title("My Location")
                );
            }
            else
            {
                marker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
            }
            notifyLocation(location.getLatitude() + ", " + location.getLongitude());
        }
        
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
        
        @Override
        public void onProviderEnabled(String provider) {}
        
        @Override
        public void onProviderDisabled(String provider) {}
    }
}
