package it.univaq.mobileprogramming;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
        
        
    
        System.out.println("Inizio DOWNLOAD");
        this.download = new Download(this);

        //This belongs to a different activity/class
        this.download.saveToDB();
    
        System.out.println("Inizio location MAIN");
        location = new U_Location(this);
    
        final Context context = this;
        
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while(U_Vars.loadingDone == false) ;
                
                String userCity = location.getUserCurrentCity();
                System.out.println("Mi trovo a: " + userCity);
                D_Database room = D_Database.getInstance(context);
                U_Vars.farmacieUtente = room.D_Farmacia_Access().getAllPharmaciesIn(userCity);
                
                U_Vars.canShowListNow = true;
    
            }
        }).start();
    
    
        while(U_Vars.canShowListNow == false) ;
        
        AdapterRecycler adapter = new AdapterRecycler(U_Vars.farmacieUtente);
        //Here link the main_list to the context (MainActivity)
        RecyclerView list = findViewById(R.id.main_list); //Search for R.id.main_list in the activity_main.xml because it's the xml file linked in the onCreate() function
        list.setLayoutManager(new LinearLayoutManager(context));
        list.setAdapter(adapter);
    }
    
    public static void showFarms()
    {
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        location.getUserCurrentLocation();
        location.createLocationCallback();
    }
    
    
    @Override
    protected void onDestroy()
    {
        this.download.unregisterReceiver();
        location.googlePlayServices.disconnect();
        D_Database.closeConnection();
        super.onDestroy();
    }
}
