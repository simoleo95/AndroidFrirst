package it.univaq.mobileprogramming.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
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

import it.univaq.mobileprogramming.activities.A_Loading;

/**
 * This class enables to connect and disconnect from Google Play Services
 * It should be instantiated on A_Loading.onStart() calling googlePlayServices.connect()
 * and then onStop() calling googlePlayServices.disconnect()
 */
public class U_Location extends Activity
{
    Context context;
    public GoogleApiClient googlePlayServices;
    private FusedLocationProviderClient locationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private long gpsUpdateInterval;
    private long gpsFastestUpdateInterval;
    private int gpsPriority;
    private Location currentLocation;
    
    private Double latitudine;
    private Double longitudine;
    private Location userLocation = new Location("Creator");
    public static String userCurrentCity = "";
    private U_MyReceiver receiveIntent = new U_MyReceiver();

    public U_Location(Context context)
    {
        this.context = context;
        this.setLocationRequest();
        this.locationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        googlePlayServices = getGooglePlayServices();
        googlePlayServices.connect();
        //getUserCurrentLocation();
    }
    
    
    /**
     * Find user's userCurrentCity from its latitude and longitude
     */
    private void findCity()
    {
        //Source: https://stackoverflow.com/questions/28852317/android-how-to-retrieve-current-location-using-google-play-services
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        List<Address> addresses;
        
        try
        {
            addresses = gcd.getFromLocation(latitudine, longitudine, 1);
            if(addresses != null)
            {
                if(addresses.size() > 0)
                {
                    setUserCurrentCity(addresses.get(0)
                            .getLocality());
                }
            }
        }
        catch(IOException e)
        {
            return;
        }
    }
    
//PART 1 - USER LAST KNOWN LOCATION
//Source: https://developer.android.com/training/location/retrieve-current
    
    /**
     * Instantiate the client needed to connect and disconnect to/from Google Play Services
     *
     * @return client handling connection to Google Play Services
     */
    private GoogleApiClient getGooglePlayServices()
    {
        GoogleApiClient client = new GoogleApiClient.Builder(this.context)
                .addConnectionCallbacks(neededCallbacks)
                .addOnConnectionFailedListener(connectionFailed)
                .addApi(LocationServices.API)
                .build();
        return client;
    }


    /**
     * Add necessary connection callback when creating the connection client
     */
    private GoogleApiClient.ConnectionCallbacks neededCallbacks = new GoogleApiClient.ConnectionCallbacks()
    {
        /**
         * Get the latest user's known position
         *
         * @param bundle the bundle
         */
        @Override
        public void onConnected(@Nullable Bundle bundle)
        {
            lastUserLocation();
        }
        
        @Override
        public void onConnectionSuspended(int i) {}
    };
    
    
    /**
     * Enable to get user's latitude and longitude.
     * Moreover it reverse-geocode the user position to detect the current user's userCurrentCity via findCity()
     */
    private void lastUserLocation()
    {
        try
        {
            this.locationProviderClient
                    .getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>()
                    {
                        @Override
                        public void onSuccess(Location location)
                        {
                            System.out.println(location);
                            if(location != null)
                            {
                                currentLocation = location;
                                setLatitudine(location.getLatitude());
                                setLongitudine(location.getLongitude());
                                userLocation.setLatitude(location.getLatitude());
                                userLocation.setLongitude(location.getLongitude());
    
                                findCity();
                            }
                        }
                    });
        }
        catch(SecurityException s)
        {
            Intent intent = new Intent(context, A_Loading.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            finish(); //Eccezione! Chiude l'activity corrente e torna a quella iniziale
            context.startActivity(intent);
        }
    }
    
    
    /**
     * Add a listener to whenever the connection to Google Play Services fails
     */
    private GoogleApiClient.OnConnectionFailedListener connectionFailed = new GoogleApiClient.OnConnectionFailedListener()
    {
        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}
    };
