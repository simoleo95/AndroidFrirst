package it.univaq.mobileprogramming.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Arrays;

import it.univaq.mobileprogramming.AdapterRecycler;
import it.univaq.mobileprogramming.R;
import it.univaq.mobileprogramming.database.D_Database;
import it.univaq.mobileprogramming.entity.E_Farmacia;
import it.univaq.mobileprogramming.utility.U_Vars;

public class A_ShowPharmaciesList extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show); //Link this class(context) to a specific XML (activity_main)
        System.out.println("CE L'ABBIAMO FATTA !!!!!!!!!!!!!!!!!!!!!!!!!!!!!");





        final Context context = this;
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
    
        if(U_Vars.farmacieUtente == null)
        {
            U_Vars.farmacieUtente = Arrays.asList(new E_Farmacia(13, "a", "b", "c", "a", "b", "c", "a", "b", "c", "a"));
        }
        AdapterRecycler adapter = new AdapterRecycler(U_Vars.farmacieUtente);
        //Here link the main_list to the context (A_Loading)
        RecyclerView list = findViewById(R.id.main_list); //Search for R.id.main_list in the activity_main.xml because it's the xml file linked in the onCreate() function
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
    }
}
