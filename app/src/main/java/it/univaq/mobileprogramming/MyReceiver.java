package it.univaq.mobileprogramming;

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
public class MyReceiver extends BroadcastReceiver
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
//                U_Vars.userHasBeenLocated = true;
            }
            else if(action.equals(U_Vars.location_Action))
            {
//                U_Vars.dataHasBeenSavedToDB = true; //Commenta in fase di RILASCIO
                U_Vars.userHasBeenLocated = true;
            }
            if((action.equals(U_Vars.download_Action) || action.equals(U_Vars.location_Action))
                    && U_Vars.dataHasBeenSavedToDB && U_Vars.userHasBeenLocated)
            {
                this.showFarms(context);
            }
//            else
//            {
//                System.out.println((action.equals(U_Vars.download_Action)
//                        + " OR " + action.equals(U_Vars.location_Action))
//                        + " AND " + U_Vars.dataHasBeenSavedToDB
//                        + " AND " + U_Vars.userHasBeenLocated);
//            }
        }
//        else
//        {
//            System.out.println("Non ho ricevuto niente sob :(");
//        }
    }
    
    /**
     * Fetch the pharmacies in the current city and start a new activity do display them
     * @param context The app context
     */
    public void showFarms(final Context context)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                D_Database room = D_Database.getInstance(context);
                U_Vars.farmacieUtente = room.D_Farmacia_Access().getAllPharmaciesIn(U_Vars.userCity);
                sendIntent(context);
            }
        }).start();

    }
    
    private void sendIntent(final Context context)
    {
        Intent showFarmList = new Intent(context, A_ShowPharmaciesList.class);
        showFarmList.setFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(showFarmList);
    }
}
