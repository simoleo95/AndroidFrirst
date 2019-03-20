package it.univaq.mobileprogramming;

import android.content.Context;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import it.univaq.mobileprogramming.database.D_Database;
import it.univaq.mobileprogramming.entity.E_Farmacia;
import it.univaq.mobileprogramming.utility.U_Location;
import it.univaq.mobileprogramming.utility.U_Vars;

import static it.univaq.mobileprogramming.utility.U_Vars.farmacieUtente;

public class MainActivity extends AppCompatActivity // <- to ensure backward compability
{
    private Download download;
    private U_Location location;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //Link this class(context) to a specific XML (activity_main)
        
        final Context context = this;
        
//        new Thread(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                System.out.println("onCreate - THREAD LOCATION");
//                Looper.prepare();
//                location = new U_Location(context);
//            }
//        }).start();
        
        this.location = new U_Location(this);
        
        this.download = new Download(this);
        this.download.saveToDB();
        System.out.println("onCreate - END FUNCTION");
    }
    

//    @Override
//    protected void onResume()
//    {
//        super.onResume();
//        location.getUserCurrentLocation();
//        location.createLocationCallback();
//    }
    
    @Override
    protected void onStart()
    {
        super.onStart();
    
//
//        final Context context = this;
//
//        new Thread(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                D_Database room = D_Database.getInstance(context);
//
//                String userCity = location.getUserCurrentCity();
//                while(userCity.equals(""))
//                {
//                    userCity = location.getUserCurrentCity();
//                }
//                System.out.println("HO TROVATO!!!!!!!!!!! : " + userCity);
//                U_Vars.farmacieUtente = room.D_Farmacia_Access().getAllPharmaciesIn(userCity);
//                System.out.println("ESISTONO TANTE FARMACIE: " + U_Vars.farmacieUtente.size());
//            }
//        }).start();


//
//
//        System.out.println("Sono PRIMA del while :( guarda qua...: " + U_Vars.canShowListNow);
//        while(U_Vars.canShowListNow == false) ;
//
//        System.out.println("Sono DOPO il while!!! Infatti: " + U_Vars.canShowListNow);
//
        if(U_Vars.farmacieUtente == null)
        {
            U_Vars.farmacieUtente = Arrays.asList(new E_Farmacia(13, "a", "b", "c", "a", "b", "c", "a", "b", "c", "a"));
        }
        AdapterRecycler adapter = new AdapterRecycler(U_Vars.farmacieUtente);
        //Here link the main_list to the context (MainActivity)
        RecyclerView list = findViewById(R.id.main_list); //Search for R.id.main_list in the activity_main.xml because it's the xml file linked in the onCreate() function
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
    }
    
    
    
    @Override
    protected void onDestroy()
    {
        this.download.unregisterReceiver();
        this.location.unregisterReceiver();
        location.googlePlayServices.disconnect();
        D_Database.closeConnection();
        super.onDestroy();
    }
}