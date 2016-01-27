package com.example.jwetesko.travelapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.view.View.OnClickListener;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class StartActivity extends AppCompatActivity {

    private ArrayList<String> destinationsList = new ArrayList<String>();
    protected ArrayList<String> choosenDestinations = new ArrayList<String>();
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        this.loadDatabase();
        this.chooseRandomDestinations();

        Button startButton = (Button) findViewById(R.id.start_button);

        startButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TravelGridActivity.class);
                intent.putExtra("choosenDestinations", choosenDestinations);
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

            destinationsList = XMLReader.parseXML(parser);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void chooseRandomDestinations() {
        Random randomGenerator = new Random();
        while (this.choosenDestinations.size() != 10) {
            int randomInt = randomGenerator.nextInt(this.destinationsList.size());
            if (!this.choosenDestinations.contains(this.destinationsList.get(randomInt))) {
                this.choosenDestinations.add(this.destinationsList.get(randomInt));
            }
        }
    }
}
