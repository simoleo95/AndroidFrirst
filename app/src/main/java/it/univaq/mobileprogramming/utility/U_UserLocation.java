package it.univaq.mobileprogramming.utility;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class U_UserLocation
{
    private LocationRequest request;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private String userCity;
    
    public U_UserLocation(final Context context)
    {
        locRequest();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        retrieveCurrentLocation(context);
    }
    
    
    //Part 1 - Battery
    //https://developer.android.com/guide/topics/location/battery
    
    public void locRequest()
    {
        request = new LocationRequest();
        request.setInterval(2000);
        request.setMaxWaitTime(5000);
        request.setFastestInterval(1000);
        request.setPriority(LocationRequest.PRIORITY_LOW_POWER);
    }
    
    
    //Part 2
    //https://developer.android.com/training/location/retrieve-current
    
    /**
     * https://developer.android.com/training/location/retrieve-current
     * @param context Main Context
     */
    private void retrieveCurrentLocation(final Context context)
    {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            String askPermissions[] = {Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions((Activity) context, askPermissions, 1);
            return;
        }
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener((Activity) context, new OnSuccessListener<Location>()
                {
                    @Override
                    public void onSuccess(Location location)
                    {
                        if(location != null)
                        {
                            reverseGeocode(context, location.getLatitude(), location.getLongitude());
                        }
                    }
                });
    }
    
    private void reverseGeocode(Context context, Double latitude, Double longitude)
    {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses;
        try
        {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if(addresses != null)
            {
                if(addresses.size() > 0)
                {
                    setUserCity(addresses.get(0).getLocality());
                }
            }
        }
        catch(IOException exception)
        {
            setUserCity("NOT FOUND, SORRY!");
        }
    }
    
    
    //Part 3 - Change Location Settings
    //https://developer.android.com/training/location/change-location-settings
    
    private void updateLocation(final Context context)
    {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(request);
        SettingsClient client = LocationServices.getSettingsClient(context);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        
        task.addOnSuccessListener((Activity) context, new OnSuccessListener<LocationSettingsResponse>()
        {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse)
            {
        
            }
        });
        task.addOnFailureListener((Activity) context, new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                if((e instanceof ResolvableApiException))
                {
                    try
                    {
                        ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                        resolvableApiException.startResolutionForResult((Activity) context, 0x1);
                    }
                    catch(IntentSender.SendIntentException send)
                    {
                        ;
                    }
                }
            }
        });
    }
    
    
    public String getUserCity()
    {
        return userCity;
    }
    
    public void setUserCity(String userCity)
    {
        this.userCity = userCity;
    }
}