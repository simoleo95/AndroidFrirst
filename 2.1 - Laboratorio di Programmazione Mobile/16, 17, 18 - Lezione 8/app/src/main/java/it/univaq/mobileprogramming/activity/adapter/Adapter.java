package it.univaq.mobileprogramming.activity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import it.univaq.mobileprogramming.R;
import it.univaq.mobileprogramming.model.City;

/**
 * MobileProgramming2018
 * Created by leonardo on 19/10/2018.
 * <p>
 * BiTE s.r.l.
 * contact info@bitesrl.it
 */
public class Adapter extends BaseAdapter {

    private City[] data;

    public Adapter(City[] data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public City getItem(int position) {
        return data[position];
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

        City city = getItem(position);
        holder.title.setText(city.getName());
        holder.subtitle.setText(city.getRegion());

        return convertView;
    }

    class ViewHolder {
        TextView title;
        TextView subtitle;
    }
}
