package it.univaq.mobileprogramming.utility;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import it.univaq.mobileprogramming.MyReceiver;

/**
 * MobileProgramming2018
 * Created by leonardo on 18/12/2018.
 */
public class U_Location2
{
    private FusedLocationProviderClient providerClient;
    private LocationListener listener;
    private Context context;
    private MyReceiver intent;
    
    public Double lat;
    public Double lon;
    
    public void onCreate(Activity activity, LocationListener listener)
    {
        providerClient = LocationServices.getFusedLocationProviderClient(activity);
        this.listener = listener;
        this.context = activity;
        this.intent = new MyReceiver();
        LocalBroadcastManager.getInstance(this.context)
                .registerReceiver(this.intent, new IntentFilter(U_Vars.location_Action));
        
    }
    
    
    private LocationCallback locationCallback = new LocationCallback()
    {
        @Override
        public void onLocationResult(LocationResult locationResult)
        {
            super.onLocationResult(locationResult);
            if(listener != null)
            {
                listener.onLocationChanged(locationResult.getLastLocation());
                findUserCity(locationResult.getLastLocation());
            }
        }
        
        @Override
        public void onLocationAvailability(LocationAvailability locationAvailability)
        {
            super.onLocationAvailability(locationAvailability);
            System.out.println("Is location available? " + locationAvailability.isLocationAvailable());
        }
    };
    
    
    /**
     * Reverse geocode the user location (lat, lon) into its current city
     * @param loco Current user location
     */
    private void findUserCity(Location loco)
    {
        this.lat = loco.getLatitude();
        this.lon = loco.getLongitude();
        Geocoder geo = new Geocoder(this.context, Locale.getDefault());
        List<Address> addresses;
        try
        {
            addresses = geo.getFromLocation(lat, lon, 1);
            if(addresses != null && addresses.size() > 0)
            {
                U_Vars.userCity = addresses.get(0).getLocality();
                U_Vars.save(this.context, U_Vars.USER_CITY, U_Vars.userCity);
                System.out.println("Città trovata: " + U_Vars.userCity);
//                LocalBroadcastManager.getInstance(this.context)
//                        .sendBroadcast(new Intent(U_Vars.location_Action));
            }
        }
        catch(IOException e)
        {
            return;
        }
    }
    
    /**
     * To be called onDestroy. See the A_Map activity
     */
    public void unregisterReceiver()
    {
        LocalBroadcastManager.getInstance(this.context)
                .unregisterReceiver(this.intent);
    }
    
    /**
     * Check if the Google Play Services are available.
     *
     * @param context of your application
     * @return true if they are available or false otherwise
     */
    public boolean areGoogleServicesAvailable(Context context)
    {
        return GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS;
    }
    
    
    /**
     * Request locations updates.
     *
     * @param context the context of the application
     * @return true if the permissions are granted and api is available or false otherwise
     */
    public boolean requestLocationUpdates(Context context)
    {
        boolean grantedFineLocation   = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)   == PackageManager.PERMISSION_GRANTED;
        boolean grantedCoarseLocation = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        if(grantedFineLocation || grantedCoarseLocation)
        {
            if(areGoogleServicesAvailable(context))
            {
                LocationRequest request = new LocationRequest();
                request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                request.setInterval(1000);
                request.setFastestInterval(500);
                
                providerClient.requestLocationUpdates(request, locationCallback, null);
                return true;
            }
        }
        return false;
    }
    
    public void stopLocationUpdates(Context context)
    {
        if(areGoogleServicesAvailable(context))
        {
            providerClient.removeLocationUpdates(locationCallback);
        }
    }
    
    /**
     * Get last known location.
     *
     * @param activity the instance of the Activity
     * @return true if the permissions are granted or false otherwise
     */
    public boolean requestLastLocation(Activity activity)
    {
    
        boolean grantedFineLocation   = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)   == PackageManager.PERMISSION_GRANTED;
        boolean grantedCoarseLocation = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        if(grantedFineLocation || grantedCoarseLocation)
        {
            if(areGoogleServicesAvailable(activity))
            {
                providerClient.getLastLocation()
                        .addOnSuccessListener(activity, new OnSuccessListener<Location>()
                        {
                            @Override
                            public void onSuccess(Location location)
                            {
                                if (listener != null)
                                {
                                    listener.onLocationChanged(location);
                                    findUserCity(location);
                                }
                            }
                        });
            }
            return true;
        }
        return false;
    }
    
    
    
    public interface LocationListener
    {
        void onLocationChanged(Location location);
    }
}
