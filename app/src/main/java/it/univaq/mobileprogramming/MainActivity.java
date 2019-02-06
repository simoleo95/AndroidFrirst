package it.univaq.mobileprogramming;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        City[] data = new City[4];
        Farmacia[] farmacia = new Farmacia[2];

        City c1 = new City("Roma", "Lazio");
        data[0] = c1;

        City c2 = new City("L'Aquila", "Abruzzo");
        data[1] = c2;

        City c3 = new City("Firenze", "Toscana");
        data[2] = c3;

        City c4 = new City("Ascoli Piceno", "Marche");
        data[3] = c4;

        Farmacia farm1 = new Farmacia();
        farm1.setCity(c1);
        farm1.setName("Popoli");

        Farmacia farm2 = new Farmacia();
        farm2.setName("Pippo");
        farm2.setCity(c2);
        farmacia[0]=farm1;
        farmacia[1]=farm2;

        // Adapter adapter = new Adapter(data);
        AdapterRecycler adapter = new AdapterRecycler(farmacia);


        RecyclerView list = findViewById(R.id.main_list);

        list.setLayoutManager(new LinearLayoutManager(this));

        list.setAdapter(adapter);
    }
}
