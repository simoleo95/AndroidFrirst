package it.univaq.mobileprogramming;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        City[] data = new City[6];

        City c1 = new City("Roma", "Lazio");
        data[0] = c1;

        City c2 = new City("L'Aquila", "Abruzzo");
        data[1] = c2;

        City c3 = new City("Firenze", "Toscana");
        data[2] = c3;

        City c4 = new City("Napoli", "Campania");
        data[3] = c4;

        City c5 = new City("Torino", "Piemonte");
        data[4] = c5;

        City c6 = new City("Milano", "Lombardia");
        data[5] = c6;


        // Adapter adapter = new Adapter(data);
        AdapterRecycler adapter = new AdapterRecycler(data);

        RecyclerView list = findViewById(R.id.main_list);

        list.setLayoutManager(new LinearLayoutManager(this));

        list.setAdapter(adapter);

        TextView text = findViewById(R.id.main_text);

        long time = Settings.loadLong(getApplicationContext(), "time", -1);
        if(time != -1){

            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS", Locale.getDefault());
            String date = format.format(new Date(time));
            text.setText(date);
        }
        Settings.save(getApplicationContext(), "time", System.currentTimeMillis());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        menu.add(0, 1, 0, R.string.main_demo);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        System.out.println(item.getItemId());
        switch (item.getItemId()){
            case R.id.main_exit:
                showExitDialog();
                return true;

            case 1: // OptionItem "Demo" defined in Java
                Toast.makeText(MainActivity.this, R.string.toast_demo, Toast.LENGTH_SHORT).show();
                return true;

            default:
                return false;
        }
    }

    private void showExitDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.dialog_title);
        builder.setMessage(R.string.dialog_message);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}