package com.mosquefinder.arnal.prayertimesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AboutActivity extends AppCompatActivity {

    TextView google, facebook, twitter, telegram, whatsapp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        google = (TextView)findViewById(R.id.google);
        facebook = (TextView)findViewById(R.id.facebook);
        twitter = (TextView)findViewById(R.id.twitter);
        telegram = (TextView)findViewById(R.id.telegram);
        whatsapp = (TextView)findViewById(R.id.whatsapp);


        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutActivity.this, getString(R.string.welcome_google), Toast.LENGTH_SHORT).show();
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutActivity.this, getString(R.string.welcome_facebook), Toast.LENGTH_SHORT).show();

            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutActivity.this, getString(R.string.welcome_twitter), Toast.LENGTH_SHORT).show();

            }
        });

        telegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutActivity.this, getString(R.string.welcome_telegram), Toast.LENGTH_SHORT).show();

            }
        });

        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutActivity.this, getString(R.string.welcome_whatsapp), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
