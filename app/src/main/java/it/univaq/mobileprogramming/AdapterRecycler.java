package it.univaq.mobileprogramming;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import it.univaq.mobileprogramming.entity.Farmacia;

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.ViewHolder>
{
    
    private Farmacia[] data;
    
    public AdapterRecycler(Farmacia[] data)
    {
        this.data = data;
    }
    
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter, viewGroup, false); //Link it to adapter.xml
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i)
    {
        //This function will ITERATE itself on EACH element present in farmacia[] array
        Farmacia farmacia = data[i];
        viewHolder.title.setText(farmacia.getDescrizione());
        viewHolder.subtitle.setText(farmacia.getLocation()
                                            .getIndirizzo());
    }
    
    @Override
    public int getItemCount()
    {
        return data.length;
    }
    
    //This class is like a OnClick_doThings
    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView title;
        TextView subtitle;
        
        public ViewHolder(@NonNull View view)
        {
            super(view);
            
            //Search for these values inside the adapter.xml (linked in onCreateViewHolder)
            title = view.findViewById(R.id.title);
            subtitle = view.findViewById(R.id.subtitle);
            
            view.setOnClickListener(new View.OnClickListener() //This is an interface with only onClick method to be implemented
            {
                @Override
                public void onClick(View v)
                {
                    Farmacia farmacia = data[getAdapterPosition()];
                    
                    Intent intent = new Intent(v.getContext(), DetailsActivity.class);
                    //Retrieve the user clicked element and pass the information to the intent
                    intent.putExtra("cityName", farmacia.getDescrizione());
                    intent.putExtra("city", farmacia.getLocation().getIndirizzo());
                    
                    v.getContext()
                            .startActivity(intent);
                }
            });
        }
    }
}
