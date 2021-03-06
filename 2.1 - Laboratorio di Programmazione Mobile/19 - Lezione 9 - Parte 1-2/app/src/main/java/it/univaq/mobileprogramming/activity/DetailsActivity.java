package it.univaq.mobileprogramming.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import it.univaq.mobileprogramming.R;

/**
 * MobileProgramming2018
 * Created by leonardo on 26/10/2018.
 */
public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        String cityName = getIntent().getStringExtra("cityName");
        String regionName = getIntent().getStringExtra("regionName");

        TextView textCityName = findViewById(R.id.details_city_name);
        TextView textRegionName = findViewById(R.id.details_region_name);

        textCityName.setText(cityName);
        textRegionName.setText(regionName);
    }
}
