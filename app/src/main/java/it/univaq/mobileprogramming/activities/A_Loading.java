package it.univaq.mobileprogramming.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import it.univaq.mobileprogramming.R;
import it.univaq.mobileprogramming.database.D_Database;
import it.univaq.mobileprogramming.utility.U_Location;
import it.univaq.mobileprogramming.utility.U_Download;
import it.univaq.mobileprogramming.utility.U_Vars;

public class A_Loading extends AppCompatActivity // <- to ensure backward compability
{
    private U_Download download;
    private U_Location location;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //Link this class(context) to a specific XML (activity_main)
    
        this.askForAllPermissions();
    }
    
    
    @Override
    public void onResume()
    {
        super.onResume();
        this.location = new U_Location(this);
        this.download = new U_Download(this);
    }
    
    
    @Override
    protected void onDestroy()
    {
        this.download.unregisterReceiver();
        this.location.unregisterReceiver();
        location.googlePlayServices.disconnect();
        D_Database.closeConnection();
        super.onDestroy();
    }
    
    
    private void askForAllPermissions()
    {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            String askPermissions[] = {Manifest.permission.ACCESS_COARSE_LOCATION};
            ActivityCompat.requestPermissions(this, askPermissions, U_Vars.requestCode);
        }
    }
}