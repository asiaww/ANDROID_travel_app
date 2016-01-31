package com.example.jwetesko.travelapplication.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.example.jwetesko.travelapplication.Classes.Country;
import com.example.jwetesko.travelapplication.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class SplashScreenActivity extends AppCompatActivity {

        private final static int SPLASH_TIME_OUT = 6000;
        private final Context context = this;
        protected ArrayList<Country> countries = new ArrayList<Country>();
        protected ArrayList<Country> choosenCountries = new ArrayList<Country>();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash_screen);

            new LoadData().execute("");

            new Handler().postDelayed(new Runnable() {

                @Override
               public void run() {
                    Intent intent = new Intent(context, TravelGridActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("CHDEST_KEY", choosenCountries);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    setProgressBarIndeterminateVisibility(false);

                    finish();
                }
           }, SPLASH_TIME_OUT);
        }

        private class LoadData extends AsyncTask<String, Integer, String> {

            protected void onPreExecute() {
                setProgressBarIndeterminateVisibility(true);
            }

            protected String doInBackground(String... strings) {
                //InputStream inputStream = getResources().openRawResource(R.raw.json);
                loadJSONDatabase();
                chooseRandomDestinations();
                return "";
            }

            protected void onPostExecute(String result) {}
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

        private void chooseRandomDestinations() {
            Random randomGenerator = new Random();
            while (this.choosenCountries.size() != 10) {
                int randomInt = randomGenerator.nextInt(this.countries.size());
                if (!this.choosenCountries.contains(this.countries.get(randomInt))) {
                    this.choosenCountries.add(this.countries.get(randomInt));
                }
            }
        }
    }

