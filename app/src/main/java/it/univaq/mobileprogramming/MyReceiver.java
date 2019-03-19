package it.univaq.mobileprogramming;

import android.support.v7.app.AppCompatActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import it.univaq.mobileprogramming.database.D_Database;
import it.univaq.mobileprogramming.entity.E_Farmacia;
import it.univaq.mobileprogramming.utility.U_Location;
import it.univaq.mobileprogramming.utility.U_Vars;

/**
 * This class updates the map when a specific Broadcast Intent is received
 * The specific intent is sent after finishing to parse Pharmacies into the DB
 */
public class MyReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        System.out.println("dwnl: " + U_Vars.dataHasBeenSavedToDB);
        System.out.println("loc: " + U_Vars.userHasBeenLocated);
        String action = intent.getAction();
        if(action != null)
        {
            System.out.println("Intent iniziale: " + action);
            System.out.println("Switch with: " + U_Vars.download_Action + ", OR " + U_Vars.location_Action);
            
            System.out.println("if 1 = " + action.equals(U_Vars.download_Action));
            System.out.println("if 2 = " + action.equals(U_Vars.location_Action));
            
            if(action.equals(U_Vars.download_Action))
            {
                System.out.println("A) Ho ricevuto questo: " + action);
                U_Vars.dataHasBeenSavedToDB = true;
            }
            else if(action.equals(U_Vars.location_Action))
            {
                System.out.println("B) Ho ricevuto questo: " + action);
//                U_Vars.dataHasBeenSavedToDB = true; //rimuovi in fase di RILASCIO
                U_Vars.userHasBeenLocated = true;
            }
            System.out.println("Fine Broadcast 1");
            System.out.println("dwnl2: " + U_Vars.dataHasBeenSavedToDB);
            System.out.println("loc2: " + U_Vars.userHasBeenLocated);
            if((action.equals(U_Vars.download_Action) || action.equals(U_Vars.location_Action))
                    && U_Vars.dataHasBeenSavedToDB && U_Vars.userHasBeenLocated)
            {
                System.out.println("C) Ultimo IF");
                U_Vars.loadingDone = true;
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
    
    public void showFarms(Context context)
    {
    
    }
}
