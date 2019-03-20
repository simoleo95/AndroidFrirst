package it.univaq.mobileprogramming.utility;

import java.util.List;

import it.univaq.mobileprogramming.BuildConfig;
import it.univaq.mobileprogramming.entity.E_Farmacia;

public class U_Vars
{
    /** Download - Broadcast Intent */
    public static String download_Action = BuildConfig.APPLICATION_ID + ".DOWNLOAD_ENDED";
    
    /** GPS - User has been located */
    public static String location_Action = BuildConfig.APPLICATION_ID + ".LOCATION_FOUND";
    
    /** MyReceiver - Finished parsing data and saved to DB */
    public static boolean dataHasBeenSavedToDB = false;
    
    /** MyReceiver - Finished using GPS and located the user*/
    public static boolean userHasBeenLocated = false;
    
    public static String userCity;
    
    public static List<E_Farmacia> farmacieUtente;
}
