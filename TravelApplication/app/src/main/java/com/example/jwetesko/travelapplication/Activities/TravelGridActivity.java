package com.example.jwetesko.travelapplication.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.jwetesko.travelapplication.Classes.Country;
import com.example.jwetesko.travelapplication.Adapters.TravelGridAdapter;
import com.example.jwetesko.travelapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class TravelGridActivity extends AppCompatActivity {

    private InputStream inputStream = null;
    private String jsonContent = "";
    private JSONObject jsonObj;
    final protected Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_grid);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        ArrayList<Country> choosenCountries = (ArrayList<Country>) bundle.getSerializable("CHDEST_KEY");

        assert choosenCountries != null;
        for(Country currentCountry : choosenCountries) {
            String textUrl = currentCountry.getURL();
            try {
                jsonContent = new DownloadInfo().execute(textUrl).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            try {
                jsonObj = new JSONObject(jsonContent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                JSONArray jsonArray = jsonObj.getJSONArray("photos");
                for (int i = 0; i < jsonArray.length(); i++) {
                    currentCountry.addPhoto(jsonArray.getJSONObject(i).getString("photo_file_url"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
            final GridView gridview = (GridView) findViewById(R.id.gridview);
            gridview.setAdapter(new TravelGridAdapter(this, choosenCountries, getFirstPhotos(choosenCountries)));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent1 = new Intent(context, CountryViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("currentCountry",(Serializable) gridview.getAdapter().getItem(position));
                intent1.putExtras(bundle);
                startActivity(intent1);
            }
        });

    }

    protected ArrayList<String> getFirstPhotos(ArrayList<Country> countries) {
        ArrayList<String> firstPhotoURLs = new ArrayList<String>();
        for (Country currentCountry : countries) {
            firstPhotoURLs.add(currentCountry.getPhoto(0));
        }
        return firstPhotoURLs;
    }

    protected String getPhotosFromPanoramio(String textUrl) throws IOException {
        URL url = null;
        try {
            url = new URL(textUrl);
        } catch (MalformedURLException | NullPointerException e){
            e.printStackTrace();
        }
        HttpURLConnection urlConnection = null;
        try {
            assert url != null;
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        assert urlConnection != null;
        urlConnection.setReadTimeout(1500);
        urlConnection.setConnectTimeout(1500);
        urlConnection.setRequestMethod("GET");
        urlConnection.setDoInput(true);

        try {
            urlConnection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            inputStream = urlConnection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return readStream(inputStream);

    }

    private String readStream(InputStream inputStream) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            total.append(line).append("\n");
        }
        return total.toString();
    }

    private class DownloadInfo extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... strings) {
            try {
                return getPhotosFromPanoramio(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }

        protected void onPostExecute(String result) {
            jsonContent = result;
        }

    }
}