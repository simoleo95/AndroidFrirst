package it.univaq.mobileprogramming.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

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
    
    /** Used to request and check app permissions */
    public static int requestCode = 1;
    
    /** Specifies whether to download the Pharmacy Excel or not */
    public static boolean db_updated = false;
    
    /**
     * Records the current user's city
     * It's used to fetch DB records and show only pertinent results
     */
    public static String userCity;
    
    /** Records all the pharacies in the current user's city */
    public static List<E_Farmacia> farmacieUtente;
    
    /** U_AdapterRecycler - Records the user selected pharmacy */
    public static E_Farmacia selectedPharm;
    
    public static final String LAST_ACCESS = "last_access_time"; // Used to save the last timestamp when the user open the app
    public static final String FIRST_TIME = "first_time"; // Used to remember if is the first time that the user open the app
    public static final String SWITCH_DB = "switch_database"; // Used to switch from SQLite DB to RoomDB and vice versa
    public static final String SWITCH_HTTP = "switch_http"; // Used to switch from URLConnection to Volley and vice versa
    public static final String SWITCH_LOCATION = "switch_location"; // Used to switch from LocationManager to GoogleService and vice versa
    
    
    public static void save(Context context, String key, String value)
    {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
    
    
    public static boolean loadBoolean(Context context, String key, boolean fallback)
    {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getBoolean(key, fallback);
    }
}