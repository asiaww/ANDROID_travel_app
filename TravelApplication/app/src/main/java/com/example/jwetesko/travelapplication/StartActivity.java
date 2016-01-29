package com.example.jwetesko.travelapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Random;

public class StartActivity extends AppCompatActivity {

    protected ArrayList<Country> countries = new ArrayList<Country>();
    protected ArrayList<Country> choosenCountries = new ArrayList<Country>();
    final protected Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //this.loadDatabase();
        this.loadJSONDatabase();
        this.chooseRandomDestinations();

        Button startButton = (Button) findViewById(R.id.start_button);

        startButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TravelGridActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("CHDEST_KEY", choosenCountries);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void loadDatabase() {
        XmlPullParserFactory pullParserFactory;
        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            InputStream in_s = getResources().openRawResource(
                    getResources().getIdentifier("raw/destinations",
                            "raw", getPackageName()));
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);

            this.countries = XMLReader.parseXML(parser);
            System.out.println(this.countries);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void chooseRandomDestinations() {
        Random randomGenerator = new Random();
        while (this.choosenCountries.size() != 10) {
            int randomInt = randomGenerator.nextInt(this.countries.size());
            if (!this.choosenCountries.contains(this.countries.get(randomInt))) {
                this.choosenCountries.add(this.countries.get(randomInt));
            }
        }
    }

    private void loadJSONDatabase() {
        InputStream inputStream = getResources().openRawResource(R.raw.json);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int ctr;
        try {
            ctr = inputStream.read();
            while (ctr != -1) {
                byteArrayOutputStream.write(ctr);
                ctr = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            // Parse the data into jsonobject to get original data in form of json.
            JSONObject jObject = new JSONObject(
                    byteArrayOutputStream.toString());
            JSONArray jArray = jObject.getJSONArray("Countries");

            for (int i = 0; i < jArray.length(); i++) {
                Country currentCountry = new Country();

                currentCountry.setProperty("name",jArray.getJSONObject(i).getString("name"));
                currentCountry.setProperty("URL",jArray.getJSONObject(i).getString("url"));
                currentCountry.setProperty("currency",jArray.getJSONObject(i).getString("currency"));
                currentCountry.setProperty("language",jArray.getJSONObject(i).getString("language"));
                currentCountry.setProperty("capital",jArray.getJSONObject(i).getString("capital"));

                this.countries.add(currentCountry);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
