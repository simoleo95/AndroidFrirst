package it.univaq.mobileprogramming.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

import it.univaq.mobileprogramming.R;
import it.univaq.mobileprogramming.database.D_Database;
import it.univaq.mobileprogramming.entity.E_Preferita;
import it.univaq.mobileprogramming.utility.U_Vars;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class A_PharmDetails extends AppCompatActivity
{
    boolean isFavourite;
    D_Database roomDB;
    
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
    
        
        this.addFavouriteButton();
        
        
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
    
    
    private void addFavouriteButton()
    {
        Button manageFavourite = findViewById(R.id.manageFavourites);
        
        this.setButtonText(manageFavourite);
        
        manageFavourite.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent startMap = new Intent(getApplication(), A_PharmDetails.class);
                startMap.setFlags(FLAG_ACTIVITY_NEW_TASK);
                updateFavourite();
                finish(); //Close this Activity in order to avoid mistakes with the DB
                getApplication().startActivity(startMap);
            }
        });
    }
    
    /**
     * This function applies a different text to the button, based on the current pharmacy state
     * @param button The button to set the text to
     */
    private void setButtonText(final Button button)
    {
        roomDB = D_Database.getInstance(this);
        
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                isFavourite = roomDB.D_Farmacia_Access().isThisFavourite(U_Vars.selectedPharm.getId());
    
                if(isFavourite) button.setText(R.string.removeFromFavourites);
                else            button.setText(R.string.addToFavourites);
            }
        }).start();
    }
    
    /**
     * This function will update the "Favourite" DB record of the current pharmacy
     */
    private void updateFavourite()
    {
        final E_Preferita pref = new E_Preferita(U_Vars.selectedPharm.getId());
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                if(isFavourite) roomDB.D_Preferita_Access().removeFromFavourite(pref);
                else            roomDB.D_Preferita_Access().setAsFavourite(pref);
            }
        }).start();
    }
}
