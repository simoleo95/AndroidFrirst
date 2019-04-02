package it.univaq.mobileprogramming.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import it.univaq.mobileprogramming.R;
import it.univaq.mobileprogramming.database.D_Database;
import it.univaq.mobileprogramming.utility.U_Location;
import it.univaq.mobileprogramming.utility.U_Download;

public class A_Loading extends AppCompatActivity // <- to ensure backward compability
{
    private U_Download download;
    private U_Location location;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //Link this class(context) to a specific XML (activity_main)
        
        this.location = new U_Location(this);
        
        this.download = new U_Download(this);
    }
    
    @Override
    protected void onStart()
    {
        super.onStart();
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
}