//
//
////PART 2 - CHANGE LOCATION SETTINGS
////Source: https://developer.android.com/training/location/change-location-settings
//
//    public void getUserCurrentLocation()
//    {
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
//                .addLocationRequest(this.getLocationRequest());
//
//        SettingsClient client = LocationServices.getSettingsClient(context);
//        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
//
//        task.addOnSuccessListener((Activity) context, new OnSuccessListener<LocationSettingsResponse>()
//        {
//            @Override
//            public void onSuccess(LocationSettingsResponse locationSettingsResponse)
//            {
//                // All location settings are satisfied.
//                // The client can initialize location requests here.
//
//
//                //PART 3 - RECEIVE LOCATION UPDATES
//                //Source: https://developer.android.com/training/location/receive-location-updates
//
//                createLocationCallback();
//
//                try
//                {
//                    locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
//                }
//                catch(SecurityException s)
//                {
//                    Intent intent = new Intent(context, A_Loading.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    finish(); //Eccezione! Chiude l'activity corrente e torna a quella iniziale
//                    context.startActivity(intent);
//                }
//            }
//        });
//
//        task.addOnFailureListener((Activity) context, new OnFailureListener()
//        {
//            @Override
//            public void onFailure(@NonNull Exception e)
//            {
//                if(e instanceof ResolvableApiException)
//                {
//                    try
//                    {
//                        ResolvableApiException res = (ResolvableApiException) e;
//                        res.startResolutionForResult((Activity) context, 0x1); //REQUEST_CHECK_SETTINGS); //https://stackoverflow.com/questions/31572323/what-is-the-value-of-request-check-settings
//                    }
//                    catch(IntentSender.SendIntentException s)
//                    {
//                        //Ignore it?
//                    }
//                }
//            }
//        });
//    }
//
//
//    /**
//     * Invoked by the Fused Location Provider to update the user location
//     * Source: https://developer.android.com/training/location/receive-location-updates#callback
//     */
//    public void createLocationCallback()
//    {
//        this.locationCallback = new LocationCallback()
//        {
//            @Override
//            public void onLocationResult(LocationResult locationResult)
//            {
//                super.onLocationResult(locationResult);
//                if(locationResult == null)
//                {
//                    return;
//                }
//                for(Location location : locationResult.getLocations())
//                {
//                    latitudine = location.getLatitude();
//                    longitudine = location.getLongitude();
//                    findCity();
//                    System.out.println("Location 2: " + location.toString());
//                    System.out.println("LAT 2: " + location.getLatitude());
//                    System.out.println("LON 2: " + location.getLongitude());
//                    System.out.println("CITTà TROVATA 2: " + U_Vars.userCity);
//                }
//            }
//        };
//    }
//
    
    public LocationRequest getLocationRequest()
    {
        return locationRequest;
    }
    
    public void setLocationRequest()
    {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(500);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        this.locationRequest = locationRequest;
    }
    
    private void sendLocationAcquired_Intent()
    {
        String broadcastAction = U_Vars.location_Action;
        
        //Register the BroadcastManager
        LocalBroadcastManager.getInstance(context)
                .registerReceiver(this.receiveIntent, new IntentFilter(broadcastAction));
    
        //Send Intent
        LocalBroadcastManager.getInstance(context)
                .sendBroadcast(new Intent(broadcastAction));
    }
    
    public void unregisterReceiver()
    {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(this.receiveIntent);
    }
    
    
    public void setLatitudine(Double latitudine)
    {
        this.latitudine = latitudine;
    }
    
    public void setLongitudine(Double longitudine)
    {
        this.longitudine = longitudine;
    }
    
    public void setUserCurrentCity(String city)
    {
        if(!city.equals(""))
        {
            city = city.toUpperCase(); //To match the DB records
            if(!city.equals(this.userCurrentCity))
            {
                this.userCurrentCity = city;
                U_Vars.userCity = city;
                this.sendLocationAcquired_Intent();
            }
        }
    }
}
