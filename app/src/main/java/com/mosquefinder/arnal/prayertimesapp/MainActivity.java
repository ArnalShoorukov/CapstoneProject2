package com.mosquefinder.arnal.prayertimesapp;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.mosquefinder.arnal.prayertimesapp.data.TimesPreferences;
import com.mosquefinder.arnal.prayertimesapp.sync.TimerService;

import net.alqs.iclib.hijri.UmmQura;
import net.alqs.iclib.qibla.Qibla;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private final String TAG = MainActivity.class.getSimpleName();
    private static final int PERMISSIONS_REQUEST_FINE_LOCATION = 111;
    private static final int PLACE_PICKER_REQUEST = 1;
    double latitude, longitude ;
    int METHOD;
    long timezone;
    TextView city,text1, fajr, sunrise, zuhr, asr, magreeb, isha, date_geo, date_hijri, next_prayer;
    ImageView fajr_not, sunrise_not, zuhr_not,asr_not, magreeb_not, isha_not;
    private static int FAJR = 0;
    private static int SUNRISE = 1;
    private static int ZUHR = 2;
    private static int ASR = 3;
    private static int MAGREEB = 4;
    private static int ISHA = 6;

    private Handler handler;
    private Runnable runnable;
    Date futureDate;
    Date currentDate;

    long diff;
    long days;
    long hours;
    long minutes;
    long seconds;
    private long timeCountInMilliSeconds = 1 * 60000;
    private ProgressBar progressBarCircle;
    private TextView textViewTime;
    private CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prayer_main_activity);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Stetho.initializeWithDefaults(this);
      //  text1 = (TextView) findViewById(R.id.time_left);
        city = (TextView) findViewById(R.id.city);
        fajr = (TextView) findViewById(R.id.fajr_time);
        sunrise = (TextView) findViewById(R.id.sunrise_time);
        zuhr = (TextView) findViewById(R.id.zuhr_time);
        asr = (TextView) findViewById(R.id.asr_time);
        magreeb = (TextView)findViewById(R.id.magreeb_time);
        isha = (TextView) findViewById(R.id.isha_time);

        date_geo = (TextView) findViewById(R.id.date_geo);
        date_hijri = (TextView) findViewById(R.id.date_hijri);
       // next_prayer = (TextView) findViewById(R.id.prayer);

        fajr_not = (ImageView)findViewById(R.id.fajr_not);
        sunrise_not = (ImageView)findViewById(R.id.sunrise_not);
        zuhr_not = (ImageView)findViewById(R.id.zuhr_not);
        asr_not = (ImageView)findViewById(R.id.asr_not);
        magreeb_not = (ImageView)findViewById(R.id.magreeb_not);
        isha_not = (ImageView)findViewById(R.id.isha_not);
        initViews();

        setupPermissions();
        Log.d(TAG+"Coordinate, Oncreate", Double.toString(latitude));

        double coord[] =  TimesPreferences.getLocationCoordinates(this);

        latitude = coord[0];
        longitude = coord[1];

        refreshUI(latitude, longitude);
        setTimerValues();
        startCountDownTimer();
        startService(new Intent(this,TimerService.class));
        // Register the listener
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }


    /**
     * method to initialize the values for count down timer
     */
    private void setTimerValues() {
        int time = 2;

        // assigning values after converting to milliseconds
        timeCountInMilliSeconds = time * 60 * 1000;
        Log.d(TAG +"SetTimerValues", Long.toString(timeCountInMilliSeconds));
    }
    /**
     * method to start count down timer
     */
    private void startCountDownTimer() {

        countDownTimer = new CountDownTimer(timeCountInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                textViewTime.setText(hmsTimeFormatter(millisUntilFinished));

                progressBarCircle.setProgress((int) (millisUntilFinished / 1000));
                Log.d(TAG+ "Inside OnTick", Long.toString(timeCountInMilliSeconds));
            }

            @Override
            public void onFinish() {


                // call to initialize the progress bar values
               setProgressBarValues();
                Log.d(TAG+"OnFinish before newTime", Long.toString(timeCountInMilliSeconds));
                timeCountInMilliSeconds = newTime();
                Log.d(TAG+"OnFinish after newTime", Long.toString(timeCountInMilliSeconds));
                this.start();
                //textViewTime.setText(hmsTimeFormatter(timeCountInMilliSeconds));
            }

        }.start();
        countDownTimer.start();
    }

    private long newTime() {
        int newTime = 1;
        timeCountInMilliSeconds = newTime * 60 * 1000;
        Log.d(TAG, Long.toString(timeCountInMilliSeconds));
        return timeCountInMilliSeconds;
    }

    private void initViews() {
        progressBarCircle = (ProgressBar) findViewById(R.id.progressBarCircle);
        textViewTime = (TextView) findViewById(R.id.textViewTime);
    }
    private void setProgressBarValues() {

        progressBarCircle.setMax((int) timeCountInMilliSeconds / 1000);
        progressBarCircle.setProgress((int) timeCountInMilliSeconds / 1000);
    }
    private void refreshUI(double lat, double lon) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        city.setText(sharedPreferences.getString(getString(R.string.pref_location_key),
                getString(R.string.pref_location_default)));
        Log.d(TAG+"LocationLabel", sharedPreferences.getString(getString(R.string.pref_location_key), getString(R.string.pref_location_default)));


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat mdformatYear = new SimpleDateFormat("yyyy");
        SimpleDateFormat mdformatMonth = new SimpleDateFormat("MM");
        SimpleDateFormat mdformatDay = new SimpleDateFormat("dd");

        String strDate = mdformat.format(calendar.getTime());
        String strDateHYear = mdformatYear.format(calendar.getTime());
        String strDateHMonth = mdformatMonth.format(calendar.getTime());
        String strDateHDay = mdformatDay.format(calendar.getTime());

        int strYear = Integer.parseInt(strDateHYear);
        int strMonth = Integer.parseInt(strDateHMonth);
        int strDay = Integer.parseInt(strDateHDay);
        date_geo.setText(strDate);
        String hijri_date = (UmmQura.fromGregorian(new GregorianCalendar(strYear, strMonth, strDay)).toString());
        date_hijri.setText(hijri_date);

        //Get NY time zone instance
        TimeZone defaultTz = TimeZone.getDefault();

        //Get NY calendar object with current date/time
        Calendar defaultCalc = Calendar.getInstance(defaultTz);

        //Get offset from UTC, accounting for DST
        int defaultTzOffsetMs = defaultCalc.get(Calendar.ZONE_OFFSET) + defaultCalc.get(Calendar.DST_OFFSET);
        double timezone = defaultTzOffsetMs / (1000 * 60 * 60);


        Log.d(TAG + "TimeZone", Double.toString(timezone));
        // Test Prayer times here
        PrayTimes prayers = new PrayTimes();

        prayers.setTimeFormat(Integer.parseInt(sharedPreferences.getString(getString(R.string.pref_time_key),
                getString(R.string.pref_time_format_24_value))));
        prayers.setCalcMethod(Integer.parseInt(sharedPreferences.getString(getString(R.string.pref_method_key),
                getString(R.string.pref_method_mwl_value) )));
        prayers.setAsrJuristic(Integer.parseInt(sharedPreferences.getString(getString(R.string.pref_juristic_key),
                getString(R.string.pref_juristic_shafii_value) )));
        Log.d(TAG + "Preferences", sharedPreferences.getString(getString(R.string.pref_juristic_key),
                getString(R.string.pref_juristic_shafii_value) ));

        prayers.setAdjustHighLats(prayers.AngleBased);
        int[] offsets = {0, 0, 0, 0, 0, 0, 0}; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
        prayers.tune(offsets);

        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        System.out.println(cal.getTime());
        ArrayList<String> prayerTimes = prayers.getPrayerTimes(cal,
                lat, lon, timezone);

        Log.d(TAG+"Coordinate, Refresh", Double.toString(lat));

        for(int j=0;j<prayerTimes.size(); j++) {
            Log.d(TAG, prayerTimes.get(j));
        }
        Log.d(TAG+"Timezone prayertimes", Double.toString(prayers.getTimeZone()));

        TimesPreferences.setPrayerTimes(this, prayerTimes);
       // ArrayList<String> prayerTimess = null;

        ArrayList<String> prayerTimess = new ArrayList<>();
        prayerTimess.clear();
        int size = sharedPreferences.getInt("Status_size", 0);

        for(int i=0;i<size;i++)
        {
            prayerTimess.add(sharedPreferences.getString("Status_" + i, null));
            Log.d(TAG+"Shared pref prayertimes", prayerTimess.get(i));
        }


        fajr.setText(prayerTimes.get(FAJR));
        sunrise.setText(prayerTimes.get(SUNRISE));
        zuhr.setText(prayerTimes.get(ZUHR));
        asr.setText(prayerTimes.get(ASR));
        magreeb.setText(prayerTimes.get(MAGREEB));
        isha.setText(prayerTimes.get(ISHA));
        Log.d(TAG, Double.toString(Qibla.findDirection(lat, lon)));
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.times, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        if (id == R.id.action_map) {
            openPreferredLocationInMap();
            return true;
        }
        if (id == R.id.find_direction) {
            startActivity(new Intent(this, QiblaActivity.class));
            return true;
        }
        if (id == R.id.dua_activity) {
            startActivity(new Intent(this, DuaActivity.class));
            return true;
        }

        if (id == R.id.about_activity) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        }
        if (id == R.id.names_activity) {
            startActivity(new Intent(this, NamesActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openPreferredLocationInMap() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, getString(R.string.need_location_permission_message), Toast.LENGTH_LONG).show();
            return;
        }
        try {
            // Start a new Activity for the Place Picker API, this will trigger {@code #onActivityResult}
            // when a place is selected or with the user cancels.
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            Intent i = builder.build(this);
            startActivityForResult(i, PLACE_PICKER_REQUEST);

        } catch (GooglePlayServicesRepairableException e) {
            Log.e(TAG, String.format("GooglePlayServices Not Available [%s]", e.getMessage()));
        } catch (GooglePlayServicesNotAvailableException e) {
            Log.e(TAG, String.format("GooglePlayServices Not Available [%s]", e.getMessage()));
        } catch (Exception e) {
            Log.e(TAG, String.format("PlacePicker Exception: %s", e.getMessage()));
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK) {
            Place place = PlacePicker.getPlace(this, data);


            if (place == null) {
                Log.i(TAG, "No place selected");
                return;
            }

            // Extract the place information from the API
            String placeName = place.getName().toString();
            String placeAddress = place.getAddress().toString();

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(getString(R.string.pref_location_key),placeAddress);
            editor.commit();

            TimesPreferences.setLocationDetails(this, place.getLatLng().latitude, place.getLatLng().longitude);

            latitude = place.getLatLng().latitude;
            longitude = place.getLatLng().longitude;

            refreshUI(latitude, longitude);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);

    }



    public void onRingerPermissionsClicked(View view) {
        Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
        startActivity(intent);
    }

    public void onLocationPermissionClicked(View view) {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSIONS_REQUEST_FINE_LOCATION);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_location_key))) {

                   Log.d(TAG+"LocationLabel", sharedPreferences.getString(getString(R.string.pref_location_key), getString(R.string.pref_location_default)));

            double coord[] =  TimesPreferences.getLocationCoordinates(this);

            latitude = coord[0];
            longitude = coord[1];
            refreshUI(latitude, longitude);
        }else if  (key.equals(getString(R.string.pref_method_key))) {
            refreshUI(latitude, longitude);
        }else if(key.equals(getString(R.string.pref_time_key))){
            refreshUI(latitude, longitude);
        }else if(key.equals(getString(R.string.pref_juristic_key))){
            refreshUI(latitude, longitude);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister VisualizerActivity as an OnPreferenceChangedListener to avoid any memory leaks.
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    /**
     * App Permissions for Audio
     **/
    private void setupPermissions() {
        // If we don't have the record audio permission...
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // And if we're on SDK M or later...
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // Ask again, nicely, for the permissions.
                String[] permissionsWeNeed = new String[]{ Manifest.permission.ACCESS_FINE_LOCATION };
                requestPermissions(permissionsWeNeed, PERMISSIONS_REQUEST_FINE_LOCATION);
            }
        } else {
            // Otherwise, permissions were granted and we are ready to go!
            //mAudioInputReader = new AudioInputReader(mVisualizerView, this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // The permission was granted! Start up the visualizer!
                    //mAudioInputReader = new AudioInputReader(mVisualizerView, this);

                } else {
                    Toast.makeText(this, "Permission for audio not granted. Visualizer can't run.", Toast.LENGTH_LONG).show();
                    finish();
                    // The permission was denied, so we can show a message why we can't run the app
                    // and then close the app.
                }
            }
            // Other permissions could go down here

        }
    }

    public void countDownStart() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this, 1000);
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

                    futureDate = dateFormat.parse("23:00:01");
                    currentDate = new Date();
                    if (!currentDate.after(futureDate)) {
                        diff = futureDate.getTime()
                                - currentDate.getTime();
                        days = diff / (24 * 60 * 60 * 1000);
                        diff -= days * (24 * 60 * 60 * 1000);
                        hours = diff / (60 * 60 * 1000);
                        diff -= hours * (60 * 60 * 1000);
                        minutes = diff / (60 * 1000);
                        diff -= minutes * (60 * 1000);
                        seconds = diff / 1000;


                       /* txtTimerDay.setText("" + String.format("%02d", days));
                        txtTimerHour.setText("" + String.format("%02d", hours));
                        txtTimerMinute.setText(""+ String.format("%02d", minutes));
                        txtTimerSecond.setText(""+ String.format("%02d", seconds));*/
                        next_prayer.setText("" +String.format("%02d", hours) +String.format("%02d", minutes) + String.format("%02d", seconds) );

                      /*  NotificationCompat.Builder builder =
                                new NotificationCompat.Builder(c)
                                        .setSmallIcon(R.drawable.ic_launcher)
                                        .setContentTitle("Notifications Example")
                                        .setContentText(Long.toString(days) + " - "+ Long.toString(hours) +" - "+ Long.toString(minutes) + " - " +
                                         Long.toString(seconds));
                        Intent notificationIntent = new Intent(c, MainActivity.class);
                        PendingIntent contentIntent = PendingIntent.getActivity(c, 0, notificationIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT);
                        builder.setContentIntent(contentIntent);
                        // Add as notification
                        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        manager.notify(0, builder.build());
*/

                    } else {
                       /* tvEvent.setVisibility(View.VISIBLE);
                        tvEvent.setText("The event started!");
                        textViewGone();*/
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 1 * 1000);
    }


    /**
     * method to convert millisecond to time format
     *
     * @param milliSeconds
     * @return HH:mm:ss time formatted string
     */
    private String hmsTimeFormatter(long milliSeconds) {

        String hms = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(milliSeconds),
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));

        return hms;


    }

}
