package com.example.jwetesko.travelapplication.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

import com.example.jwetesko.travelapplication.Classes.Country;
import com.example.jwetesko.travelapplication.R;

import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {

    protected ArrayList<Country> countries = new ArrayList<Country>();
    protected ArrayList<Country> choosenCountries = new ArrayList<Country>();
    final protected Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button startButton = (Button) findViewById(R.id.start_button);

        startButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SplashScreenActivity.class);
                startActivity(intent);
            }
        });
    }
}