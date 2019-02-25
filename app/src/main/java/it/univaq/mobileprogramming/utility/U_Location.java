package it.univaq.mobileprogramming.utility;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * This class enables to connect and disconnect from Google Play Services
 * It should be instantiated on MainActivity.onStart() calling googlePlayServices.connect()
 * and then onStop() calling googlePlayServices.disconnect()
 */
public class U_Location {
    Context context;
    GoogleApiClient googlePlayServices;
    Double latitudine;
    Double longitudine;

    public U_Location(Context context) {
        this.context = context;
        this.googlePlayServices = getGooglePlayServices();
    }


    //https://stackoverflow.com/questions/28852317/android-how-to-retrieve-current-location-using-google-play-services

    public String findCity() throws IOException {
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        List<Address> addresses;
        addresses = gcd.getFromLocation(latitudine, longitudine, 1);
        if (addresses != null) {
            if (addresses.size() > 0)
                return  addresses.get(0).getLocality();

        }
        return null;
    }

    /**
     * Instantiate the client needed to connect and disconnect to/from Google Play Services
     *
     * @return client handling connection to Google Play Services
     */
    private GoogleApiClient getGooglePlayServices() {
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
    private GoogleApiClient.ConnectionCallbacks neededCallbacks = new GoogleApiClient.ConnectionCallbacks() {

        /**
         * Get the latest user's known position
         *
         * @param bundle the bundle
         */
        @Override
        public void onConnected(@Nullable Bundle bundle) {
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

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            loco.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                latitudine = location.getLatitude();
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
