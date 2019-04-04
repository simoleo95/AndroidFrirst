package it.univaq.mobileprogramming.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.sql.Timestamp;
import java.util.List;

import it.univaq.mobileprogramming.BuildConfig;
import it.univaq.mobileprogramming.entity.E_Farmacia;

public class U_Vars
{
    /** U_Download - Broadcast Intent */
    public static String download_Action = BuildConfig.APPLICATION_ID + ".DOWNLOAD_ENDED";
    
    /** GPS - User has been located */
    public static String location_Action = BuildConfig.APPLICATION_ID + ".LOCATION_FOUND";
    
    /** U_MyReceiver - Finished parsing data and saved to DB */
    public static boolean dataHasBeenSavedToDB = false;
    
    /** U_MyReceiver - Finished using GPS and located the user*/
    public static boolean userHasBeenLocated = false;
    
    /** A_Loading - Used to request and check app permissions */
    public static int requestCode = 1;
    
    /**
     * Records the current user's city
     * It's used to fetch DB records and show only pertinent results
     */
    public static String userCity;
    
    /** Records all the pharmacies in the current user's city */
    public static List<E_Farmacia> farmacieUtente;
    
    /** U_AdapterRecycler - Records the user selected pharmacy */
    public static E_Farmacia selectedPharm;
    
    public static final String LAST_ACCESS = "last_access_time"; // Used to save the last timestamp when the user open the app
    public static final String FIRST_TIME = "first_time"; // Used to remember if is the first time that the user open the app
    public static final String SWITCH_DB = "switch_database"; // Used to switch from SQLite DB to RoomDB and vice versa
    public static final String SWITCH_HTTP = "switch_http"; // Used to switch from URLConnection to Volley and vice versa
    public static final String SWITCH_LOCATION = "switch_location"; // Used to switch from LocationManager to GoogleService and vice versa
    
    /** Used to save the timestamp of the last DB update */
    private static final String LAST_DB_UPDATE = "last_DB_Update";
    
    /** DB will need to be updated each 7 days */
    public static final long DB_needsUpdate = 1000*60*60*24*7; //7 days in milliseconds
    
    
    /**
     * Retrieve the timestamp of DB's last update
     * @param context App context
     * @return DB's last update timestamp
     */
    public static long get_DB_lastUpdate(Context context)
    {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
//        return Long.parseLong(preferences.getString(key, String.valueOf(fallback)));
        return preferences.getLong(LAST_DB_UPDATE, 0);
    }
    
    /**
     * Save the current timestamp to record the last timestamp DB has been updated
     * @param context App context
     */
    public static void set_Last_DB_UpdateTimestamp(Context context)
    {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(LAST_DB_UPDATE, new Timestamp(System.currentTimeMillis()).getTime());
        editor.apply();
    }
}