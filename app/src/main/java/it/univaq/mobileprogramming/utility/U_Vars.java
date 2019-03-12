package it.univaq.mobileprogramming.utility;

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
    
    /** Switch between loading and show all Pharmacies in user's city */
    public static boolean loadingDone = false;
    
    /** Show pharms list */
    public static boolean canShowListNow = false;
    
    public static E_Farmacia[] farmacieUtente;
}