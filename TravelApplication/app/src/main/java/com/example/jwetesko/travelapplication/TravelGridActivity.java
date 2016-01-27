package com.example.jwetesko.travelapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class TravelGridActivity extends AppCompatActivity {

    public final static String CHDEST_KEY = "choosenDestinations";
    private ArrayList<String> choosenDestinations = new ArrayList<String>();
    InputStream is = null;
    String oho = "";
    JSONObject jsonObj;
    ArrayList<String> photos3 = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_grid);

        Intent intent = getIntent();
        this.choosenDestinations = intent.getStringArrayListExtra(CHDEST_KEY);


        /*String hostUrl = "http://api.qtravel.pl/apis?";
        String queryParameterName = "&query=";
        String transportParameterName = "&ftrans=";
        String transportParameterValue = "samolot";
        String qapiKeyParameterName = "qapikey=";
        String qapiKeyParameterValue = "125371768e33eb299b923ed3ca28e71c";
        String countryParameterName = "&f_country=";
        String countryParameterValue = "Ekwador";
        String textUrl = hostUrl + qapiKeyParameterName + qapiKeyParameterValue + queryParameterName + countryParameterValue + countryParameterName + countryParameterValue;
        //new HttpAsyncTask().execute(textUrl);
        new DownloadInfo().execute(textUrl);
        System.out.println(oho);
        */

        String textUrl = "http://www.panoramio.com/map/get_panoramas.php?set=public&from=0&to=10&minx=7.38&miny=42&maxx=7.45&maxy=44&size=medium&mapfilter=false";
        try {
            oho = new DownloadInfo().execute(textUrl).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        try {
            jsonObj = new JSONObject(oho);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            JSONArray wow2 = jsonObj.getJSONArray("photos");
            for (int i = 0; i < wow2.length(); i++) {
                photos3.add(wow2.getJSONObject(i).getString("photo_file_url"));
                System.out.println(photos3.get(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new MyAdapter(this, choosenDestinations, photos3));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                System.out.println(gridview.getAdapter().getItem(position));
            }
        });

    }

    protected String rzeczy(String textUrl) throws IOException {
        URL url = null;
        try {
            url = new URL(textUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            is = urlConnection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return readStream(is);

    }

    private String readStream(InputStream is) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(is));
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            total.append(line + "\n");
        }
        return total.toString();
    }

    private class DownloadInfo extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... strings) {
            try {
                String wow = rzeczy(strings[0]);
                return wow;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }

        protected void onPostExecute(String result) {
            oho = result;
        }

    }
}