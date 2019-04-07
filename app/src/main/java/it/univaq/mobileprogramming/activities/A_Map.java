package it.univaq.mobileprogramming.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import it.univaq.mobileprogramming.R;
import it.univaq.mobileprogramming.entity.E_Farmacia;
import it.univaq.mobileprogramming.utility.U_Vars;

public class A_Map extends FragmentActivity implements OnMapReadyCallback
{
    private GoogleMap mMap;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);
        
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if(mapFragment != null)
        {
            mapFragment.getMapAsync(this);
        }
    }
    
    
    /**
     * Manipulates the map once available
     */
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
    
        if(ActivityCompat.checkSelfPermission(this,
                                              Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        mMap.setMyLocationEnabled(true);
        this.showSelected();
//        this.showPharms();
    }
    
    
    /**
     * Display a marker on the selected pharmacy
     */
    private void showSelected()
    {
        LatLng marker2add = new LatLng(U_Vars.selectedPharm.getLat_Double(), U_Vars.selectedPharm.getLon_Double());
    
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(marker2add);
        markerOptions.title(U_Vars.selectedPharm.getFarmacia());
        markerOptions.snippet(U_Vars.selectedPharm.getIndirizzo());
    
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        this.zoomOnCity();
    
        Marker marker = mMap.addMarker(markerOptions);
        marker.showInfoWindow();
    }


    /**
     * Display a marker for each Pharmacy found
     */
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
            
            if(marker2add.latitude == U_Vars.selectedPharm.getLat_Double()
                    && marker2add.longitude == U_Vars.selectedPharm.getLon_Double())
            {
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                this.zoomOnCity();
            }
            else
            {
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                markerOptions.alpha(0.70f); //70% transparency
            }
            mMap.addMarker(markerOptions);
//            Marker marker = mMap.addMarker(markerOptions);
//            marker.showInfoWindow(); //Shows the marker on the LAST pharmacy found ONLY!
        }
    }
    
    
    /**
     * Zoom on the user city
     */
    private void zoomOnCity()
    {
        LatLng cityLL = new LatLng(
                U_Vars.selectedPharm.getLat_Double(),
                U_Vars.selectedPharm.getLon_Double()
        );
        
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cityLL, 12f));
    }
}
