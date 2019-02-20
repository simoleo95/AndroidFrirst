package it.univaq.mobileprogramming.utility;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.LocaleList;


/**
 * THIS CLASS IS BASED ON GPS
 * IT'S RECOMMENDED TO SWITCH TO Google Play Services TO ENHANCE LOCATION AWARENESS
 */
public class U_UserLocation
{
    Context context;
    
    public U_UserLocation(Context context)
    {
        this.context = context;
    }
    
    
    public LocationManager getLocationManager()
    {
        LocationManager manager = (LocationManager) this.context.getSystemService(Context.LOCATION_SERVICE);
        
        //    Call requires permission which may be rejected by user:
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
            public void onLocationChanged(Location location)
            {
        
            }
    
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras)
            {
        
            }
    
            @Override
            public void onProviderEnabled(String provider)
            {
        
            }
    
            @Override
            public void onProviderDisabled(String provider)
            {
        
            }
        };
        
        return listener;
    }
}

