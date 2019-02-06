package it.univaq.mobileprogramming.activity;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import it.univaq.mobileprogramming.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private final int notification_id = 1;

    private MyListener listener = new MyListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if(mapFragment != null) mapFragment.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startGPS();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopGPS();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                startGPS();
            } else {
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        String city = getIntent().getStringExtra("cityName");
        String region = getIntent().getStringExtra("regionName");
        double latitude = getIntent().getDoubleExtra("latitude", 0);
        double longitude = getIntent().getDoubleExtra("longitude", 0);

        if(city == null || region == null) return;

        LatLng position = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(position).title(String.format("%s %s", city, region)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
    }

    /**
     * Start Location Service by GPS and Network provider.
     */
    private void startGPS(){

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
        }   else {
            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{ Manifest.permission.ACCESS_FINE_LOCATION }, 1);
        }
    }

    /**
     * Stop Location service.
     */
    private void stopGPS(){

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("myChannel", "Il Mio Canale", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setLightColor(Color.argb(255, 255, 0, 0));
            if(notificationManager != null) notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                getApplicationContext(), "myChannel");
        builder.setContentTitle(getString(R.string.app_name));
        builder.setSmallIcon(R.drawable.ic_stat_name);
        builder.setContentText(message);
        builder.setAutoCancel(true);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                getApplicationContext(), 0, intent, 0);

        builder.setContentIntent(pendingIntent);

        Notification notify = builder.build();
        if(notificationManager != null) notificationManager.notify(notification_id, notify);
    }

    /**
     * Location Listener
     */
    private class MyListener implements LocationListener{

        private Marker marker;

        @Override
        public void onLocationChanged(Location location) {

            if(marker == null) {
                marker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(location.getLatitude(), location.getLongitude()))
                        .title("My Location")
                );
            } else {
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
