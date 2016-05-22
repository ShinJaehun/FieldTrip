package com.shinjaehun.fieldtrip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by shinjaehun on 2016-05-21.
 */
public class ListPlacesAdapter extends BaseAdapter {

    public static final String TAG = "ListPlacesAdatper";

    private List<Place> places;
    private LayoutInflater inflater;

    public ListPlacesAdapter (Context context, List<Place> listPlaces) {
        this.setItems(listPlaces);
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().size() : 0 ;
    }

    @Override
    public Place getItem(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position) : null ;
    }

    @Override
    public long getItemId(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position).getId() : position ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;

        if (v == null) {
            v = inflater.inflate(R.layout.list_item_place, parent, false);
            holder = new ViewHolder();
            holder.imgIcon = (ImageView)v.findViewById(R.id.img_icon);
            holder.txtName = (TextView)v.findViewById(R.id.text_name);
            holder.txtDescription = (TextView)v.findViewById(R.id.text_desc);
            v.setTag(holder);
        } else {
            holder = (ViewHolder)v.getTag();
        }

        Place currentItem = getItem(position);
        if (currentItem != null) {
            switch (currentItem.getPic()) {
                case "jejumuseum" :
                    holder.imgIcon.setImageResource(R.drawable.jejumuseum);
                    break;
                case "samyangprehistoric" :
                    holder.imgIcon.setImageResource(R.drawable.samyangprehistoric);
                    break;
                default:
                    holder.imgIcon.setImageResource(R.drawable.noimage);
                    break;
            }
            holder.txtName.setText(currentItem.getName());
            holder.txtDescription.setText(currentItem.getDescription());
        }

        return v;
    }

    public List<Place> getItems() {
        return places;
    }

    public void setItems(List<Place> places) {
        this.places = places;
    }

    class ViewHolder {
        ImageView imgIcon;
        TextView txtName;
        TextView txtDescription;
    }
}
