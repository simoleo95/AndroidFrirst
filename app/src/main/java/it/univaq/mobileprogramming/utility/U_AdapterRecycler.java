package it.univaq.mobileprogramming.utility;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import it.univaq.mobileprogramming.R;
import it.univaq.mobileprogramming.activities.A_PharmDetails;
import it.univaq.mobileprogramming.entity.E_Farmacia;

public class U_AdapterRecycler extends RecyclerView.Adapter<U_AdapterRecycler.ViewHolder>
{
    //Source: https://developer.android.com/guide/topics/ui/layout/recyclerview
    private List<E_Farmacia> data;
    
    public U_AdapterRecycler(List<E_Farmacia> data)
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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) //https://stackoverflow.com/questions/37523308/when-onbindviewholder-is-called-and-how-it-works/37524217
    {
        E_Farmacia farmacia = data.get(i);
        viewHolder.pharmacy.setText(farmacia.getFarmacia());
        viewHolder.address.setText(farmacia.getIndirizzo());
    }
    
    @Override
    public int getItemCount()
    {
        return data.size();
    }
    
    //This class is like a OnClick_doThings
    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView pharmacy;
        TextView address;
        
        public ViewHolder(@NonNull View view)
        {
            super(view);
            
            //Search for these values inside the adapter.xml (linked in onCreateViewHolder)
            pharmacy = view.findViewById(R.id.title);
            address  = view.findViewById(R.id.subtitle);
            
            view.setOnClickListener(new View.OnClickListener() //This is an interface with only onClick method to be implemented
            {
                @Override
                public void onClick(View v)
                {
                    U_Vars.selectedPharm = data.get(getAdapterPosition());
                    
                    Intent intent = new Intent(v.getContext(), A_PharmDetails.class);
                    v.getContext()
                            .startActivity(intent);
                }
            });
        }
    }
}
