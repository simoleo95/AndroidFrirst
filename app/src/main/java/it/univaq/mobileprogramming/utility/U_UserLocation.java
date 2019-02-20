package it.univaq.mobileprogramming.utility;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.LocaleList;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


/**
 * THIS CLASS IS BASED ON GPS
 * IT'S RECOMMENDED TO SWITCH TO Google Play Services TO ENHANCE LOCATION AWARENESS
 */
public class U_UserLocation
{
    Context context;
    LocationCallback callback;
    
    public U_UserLocation(Context context)
    {
        this.context = context;
    }
    
    
    public void updatePosition(long updateRate_ms, long minimumUpdateRate_ms)
    {
        LocationRequest updateLocation = new LocationRequest();
        updateLocation.setInterval(updateRate_ms);
        updateLocation.setFastestInterval(minimumUpdateRate_ms);
    
        //PRIORITY_HIGH_ACCURACY            - the most precise location possible
        //PRIORITY_BALANCED_POWER_ACCURACY  - ~100m
        //PRIORITY_LOW_POWER                - ~10Km
        //PRIORITY_NO_POWER                 - you want receive location updates when available, if other apps request it
        updateLocation.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY); //We need to know the user's CITY so PRIORITY_BALANCED_POWER_ACCURACY seems a good choice
    
        LocationSettingsRequest settings = new LocationSettingsRequest
                .Builder()
                .addLocationRequest(updateLocation)
                .build();
    
        LocationServices.getSettingsClient(this.context)
                        .checkLocationSettings(settings)
                        .addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>()
                            {
                                @Override
                                public void onComplete(@NonNull Task<LocationSettingsResponse> task)
                                {
                                    try
                                    {
                                        LocationSettingsResponse response = task.getResult(ApiException.class);
                                    }
                                    catch(ApiException e)
                                    {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        );
    
        this.callback = new LocationCallback()
            {
                @Override
                public void onLocationResult(LocationResult res)
                {
                    super.onLocationResult(res);
                    Location loco = res.getLastLocation();
                }
            };
    
        FusedLocationProviderClient c = LocationServices.getFusedLocationProviderClient(this.context);
        c.requestLocationUpdates(updateLocation, this.callback, Looper.myLooper());
    }
    
    
    public void stopLocationUpdates()
    {
        FusedLocationProviderClient c = LocationServices.getFusedLocationProviderClient(this.context);
        c.removeLocationUpdates(this.callback);
    }
    
    
    private boolean GooglePlayService_isAvailable()
    {
        //CONTROLLA ANCHE QUESTA ROBA: https://developers.google.com/android/guides/permissions
        return GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this.context) == ConnectionResult.SUCCESS;
    }
    
    
    public LocationManager getLocationManager()
    {
        LocationManager manager = (LocationManager) this.context.getSystemService(Context.LOCATION_SERVICE);
        
        //    Call (la chiamata manager.requestLocationUpdates) requires permission which may be rejected by user:
        //    code should explicitly check to see if permission is available (with checkPermission) or explicitly handle a potential SecurityException
        //    Inspection info:
        //    This check scans through your code and libraries and looks at the APIs being used,
        //    and checks this against the set of permissions required to access those APIs.
        //    If the code using those APIs is called at runtime, then the program will crash.
        //    Furthermore, for permissions that are revocable (with targetSdkVersion 23),
        //    client code must also be prepared to handle the calls throwing an exception if the user rejects the request for permission at runtime.
        //    Issue id: MissingPermission  -> BISOGNA DICHIARARE I "RUN-TIME PERMISSIONS"
        if(manager != null)
        {
            LocationListener loco = this.generateListener();
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, loco);
        }
        
        return manager;
    }
    
    
    private LocationListener generateListener()
    {
        LocationListener listener = new LocationListener()
        {
            @Override
            public void onLocationChanged(Location location) { }
    
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) { }
    
            @Override
            public void onProviderEnabled(String provider) { }
    
            @Override
            public void onProviderDisabled(String provider) { }
        };
        
        return listener;
    }
}

