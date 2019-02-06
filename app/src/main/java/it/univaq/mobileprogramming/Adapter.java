package it.univaq.mobileprogramming;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Adapter extends BaseAdapter {

    private Farmacia[] farmacia;


    public Adapter(Farmacia[] farmacia){this.farmacia = farmacia;}

    @Override
    public int getCount() {
        return farmacia.length;
    }

    @Override
    public Farmacia getItem(int position) {
        return farmacia[position];
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter, parent, false);
            holder = new ViewHolder();

            holder.title = convertView.findViewById(R.id.title);
            holder.subtitle = convertView.findViewById(R.id.subtitle);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Farmacia farmacia = getItem(position);
        holder.title.setText(farmacia.getName());
        holder.subtitle.setText(farmacia.getCity().getName());

        return convertView;
    }

    class ViewHolder {
        TextView title;
        TextView subtitle;
    }
}
