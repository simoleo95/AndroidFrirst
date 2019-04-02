package it.univaq.mobileprogramming.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import it.univaq.mobileprogramming.R;
import it.univaq.mobileprogramming.utility.U_Vars;

public class A_PharmDetails extends AppCompatActivity
{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //Link values to XML layout
        TextView textCityName = findViewById(R.id.details_city_name);
        TextView textRegionName = findViewById(R.id.details_region_name);

        textCityName.setText(U_Vars.selectedPharm.getFarmacia());
        textRegionName.setText(U_Vars.selectedPharm.getIndirizzo());
    }
}
