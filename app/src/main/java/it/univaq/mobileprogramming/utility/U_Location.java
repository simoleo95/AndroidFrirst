package it.univaq.mobileprogramming.utility;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * This class enables to connect and disconnect from Google Play Services
 * It should be instantiated on MainActivity.onStart() calling googlePlayServices.connect()
 * and then onStop() calling googlePlayServices.disconnect()
 */
public class U_Location
{
    Context context;
    GoogleApiClient googlePlayServices;
    Double latitudine;
    Double longitudine;
    
    public U_Location(Context context)
    {
        this.context = context;
        this.googlePlayServices = getGooglePlayServices();
    }
    
    
    /**
     * Instantiate the client needed to connect and disconnect to/from Google Play Services
     *
     * @param context The app context
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
            FusedLocationProviderClient loco = LocationServices.getFusedLocationProviderClient(context);
    
            
//        Call requires permission which may be rejected by user:
//        code should explicitly check to see if permission is available (with checkPermission)
//        or explicitly handle a potential SecurityException
//
//        Inspection info:
//        This check scans through your code and libraries and looks at the APIs being used,
//        and checks this against the set of permissions required to access those APIs.
//        If the code using those APIs is called at runtime, then the program will crash.
//        Furthermore, for permissions that are revocable (with targetSdkVersion 23),
//        client code must also be prepared to handle the calls throwing an exception if the user rejects the request for permission at runtime.
//
//        Issue id: MissingPermission
            loco.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>()
                    {
                        @Override
                        public void onSuccess(Location location)
                        {
                            if(location != null)
                            {
                                latitudine  = location.getLatitude();
                                longitudine = location.getLongitude();
                            }
                        }
                    });
        }
        
        @Override
        public void onConnectionSuspended(int i) {}
    };
    
    
    /**
     * Add a listener to whenever the connection to Google Play Services fails
     */
    private GoogleApiClient.OnConnectionFailedListener connectionFailed = new GoogleApiClient.OnConnectionFailedListener()
    {
        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}
    };
}
