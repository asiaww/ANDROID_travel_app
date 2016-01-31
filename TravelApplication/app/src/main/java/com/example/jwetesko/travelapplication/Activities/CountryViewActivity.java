package com.example.jwetesko.travelapplication.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.jwetesko.travelapplication.Classes.Country;
import com.example.jwetesko.travelapplication.Adapters.CountryPhotoGridAdapter;
import com.example.jwetesko.travelapplication.Views.ExpandableHeightGridView;
import com.example.jwetesko.travelapplication.R;
import com.squareup.picasso.Picasso;

public class CountryViewActivity extends AppCompatActivity {

    protected Country currentCountry = new Country();
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_view);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        currentCountry = (Country) bundle.getSerializable("currentCountry");

        this.initUI();
    }

    private void initUI() {
        ScrollView sv = (ScrollView) findViewById(R.id.scrollview);

        initImageView(sv, R.id.current_country_image, currentCountry.getPhoto(0), 550);
        initImageView(sv, R.id.list_properties_image_1, R.drawable.icon1, 75);
        initImageView(sv, R.id.list_properties_image_2, R.drawable.icon2, 75);
        initImageView(sv, R.id.list_properties_image_3, R.drawable.icon3, 75);

        initTextView(sv, R.id.current_country_name, this.currentCountry.getName());
        initTextView(sv, R.id.list_properties_text_1, "Stolica: " + this.currentCountry.getCapital());
        initTextView(sv, R.id.list_properties_text_2, "Waluta: " + this.currentCountry.getCurrency());
        initTextView(sv, R.id.list_properties_text_3, "Język: " + this.currentCountry.getLanguage());

        final ExpandableHeightGridView gridview = (ExpandableHeightGridView) findViewById(R.id.current_country_photos_grid);
        gridview.setExpanded(true);
        gridview.setAdapter(new CountryPhotoGridAdapter(context, currentCountry.getAllPhotos()));

        final Button mailButton = (Button) findViewById(R.id.mail_button);
        final Button searchButton = (Button) findViewById(R.id.search_button);
        final Button searchGoogleButton = (Button) findViewById(R.id.search_button_google);

        setMailOnClickListener(mailButton);
        setWebOnClickListener(searchButton, "http://www.qtravel.pl/q/" + currentCountry.getName());
        setWebOnClickListener(searchGoogleButton, "http://www.google.com/search?q=" + currentCountry.getName());
    }

    private void initTextView(View view, Integer id, String text) {
        final TextView textView = (TextView) view.findViewById(id);
        textView.setText(text);
    }

    private void initImageView(View view, Integer id, Integer source, Integer size) {
        final ImageView imageView = (ImageView) findViewById(id);
        Picasso.with(context).load(source).resize(size, size).noFade().into(imageView);
    }

    private void initImageView(View view, Integer id, String source, Integer size) {
        final ImageView imageView = (ImageView) findViewById(id);
        Picasso.with(context).load(source).resize(size, size).noFade().into(imageView);
    }

    private void setMailOnClickListener(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Pomysł na wakacje!");
                intent.putExtra(Intent.EXTRA_TEXT, "Cześć! Mam świetny pomysł na wakacje, jedźmy do " + currentCountry.getName() + "! Zobacz jakie super oferty: http://www.qtravel.pl/q/" + currentCountry.getName() + " ");
                        Intent mailer = Intent.createChooser(intent, null);
                startActivity(mailer);
            }
        });
    }

    private void setWebOnClickListener(Button button, final String url) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });
    }
}
