package it.univaq.mobileprogramming;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //These values will be the user clicked item on the previous View
        String cityName = getIntent().getStringExtra("cityName");
        String regionName = getIntent().getStringExtra("regionName");

        //Link values to XML layout
        TextView textCityName = findViewById(R.id.details_city_name);
        TextView textRegionName = findViewById(R.id.details_region_name);

        textCityName.setText(cityName);
        textRegionName.setText(regionName);
    }
}
