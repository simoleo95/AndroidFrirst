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
    
    /** MyReceiver - Finished parsing data and saved to DB */
    public static boolean dataHasBeenSavedToDB = false;
    
    /** MyReceiver - Finished using GPS and located the user*/
    public static boolean userHasBeenLocated = false;
    
    public static final String USER_CITY = "User_City";
    public static String userCity;
    public static Double chosenPharm_LAT;
    public static Double chosenPharm_LON;
    
    public static List<E_Farmacia> farmacieUtente;
    
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
