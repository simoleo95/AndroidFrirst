package it.univaq.mobileprogramming;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import it.univaq.mobileprogramming.database.D_Database;
import it.univaq.mobileprogramming.entity.Farmacia;
import it.univaq.mobileprogramming.entity.Location;
import it.univaq.mobileprogramming.utility.U_Location;

public class MainActivity extends AppCompatActivity // <- to ensure backward compability
{
    private Download download;
    private U_Location location;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //Link this class(context) to a specific XML (activity_main)
        
        
    
//        System.out.println("Inizio DOWNLOAD");
//        this.download = new Download(this);
//
//        //This belongs to a different activity/class
//        this.download.saveToDB();
        
        location = new U_Location(this);
        
        
        
        Location[] loc = new Location[2];
        Farmacia[] farmacia = new Farmacia[2];
    
        Farmacia f1 = new Farmacia();
        f1.setDescrizione("FARMACIA CASABIANCA S.N.C. DEI DOTT.RI PALINI ROBERTA, RESTIVO PIETRO E CANTONI ANDREA");
        
        Location l1 = new Location();
        l1.setIndirizzo("Via Buggianese, 108");
        f1.setLocation(l1);
        
        
        
        Farmacia f2 = new Farmacia();
        f2.setDescrizione("SAN FRANCESCO DA PAOLA");
        Location l2 = new Location();
        l2.setIndirizzo("Via San Francesco Da Paola, 10");
        f2.setLocation(l2);
        
        
        farmacia[0]=f1;
        farmacia[1]=f2;

        // Adapter adapter = new Adapter(data);
        AdapterRecycler adapter = new AdapterRecycler(farmacia);


        //Here link the main_list to the context (MainActivity)
        RecyclerView list = findViewById(R.id.main_list); //Search for R.id.main_list in the activity_main.xml because it's the xml file linked in the onCreate() function
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
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
