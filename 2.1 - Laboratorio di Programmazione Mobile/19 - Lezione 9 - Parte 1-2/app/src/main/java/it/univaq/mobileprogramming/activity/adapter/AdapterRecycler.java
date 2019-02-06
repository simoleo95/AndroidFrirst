package it.univaq.mobileprogramming.activity.adapter;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import it.univaq.mobileprogramming.R;
import it.univaq.mobileprogramming.activity.DetailsActivity;
import it.univaq.mobileprogramming.activity.MapsActivity;
import it.univaq.mobileprogramming.model.City;

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.ViewHolder> {

    private List<City> data;

    public AdapterRecycler(List<City> data){
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        City city = data.get(i);
        viewHolder.title.setText(city.getName());
        viewHolder.subtitle.setText(city.getRegion());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    // Use ViewHolder Pattern
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView subtitle;

        ViewHolder(@NonNull View view) {
            super(view);

            title = view.findViewById(R.id.title);
            subtitle = view.findViewById(R.id.subtitle);

            // Define the click event on item
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Open another Activity and pass to it the right city
                    City city = data.get(getAdapterPosition());
                    Intent intent = new Intent(v.getContext(), MapsActivity.class);
                    intent.putExtra("cityName", city.getName());
                    intent.putExtra("regionName", city.getRegion());
                    intent.putExtra("latitude", city.getLatitude());
                    intent.putExtra("longitude", city.getLongitude());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
