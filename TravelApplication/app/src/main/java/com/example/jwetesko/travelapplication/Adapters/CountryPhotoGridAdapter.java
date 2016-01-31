package com.example.jwetesko.travelapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.jwetesko.travelapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by jwetesko on 29.01.16.
 */
public class CountryPhotoGridAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> photoURLs;

    public CountryPhotoGridAdapter(Context context, ArrayList<String> photoURLs) {
        this.context = context;
        this.photoURLs = photoURLs;
    }

    public int getCount() {
        return this.photoURLs.size();
    }

    public String getItem(int position) {
        return this.photoURLs.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;
        if (convertView == null) {
            gridView = inflater.inflate( R.layout.grid_element,null);

            ImageView imageView = (ImageView) gridView.findViewById(R.id.grid_image);
            Picasso.with(context).load(photoURLs.get(position)).noFade().into(imageView);
        } else {

            gridView = (View) convertView;
        }

        return gridView;
    }
}
