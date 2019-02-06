package it.univaq.mobileprogramming;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        City[] data = new City[3];

        City c1 = new City("Roma", "Lazio");
        data[0] = c1;

        City c2 = new City("L'Aquila", "Abruzzo");
        data[1] = c2;

        City c3 = new City("Firenze", "Toscana");
        data[2] = c3;


        Adapter adapter = new Adapter(data);

        ListView list = findViewById(R.id.main_list);
        list.setAdapter(adapter);
    }
}
