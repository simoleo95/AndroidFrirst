package it.univaq.mobileprogramming.utility;

import android.Manifest;
import android.app.Activity;
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

import it.univaq.mobileprogramming.MainActivity;

/**
 * This class enables to connect and disconnect from Google Play Services
 * It should be instantiated on MainActivity.onStart() calling googlePlayServices.connect()
 * and then onStop() calling googlePlayServices.disconnect()
 */
public class U_Location implements ActivityCompat.OnRequestPermissionsResultCallback
{
    Context context;
    public GoogleApiClient googlePlayServices;
    
    private Double latitudine;
    private Double longitudine;
    private String city;
    private int requestCode = 1;

    public U_Location(Context context)
    {
        this.context = context;
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                googlePlayServices = getGooglePlayServices();
                googlePlayServices.connect();
//                System.out.println("Ho finito il thread per la localizzazione");
            }
        }).start();
    }


    //https://stackoverflow.com/questions/28852317/android-how-to-retrieve-current-location-using-google-play-services

    private void findCity()
    {
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        List<Address> addresses;
        
        try
        {
            addresses = gcd.getFromLocation(latitudine, longitudine, 1);
            if(addresses != null)
            {
                if(addresses.size() > 0)
                {
                    setCity(addresses.get(0)
                            .getLocality());
//                    System.out.println("L'array addresses che ho trovato: " + addresses.toString());
                }
            }
        }
        catch(IOException e)
        {
            setCity("L'Aquila"); //This is BAD... but also a short fix
            return;
        }
        setCity("L'Aquila"); //This is BAD... but also a short fix
        return;
    }
    

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
            updateLocation();
        }
        
        @Override
        public void onConnectionSuspended(int i) {}
    };
    
    
    /**
     * Enable to get user's latitude and longitude.
     * Moreover it reverse-geocode the user position to detect the current user's city via findCity()
     */
    private void updateLocation()
    {
        FusedLocationProviderClient loco = LocationServices.getFusedLocationProviderClient(context);
        
//        System.out.println("Sono pronto Connesso");
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
//            System.out.println("SONO NELL'IF PERCHE' NON HO I PERMESSI!");
            String askPermissions[] = {Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions((Activity) context, askPermissions, requestCode);
            return;
        }
//        System.out.println("Sono prima della location");
        loco.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>()
                {
                    @Override
                    public void onSuccess(Location location)
                    {
                        if (location != null)
                        {
//                            System.out.println("Ho trovato una location!!");
                            setLatitudine(location.getLatitude());
                            setLongitudine(location.getLongitude());
                            findCity();
//                            System.out.println("latitudine = " + latitudine);
//                            System.out.println("longitudine = " + longitudine);
//                            System.out.println("Ci troviamo a: " + city);
                        }
                    }
                });
        System.out.println("Ho TERMINATO la location!!");
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
                    updateLocation();
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
    
    public String getCity()
    {
        return city;
    }
    
    public void setCity(String city)
    {
        this.city = city;
    }
    
}
