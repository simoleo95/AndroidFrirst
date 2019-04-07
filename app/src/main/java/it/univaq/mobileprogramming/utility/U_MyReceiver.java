package it.univaq.mobileprogramming.utility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import it.univaq.mobileprogramming.activities.A_Map;
import it.univaq.mobileprogramming.activities.A_ShowPharmaciesList;
import it.univaq.mobileprogramming.database.D_Database;
import it.univaq.mobileprogramming.utility.U_Vars;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * This class updates the map when a specific Broadcast Intent is received
 * The specific intent is sent after finishing to parse Pharmacies into the DB
 */
public class U_MyReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        String action = intent.getAction();
        if(action != null)
        {
            if(action.equals(U_Vars.download_Action))
            {
                U_Vars.dataHasBeenSavedToDB = true;
            }
            else if(action.equals(U_Vars.location_Action))
            {
                U_Vars.userHasBeenLocated = true;
            }
            if((action.equals(U_Vars.download_Action) || action.equals(U_Vars.location_Action))
                    && U_Vars.dataHasBeenSavedToDB && U_Vars.userHasBeenLocated)
            {
                U_Vars.showFarms(context);
            }
        }
    }
}
