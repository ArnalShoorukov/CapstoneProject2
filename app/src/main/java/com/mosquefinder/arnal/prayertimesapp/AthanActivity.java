package com.mosquefinder.arnal.prayertimesapp;

import android.content.Context;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class AthanActivity extends AppCompatActivity {

    private TextView athan_label;
    private Button stop_athan_button;
    private int nextPrayerCode;
    private String language;
    private String athanType;
    private Typeface tf;
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    private TimerTask timerTask;
    private Timer timer;
    private int counter = 0;

    public static boolean STARTED = false;//if athan started or not

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athan);

        STARTED = true;

        changeMainBackground();

        this.mediaPlayer = new MediaPlayer();

        this.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.stop();
                finish();
            }
        });

        Bundle localBundle = getIntent().getExtras();
        if (localBundle != null) {
            this.nextPrayerCode = localBundle.getInt("nextPrayerCode");
            this.language = localBundle.getString("language");
            this.athanType = localBundle.getString("athanType");
        }

        this.athan_label = (TextView) findViewById(R.id.athan_label);
        this.stop_athan_button = (Button) findViewById(R.id.stop_athan);

        String actualAthanTime = "";
        switch (nextPrayerCode) {
            case 1020:
                actualAthanTime = this.getResources().getString(R.string.shorouk);
                break;
            case 1021:
                actualAthanTime = this.getResources().getString(R.string.duhr);
                break;
            case 1022:
                actualAthanTime = this.getResources().getString(R.string.asr);
                break;
            case 1023:
                actualAthanTime = this.getResources().getString(R.string.maghrib);
                break;
            case 1024:
                actualAthanTime = this.getResources().getString(R.string.ishaa);
                break;
            case 1025:
                actualAthanTime = this.getResources().getString(R.string.fajr);
                break;
            default:
                break;
        }
        if (language.equalsIgnoreCase("ar")) {
            tf = Typeface.createFromAsset(this.getAssets(), "arabic_font.ttf");
            athan_label.setTypeface(tf);
            athan_label.setText(ArabicReshape.reshape(getResources().getString(R.string.its_the_hour_of) + " " + actualAthanTime));
            stop_athan_button.setTypeface(tf);
            stop_athan_button.setText(ArabicReshape.reshape(getResources().getString(R.string.stopathan)));
        } else {
            athan_label.setText(getResources().getString(R.string.its_the_hour_of) + " " + actualAthanTime);
            stop_athan_button.setText(getResources().getString(R.string.stopathan));
        }

        this.stop_athan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }

                if (vibrator != null) {
                    vibrator.cancel();
                }
                if (timer != null) {
                    timer.cancel();
                    timer.purge();
                }

                if (timerTask != null) {
                    timerTask.cancel();
                }

                finish();
            }
        });

        if (this.athanType.equalsIgnoreCase("athan")) {
            playAthan();
        } else {
            this.vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            long[] pattern = {500, 500, 500};
            this.vibrator.vibrate(pattern, 0);
            this.timer = new Timer();
            this.timerTask = new TimerTask() {
                @Override
                public void run() {
                    counter++;
                    if (counter == 10) {
                        vibrator.cancel();
                    }
                    if (counter == 20) {
                        if (vibrator != null) {
                            vibrator.cancel();
                        }
                        if (timer != null) {
                            timer.cancel();
                            timer.purge();
                        }

                        if (timerTask != null) {
                            timerTask.cancel();
                        }

                        finish();
                    }
                }
            };
            this.timer.schedule(timerTask, 0, 1000);
        }
    }
}
