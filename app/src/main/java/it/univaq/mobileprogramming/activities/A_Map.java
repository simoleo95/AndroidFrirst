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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import it.univaq.mobileprogramming.R;
import it.univaq.mobileprogramming.entity.E_Farmacia;
import it.univaq.mobileprogramming.utility.U_Location2;
import it.univaq.mobileprogramming.utility.U_Vars;

public class A_Map extends FragmentActivity implements OnMapReadyCallback
{
    private GoogleMap mMap;
    private Marker marker;
    private final int notification_id = 1;
    private double default_LAT = 0;
    private double default_LON = 0;
    
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
        this.showPharms();
    }
    
    /**
     * Zoom on the user city
     */
    private void zoomOnCity()
    {
        LatLng cityLL = new LatLng(
                getIntent().getDoubleExtra("lat", this.default_LAT),
                getIntent().getDoubleExtra("lon", this.default_LON));
//                getIntent().getDoubleExtra("lat", 42.3498479), //L'Aquila LAT
//                getIntent().getDoubleExtra("lon", 13.3995091));//L'Aquila LON
        
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cityLL, 15));
    }
    
    private void showPharms()
    {
        E_Farmacia farmacia;
        for(int i = 0; i < U_Vars.farmacieUtente.size(); i++)
        {
            farmacia = U_Vars.farmacieUtente.get(i);
            LatLng marker2add = new LatLng(farmacia.getLat_Double(), farmacia.getLon_Double());
            
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(marker2add);
            
            markerOptions.title(farmacia.getFarmacia());
            markerOptions.snippet(farmacia.getIndirizzo());
            
            if(marker2add.latitude == getIntent().getDoubleExtra("lat", this.default_LAT)
                    && marker2add.longitude == getIntent().getDoubleExtra("lon", this.default_LON))
            {
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
                this.zoomOnCity();
            }
            else
            {
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            }
            
            mMap.addMarker(markerOptions);
        }
    }
}
