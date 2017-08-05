package com.mosquefinder.arnal.prayertimesapp;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.mosquefinder.arnal.prayertimesapp.data.TimesPreferences;

import net.alqs.iclib.hijri.UmmQura;
import net.alqs.iclib.qibla.Qibla;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private final String TAG = MainActivity.class.getSimpleName();
    private static final int PERMISSIONS_REQUEST_FINE_LOCATION = 111;
    private static final int PLACE_PICKER_REQUEST = 1;
    double latitude, longitude ;
    TextView city,method, fajr, sunrise, zuhr, asr, magreeb, isha, date_geo, date_hijri, next_prayer;
    private static int FAJR = 0;
    private static int SUNRISE = 1;
    private static int ZUHR = 2;
    private static int ASR = 3;
    private static int MAGREEB = 4;
    private static int ISHA = 6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prayer_main_activity);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Stetho.initializeWithDefaults(this);
        city = (TextView) findViewById(R.id.city);
        fajr = (TextView) findViewById(R.id.fajr_time);
        sunrise = (TextView) findViewById(R.id.sunrise_time);
        zuhr = (TextView) findViewById(R.id.zuhr_time);
        asr = (TextView) findViewById(R.id.asr_time);
        magreeb = (TextView)findViewById(R.id.magreeb_time);
        isha = (TextView) findViewById(R.id.isha_time);
        method = (TextView) findViewById(R.id.calculation_method);

        date_geo = (TextView) findViewById(R.id.date_geo);
        date_hijri = (TextView) findViewById(R.id.date_hijri);

        setupPermissions();
        Log.d(TAG+"Coordinate, Oncreate", Double.toString(latitude));

        double coord[] =  TimesPreferences.getLocationCoordinates(this);

        latitude = coord[0];
        longitude = coord[1];
        MobileAds.initialize(this);
      //  LoadAds();
        refreshUI(latitude, longitude);




        // Register the listener
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }


  /*  private void LoadAds() {

        AdView mAdView = (AdView) findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder().build();

        mAdView.loadAd(adRequest);

       *//* // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);*//*
    }*/
    private void refreshUI(double lat, double lon) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        city.setText(sharedPreferences.getString(getString(R.string.pref_location_key),
                getString(R.string.pref_location_default)));


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
            Log.d(TAG+"getCalcMethod", Integer.toString(prayers.getCalcMethod()));
        //method.setText(sharedPreferences.getString(getString(R.string.pref_method_key), getString(R.string.pref_method_label)));
        Log.d(TAG+"Methodlabel", sharedPreferences.getString(getString(R.string.pref_method_key), getString(R.string.pref_method_mwl_value)));
        String method_number = sharedPreferences.getString(getString(R.string.pref_method_key), getString(R.string.pref_method_mwl_value));

        String method_label;
        switch (method_number){
            case "0":method_label = getString(R.string.pref_method_label_jafari);
                break;
            case "1":method_label = getString(R.string.pref_method_label_karachi);
                break;
            case "2":method_label = getString(R.string.pref_method_label_isna);
                break;
            case "3":method_label = getString(R.string.pref_method_label_mwl);
                break;
            case "4":method_label = getString(R.string.pref_method_label_qura);
                break;
            case "5":method_label = getString(R.string.pref_method_label_egypt);
                break;
            case "6":method_label = getString(R.string.pref_method_label_tehran);
                break;
            default:
                throw new IllegalArgumentException("Invalid day of the week: " + method_number);
        }
        method.setText(method_label);
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

    public void openPreferredLocationInMap() {

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
                    Toast.makeText(this, "Permission for location not granted.", Toast.LENGTH_LONG).show();
                    finish();
                    // The permission was denied, so we can show a message why we can't run the app
                    // and then close the app.
                }
            }
            // Other permissions could go down here

        }
    }
}
