package com.example.jwetesko.travelapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by jwetesko on 18.01.16.
 */
public class MyAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> choosenDestinations;
    private ArrayList<String> photoURLs;

    public MyAdapter(Context context, ArrayList<String> choosenDestinations, ArrayList<String> photoURLs) {
        this.context = context;
        this.choosenDestinations = choosenDestinations;
        this.photoURLs = photoURLs;
    }

    public int getCount() {
        return this.choosenDestinations.size();
    }

    public Object getItem(int position) {
        return this.choosenDestinations.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;
        if (convertView == null) {
            gridView = inflater.inflate( R.layout.grid_element , null);

            //TextView textView = (TextView) gridView
            //        .findViewById(R.id.grid_text);

            //textView.setText(this.choosenDestinations.get(position));

            ImageView imageView = (ImageView) gridView.findViewById(R.id.grid_image);
            Picasso.with(context).load(photoURLs.get(position)).resize(550,550).noFade().into(imageView);

        } else {

            gridView = (View) convertView;
        }

        return gridView;
    }
}
