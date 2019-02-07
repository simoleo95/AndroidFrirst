package it.univaq.mobileprogramming;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Location[] data = new Location[4];
//        Farmacia[] farmacia = new Farmacia[2];
//
//        Location c1 = new Location("Roma", "Lazio");
//        data[0] = c1;
//
//        Location c2 = new Location("L'Aquila", "Abruzzo");
//        data[1] = c2;
//
//        Location c3 = new Location("Firenze", "Toscana");
//        data[2] = c3;
//
//        Location c4 = new Location("Ascoli Piceno", "Marche");
//        data[3] = c4;
//
//        Farmacia farm1 = new Farmacia();
//        farm1.setCity(c1);
//        farm1.setName("Popoli");
//
//        Farmacia farm2 = new Farmacia();
//        farm2.setName("Pippo");
//        farm2.setCity(c2);


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


        RecyclerView list = findViewById(R.id.main_list);

        list.setLayoutManager(new LinearLayoutManager(this));

        list.setAdapter(adapter);
    }
}
