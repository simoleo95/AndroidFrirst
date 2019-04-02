package it.univaq.mobileprogramming.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import it.univaq.mobileprogramming.R;
import it.univaq.mobileprogramming.utility.U_Vars;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class A_PharmDetails extends AppCompatActivity
{
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        
        //Link values to XML layout
        TextView name = findViewById(R.id.name);
        name.setText(U_Vars.selectedPharm.getFarmacia());
        
        TextView address = findViewById(R.id.address);
        address.setText(U_Vars.selectedPharm.getIndirizzo());
        
        TextView iva = findViewById(R.id.iva);
        iva.setText(U_Vars.selectedPharm.getIva());
        
        TextView city = findViewById(R.id.city);
        city.setText(U_Vars.selectedPharm.getComune());
        
        TextView subcity = findViewById(R.id.subcity);
        subcity.setText(U_Vars.selectedPharm.getFrazione());
        
        TextView provence = findViewById(R.id.provence);
        provence.setText(U_Vars.selectedPharm.getProvincia());
        
        TextView region = findViewById(R.id.region);
        region.setText(U_Vars.selectedPharm.getRegione());
        
        TextView dateStart = findViewById(R.id.dateStart);
        dateStart.setText(U_Vars.selectedPharm.getData_inizio());
        
        TextView lat = findViewById(R.id.lat);
        lat.setText(U_Vars.selectedPharm.getLatitudine());
        
        TextView lon = findViewById(R.id.lon);
        lon.setText(U_Vars.selectedPharm.getLongitudine());
        
        Button showOnMap = findViewById(R.id.showOnMap);
        showOnMap.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent startMap = new Intent(getApplication(), A_Map.class);
                startMap.setFlags(FLAG_ACTIVITY_NEW_TASK);
                getApplication().startActivity(startMap);
            }
        });
    }
}
