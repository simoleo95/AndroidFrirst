package it.univaq.mobileprogramming.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Arrays;

import it.univaq.mobileprogramming.utility.U_AdapterRecycler;
import it.univaq.mobileprogramming.R;
import it.univaq.mobileprogramming.entity.E_Farmacia;
import it.univaq.mobileprogramming.utility.U_Vars;

public class A_ShowPharmaciesList extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show); //Link this class(context) to a specific XML (activity_main)

        if(U_Vars.farmacieUtente == null)
        {
            U_Vars.farmacieUtente = Arrays.asList(new E_Farmacia(13, "a", "b", "c", "a", "b", "c", "a", "b", "c", "a"));
        }
        U_AdapterRecycler adapter = new U_AdapterRecycler(U_Vars.farmacieUtente);
        //Here link the main_list to the context (A_Loading)
        RecyclerView list = findViewById(R.id.main_list); //Search for R.id.main_list in the activity_main.xml because it's the xml file linked in the onCreate() function
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
    }
}
