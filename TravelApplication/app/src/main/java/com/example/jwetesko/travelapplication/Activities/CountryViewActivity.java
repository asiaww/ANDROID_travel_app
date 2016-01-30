package com.example.jwetesko.travelapplication.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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

        Intent intent1 = this.getIntent();
        Bundle bundle = intent1.getExtras();

        currentCountry = (Country) bundle.getSerializable("currentCountry");

        ScrollView sv = (ScrollView) findViewById(R.id.scrollview);

        ImageView imageView = (ImageView) sv.findViewById(R.id.current_country_image);
        Picasso.with(context).load(currentCountry.getPhoto(0)).resize(550, 550).noFade().into(imageView);

        TextView nameTextView = (TextView) sv.findViewById(R.id.current_country_name);
        nameTextView.setText(this.currentCountry.getName());

        ImageView capitalImageView = (ImageView) findViewById(R.id.list_properties_image_1);
        Picasso.with(context).load(R.drawable.icon1).resize(75,75).noFade().into(capitalImageView);

        ImageView currencyImageView = (ImageView) findViewById(R.id.list_properties_image_2);
        Picasso.with(context).load(R.drawable.icon2).resize(75, 75).noFade().into(currencyImageView);

        ImageView languageImageView = (ImageView) findViewById(R.id.list_properties_image_3);
        Picasso.with(context).load(R.drawable.icon3).resize(75,75).noFade().into(languageImageView);

        TextView capitalTextView = (TextView) sv.findViewById(R.id.list_properties_text_1);
        capitalTextView.setText("Stolica: " + this.currentCountry.getCapital());

        TextView currencyTextView = (TextView) sv.findViewById(R.id.list_properties_text_2);
        currencyTextView.setText("Waluta: " + this.currentCountry.getCurrency());

        TextView languageTextView = (TextView) sv.findViewById(R.id.list_properties_text_3);
        languageTextView.setText("Język: " + this.currentCountry.getLanguage());

        final ExpandableHeightGridView gridview = (ExpandableHeightGridView) findViewById(R.id.current_country_photos_grid);
        gridview.setExpanded(true);
        gridview.setAdapter(new CountryPhotoGridAdapter(context, currentCountry.getAllPhotos()));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
            }
        });

        Button mailButton = (Button) findViewById(R.id.mail_button);
        Button searchButton = (Button) findViewById(R.id.search_button);
        Button searchGoogleButton = (Button) findViewById(R.id.search_button_google);

        mailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Świetna wycieczka!");
                intent.putExtra(Intent.EXTRA_TEXT, "Musisz koniecznie jechać ze mną na wakacje!");
                Intent mailer = Intent.createChooser(intent, null);
                startActivity(mailer);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/search?q=" + currentCountry.getName()));
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.qtravel.pl/q/" + currentCountry.getName()));
                startActivity(browserIntent);
            }
        });

        searchGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/search?q=" + currentCountry.getName()));
                startActivity(browserIntent);
            }
        });
    }

    private void initUI() {


    }

    private void initTextView(View view, Integer id, String text) {
        TextView textView = (TextView) view.findViewById(id);
        textView.setText(text);
    }

    private void initImageView(View view, Integer id, String source, Integer size) {
        ImageView imageView = (ImageView) findViewById(id);
        Picasso.with(context).load(source).resize(size, size).noFade().into(imageView);
    }
}
