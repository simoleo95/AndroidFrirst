package it.univaq.mobileprogramming.utility;

import android.Manifest;
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
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
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

import it.univaq.mobileprogramming.MyReceiver;

/**
 * This class enables to connect and disconnect from Google Play Services
 * It should be instantiated on MainActivity.onStart() calling googlePlayServices.connect()
 * and then onStop() calling googlePlayServices.disconnect()
 */
public class U_Location extends Activity implements ActivityCompat.OnRequestPermissionsResultCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener
{
    Context context;
    public GoogleApiClient googlePlayServices;
    private FusedLocationProviderClient locationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private long gpsUpdateInterval;
    private long gpsFastestUpdateInterval;
    private int gpsPriority;
    
    private Double latitudine;
    private Double longitudine;
    private Location userLocation = new Location("Creator");
    public static String userCurrentCity = "";
    private int requestCode = 1;

    public U_Location(Context context)
    {
        this.context = context;
        this.setGpsUpdateInterval(1000);
        this.setGpsFastestUpdateInterval(500);
        this.setGpsPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        System.out.println("COSTRUTTORE!!!!");
    
        this.setLocationProviderClient(LocationServices.getFusedLocationProviderClient(context));
    
        this.setLocationRequest();
        this.createLocationCallback();
        
        
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                googlePlayServices = getGooglePlayServices();
                googlePlayServices.connect();
            }
        }).start();
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
            setUserCurrentCity("L'Aquila"); //This is BAD... but also a short fix
            return;
        }
        setUserCurrentCity("L'Aquila"); //This is BAD... but also a short fix
        return;
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
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            String askPermissions[] = {Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions((Activity) context, askPermissions, requestCode);
            return;
        }
        this.getLocationProviderClient().getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>()
                {
                    @Override
                    public void onSuccess(Location location)
                    {
                        if (location != null)
                        {
                            setLatitudine(location.getLatitude());
                            setLongitudine(location.getLongitude());
                            userLocation.setLatitude(location.getLatitude());
                            userLocation.setLongitude(location.getLongitude());
                            findCity();
                        }
                    }
                });
    }
    
    /**
     * Called after the user has been prompted to grand permissions
     *
     * @param requestCode the same code put into the ActivityCompat.requestPermissions()
     * @param permissions needed permission to run the activity/app
     * @param grantResults ???
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case 1:
                {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    lastUserLocation();
                }
                else
                {
                    //Trivial solution #1 - Setup the L'Aquila coordinates
                    setLatitudine(42.3498479);
                    setLongitudine(13.3995091);
                }
            }
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


//PART 2 - CHANGE LOCATION SETTINGS
//Source: https://developer.android.com/training/location/change-location-settings
    
    public void getUserCurrentLocation()
    {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(this.getLocationRequest());
    
    
        SettingsClient client = LocationServices.getSettingsClient(context);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        
        task.addOnSuccessListener((Activity) context, new OnSuccessListener<LocationSettingsResponse>()
        {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse)
            {
                // All location settings are satisfied.
                // The client can initialize location requests here.
                
                
                //PART 3 - RECEIVE LOCATION UPDATES
                //Source: https://developer.android.com/training/location/receive-location-updates
                
                if ( ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED )
                {
                    ActivityCompat.requestPermissions(
                            (Activity) context,
                            new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                            requestCode );
                }
                LocationRequest r = getLocationRequest();
                LocationCallback c = getLocationCallback();
                System.out.print("Req = ");
                System.out.println(r != null);
                System.out.print("Call = ");
                System.out.println(c != null);
                
                getLocationProviderClient().requestLocationUpdates(r, c, null);
            }
        });
        
        task.addOnFailureListener((Activity) context, new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                if(e instanceof ResolvableApiException)
                {
                    try
                    {
                        ResolvableApiException res = (ResolvableApiException) e;
                        res.startResolutionForResult((Activity) context, 0x1); //REQUEST_CHECK_SETTINGS); //https://stackoverflow.com/questions/31572323/what-is-the-value-of-request-check-settings
                    }
                    catch(IntentSender.SendIntentException s)
                    {
                        //Ignore it?
                    }
                }
            }
        });
    }

    
    
    
    
    public FusedLocationProviderClient getLocationProviderClient()
    {
        return locationProviderClient;
    }
    
    public void setLocationProviderClient(
            FusedLocationProviderClient locationProviderClient)
    {
        this.locationProviderClient = locationProviderClient;
    }
    
    public LocationCallback getLocationCallback()
    {
        return locationCallback;
    }
    
    /**
     * Invoked by the Fused Location Provider to update the user location
     * Source: https://developer.android.com/training/location/receive-location-updates#callback
     */
    public void createLocationCallback()
    {
        this.locationCallback = new LocationCallback()
        {
            @Override
            public void onLocationResult(LocationResult locationResult)
            {
                if(locationResult == null)
                {
                    return;
                }
                for(Location location : locationResult.getLocations())
                {
                    //UPDATE THE GUI WITH LOCATION DATA HERE
                    latitudine = location.getLatitude();
                    longitudine = location.getLongitude();
                    findCity();
                    //UPDATE THE GUI WITH LOCATION DATA HERE
                }
            }
        };
    }
    
    public LocationRequest getLocationRequest()
    {
        return locationRequest;
    }
    
    public void setLocationRequest()
    {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(this.getGpsUpdateInterval());
        locationRequest.setFastestInterval(this.getGpsFastestUpdateInterval());
        locationRequest.setPriority(this.getGpsPriority());
        this.locationRequest = locationRequest;
    }
    
    private void sendLocationAcquired_Intent()
    {
        System.out.println("Cambiata Loc e mando intent");
        MyReceiver receiveIntent = new MyReceiver();
        String broadcastAction = U_Vars.location_Action;
        
        //Register the BroadcastManager
        LocalBroadcastManager.getInstance(context)
                .registerReceiver(receiveIntent, new IntentFilter(broadcastAction));
    
        //Send Intent
        LocalBroadcastManager.getInstance(context)
                .sendBroadcast(new Intent(broadcastAction));
    }
    
    
    public Double getLatitudine()
    {
        return latitudine;
    }
    
    public void setLatitudine(Double latitudine)
    {
        this.latitudine = latitudine;
    }
    
    public Double getLongitudine()
    {
        return longitudine;
    }
    
    public void setLongitudine(Double longitudine)
    {
        this.longitudine = longitudine;
    }
    
    public Location getUserLocation()
    {
        return userLocation;
    }
    
    public void setUserLocation(Location userLocation)
    {
        this.userLocation = userLocation;
    }
    
    public String getUserCurrentCity()
    {
        return userCurrentCity;
    }
    
    public void setUserCurrentCity(String city)
    {
        if(!city.equals(this.userCurrentCity))
        {
            this.userCurrentCity = city;
            this.sendLocationAcquired_Intent();
        }
    }
    
    public long getGpsUpdateInterval()
    {
        return gpsUpdateInterval;
    }
    
    public void setGpsUpdateInterval(long gpsUpdateInterval)
    {
        this.gpsUpdateInterval = gpsUpdateInterval;
    }
    
    public long getGpsFastestUpdateInterval()
    {
        return gpsFastestUpdateInterval;
    }
    
    public void setGpsFastestUpdateInterval(long gpsFastestUpdateInterval)
    {
        this.gpsFastestUpdateInterval = gpsFastestUpdateInterval;
    }
    
    public int getGpsPriority()
    {
        return gpsPriority;
    }
    
    public void setGpsPriority(int gpsPriority)
    {
        this.gpsPriority = gpsPriority;
    }
    
    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
    
    }
    
    @Override
    public void onConnectionSuspended(int i)
    {
    
    }
    
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
    
    }
    
    @Override
    public void onLocationChanged(Location location)
    {
    
    }
}
