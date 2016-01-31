package com.example.jwetesko.travelapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.jwetesko.travelapplication.Classes.Country;
import com.example.jwetesko.travelapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by jwetesko on 18.01.16.
 */
public class TravelGridAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Country> choosenCountries;
    private ArrayList<String> photoURLs;
    int counter = 0;

    public TravelGridAdapter(Context context, ArrayList<Country> choosenCountries, ArrayList<String> photoURLs) {
        this.context = context;
        this.choosenCountries = choosenCountries;
        this.photoURLs = photoURLs;
    }

    public int getCount() {
        return this.choosenCountries.size();
    }

    public Country getItem(int position) {
        return this.choosenCountries.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;
        if (convertView == null) {
            gridView = inflater.inflate(R.layout.grid_element, null);

            ImageView imageView = (ImageView) gridView.findViewById(R.id.grid_image);
            System.out.println(photoURLs.get(position));
            Picasso.with(context).load(photoURLs.get(this.counter)).noFade().into(imageView);
            this.counter += 1;

        } else {

            gridView = (View) convertView;
        }

        return gridView;
    }
}
