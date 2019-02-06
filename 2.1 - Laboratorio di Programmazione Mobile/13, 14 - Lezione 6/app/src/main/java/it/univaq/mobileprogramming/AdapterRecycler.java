package it.univaq.mobileprogramming;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.ViewHolder> {

    private City[] data;

    public AdapterRecycler(City[] data){
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

        City city = data[i];
        viewHolder.title.setText(city.getName());
        viewHolder.subtitle.setText(city.getRegion());
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView subtitle;

        public ViewHolder(@NonNull View view) {
            super(view);

            title = view.findViewById(R.id.title);
            subtitle = view.findViewById(R.id.subtitle);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    City city = data[getAdapterPosition()];

                    Intent intent = new Intent(v.getContext(), DetailsActivity.class);
                    intent.putExtra("cityName", city.getName());
                    intent.putExtra("regionName", city.getRegion());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
