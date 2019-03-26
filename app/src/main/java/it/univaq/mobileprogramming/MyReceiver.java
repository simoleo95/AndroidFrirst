package it.univaq.mobileprogramming;

import android.support.v7.app.AppCompatActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import it.univaq.mobileprogramming.activities.A_ShowPharmaciesList;
import it.univaq.mobileprogramming.database.D_Database;
import it.univaq.mobileprogramming.entity.E_Farmacia;
import it.univaq.mobileprogramming.utility.U_Location;
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
            }
            else if(action.equals(U_Vars.location_Action))
            {
//                U_Vars.dataHasBeenSavedToDB = true; //Commenta in fase di RILASCIO
                U_Vars.userHasBeenLocated = true;
            }
            if((action.equals(U_Vars.download_Action) || action.equals(U_Vars.location_Action))
                    && U_Vars.dataHasBeenSavedToDB && U_Vars.userHasBeenLocated)
            {
                System.out.println("ORA PUOI FETCHARE I RISULTATI DAL DB");
                this.showFarms(context);
            }
            else
            {
                System.out.println((action.equals(U_Vars.download_Action)
                        + " OR " + action.equals(U_Vars.location_Action))
                        + " AND " + U_Vars.dataHasBeenSavedToDB
                        + " AND " + U_Vars.userHasBeenLocated);
            }
        }
        else
        {
            System.out.println("Non ho ricevuto niente sob :(");
        }
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
                System.out.println("CITTà TROVATA ALLA FINE: " + U_Vars.userCity);
                D_Database room = D_Database.getInstance(context);
                U_Vars.farmacieUtente = room.D_Farmacia_Access().getAllPharmaciesIn(U_Vars.userCity);
                System.out.println("ESISTONO TANTE FARMACIE: " + U_Vars.farmacieUtente.size());
                sendIntent(context);
            }
        }).start();

    }
    
    private void sendIntent(final Context context)
    {
        //TODO: Prova a metterlo nel thread per assicurarti di avere preso i record dal DB
        Intent showFarmList = new Intent(context, A_ShowPharmaciesList.class);
        showFarmList.setFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(showFarmList);
    }
}
