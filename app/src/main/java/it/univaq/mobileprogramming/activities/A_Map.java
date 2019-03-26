package it.univaq.mobileprogramming.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

public class A_Map extends AppCompatActivity implements OnMapReadyCallback
{
    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
    }
    
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        addThesePharms();
    }
    
    private void addThesePharms()
    {
    
    }
}
