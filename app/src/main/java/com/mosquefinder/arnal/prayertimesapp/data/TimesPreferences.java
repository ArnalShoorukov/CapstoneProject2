package com.mosquefinder.arnal.prayertimesapp.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.mosquefinder.arnal.prayertimesapp.R;

import java.util.ArrayList;

/**
 * Created by arnal on 7/16/17.
 */

public final class TimesPreferences {

    public static final String PREF_COORD_LAT = "coord_lat";
    public static final String PREF_COORD_LONG = "coord_long";
    public static final String PRAYER_TIMES = "prayer_times";


    public static void setPrayerTimes(Context context, ArrayList<String> prayerTimes) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        /* sKey is an array */
        editor.putInt("Status_size", prayerTimes.size());

        for(int i=0;i<prayerTimes.size();i++)
        {
            editor.remove("Status_" + i);
            editor.putString("Status_" + i, prayerTimes.get(i));
        }
        editor.apply();
    }
    public static void setLocationDetails(Context context, double lat, double lon) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();

        editor.putLong(PREF_COORD_LAT, Double.doubleToRawLongBits(lat));
        editor.putLong(PREF_COORD_LONG, Double.doubleToRawLongBits(lon));
        editor.apply();
    }


    public static void resetLocationCoordinates(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();

        editor.remove(PREF_COORD_LAT);
        editor.remove(PREF_COORD_LONG);
        editor.apply();
    }
    public static String getPreferredWeatherLocation(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        String keyForLocation = context.getString(R.string.pref_location_key);
        String defaultLocation = context.getString(R.string.pref_location_default);

        return sp.getString(keyForLocation, defaultLocation);
    }

    public static String getPreferredLocation(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        String keyForLocation = context.getString(R.string.pref_location_key);
        String defaultLocation = context.getString(R.string.pref_location_default);

        return sp.getString(keyForLocation, defaultLocation);
    }

    public static int getCalculationMethod(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        String keyForMethod = context.getString(R.string.pref_method_key);
        String defaultMethod = context.getString(R.string.pref_method_mwl_value);

        return sp.getInt(keyForMethod, Integer.parseInt(defaultMethod));
    }
    public static boolean isMetric(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        String keyForUnits = context.getString(R.string.pref_units_key);
        String defaultUnits = context.getString(R.string.pref_units_metric);
        String preferredUnits = sp.getString(keyForUnits, defaultUnits);
        String metric = context.getString(R.string.pref_units_metric);

        boolean userPrefersMetric = false;
        if (metric.equals(preferredUnits)) {
            userPrefersMetric = true;
        }

        return userPrefersMetric;
    }

    public static double[] getLocationCoordinates(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        double[] preferredCoordinates = new double[2];

        preferredCoordinates[0] = Double
                .longBitsToDouble(sp.getLong(PREF_COORD_LAT, Double.doubleToRawLongBits(42.870022)));
        preferredCoordinates[1] = Double
                .longBitsToDouble(sp.getLong(PREF_COORD_LONG, Double.doubleToRawLongBits(74.587883)));

        return preferredCoordinates;
    }


    public static boolean isLocationLatLonAvailable(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        boolean spContainLatitude = sp.contains(PREF_COORD_LAT);
        boolean spContainLongitude = sp.contains(PREF_COORD_LONG);

        boolean spContainBothLatitudeAndLongitude = false;
        if (spContainLatitude && spContainLongitude) {
            spContainBothLatitudeAndLongitude = true;
        }

        return spContainBothLatitudeAndLongitude;
    }


}
