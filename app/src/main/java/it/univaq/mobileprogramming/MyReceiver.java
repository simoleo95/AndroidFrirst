package it.univaq.mobileprogramming;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * This class updates the map when a specific Broadcast Intent is received
 * The specific intent is sent after finishing to parse Pharmacies into the DB
 */
public class MyReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        System.out.println("Eh eh... che dire? Qui andrebbero aggiornate le farmacie sulla mappa");
        String action = intent.getAction();
        if(action != null)
        {
            System.out.println("Ho ricevuto questo: " + action);
        }
        else
        {
            System.out.println("Non ho ricevuto un cazzo, puttana troia!");
        }
    }
}
