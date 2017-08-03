package com.mosquefinder.arnal.prayertimesapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.mosquefinder.arnal.prayertimesapp.data.TimesPreferences.getPreferredWeatherLocation;

public class SimpleWidgetProvider extends AppWidgetProvider {
    private ArrayList<String> prayerTimess = new ArrayList<>();

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        //Create an Intent to launch MainActivity when clicked
        Intent titleIntent = new Intent(context, MainActivity.class);
        PendingIntent startIntent = PendingIntent.getActivity(context, 0, titleIntent, 0);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("EEEE, dd, MMMM, yyyy");


        String strDate = mdformat.format(calendar.getTime());


        prayerTimess.clear();
        int size = sharedPreferences.getInt("Status_size", 0);

        for (int i = 0; i < size; i++) {
            prayerTimess.add(sharedPreferences.getString("Status_" + i, null));

        }

        for (int i = 0; i < count; i++) {
        int widgetId = appWidgetIds[i];

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.simple_widget);
        remoteViews.setTextViewText(R.id.textView_fajr, prayerTimess.get(0));
        remoteViews.setTextViewText(R.id.textView_sunrise, prayerTimess.get(1));
        remoteViews.setTextViewText(R.id.textView_zuhr, prayerTimess.get(2));
        remoteViews.setTextViewText(R.id.textView_asr, prayerTimess.get(3));
        remoteViews.setTextViewText(R.id.textView_magreeb, prayerTimess.get(4));
        remoteViews.setTextViewText(R.id.textView_isha, prayerTimess.get(6));

       // sharedPreferences.getString(getString(R.string.pref_location_key), getString(R.string.pref_location_default));
           String location =  getPreferredWeatherLocation(context);
        remoteViews.setTextViewText(R.id.textView_location, location);
        remoteViews.setTextViewText(R.id.date_widget, strDate);

        Intent intent = new Intent(context, SimpleWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Widget allows click handlers to only launch pending intent
        remoteViews.setOnClickPendingIntent(R.id.widget, pendingIntent);
        remoteViews.setOnClickPendingIntent(R.id.title_widget, startIntent);

             appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }

    }
}
