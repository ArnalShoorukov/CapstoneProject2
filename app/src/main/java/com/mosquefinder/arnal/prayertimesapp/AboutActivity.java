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
                Toast.makeText(AboutActivity.this, "Welcome to our Google +", Toast.LENGTH_SHORT).show();
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutActivity.this, "Welcome to our Facebook", Toast.LENGTH_SHORT).show();

            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutActivity.this, "Welcome to our Twitter", Toast.LENGTH_SHORT).show();

            }
        });

        telegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutActivity.this, "Welcome to our Telegram", Toast.LENGTH_SHORT).show();

            }
        });

        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutActivity.this, "Welcome to our WhatsApp", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
