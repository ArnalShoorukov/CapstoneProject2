package com.mosquefinder.arnal.prayertimesapp.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.mosquefinder.arnal.prayertimesapp.MainActivity;
import com.mosquefinder.arnal.prayertimesapp.R;

import java.util.ArrayList;

/**
 * Created by arnal on 7/31/17.
 */


public class MyWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private   ArrayList<String> prayerTimess = new ArrayList<>();

    public MyWidgetRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        getData();
    }

    public void getData() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);


      //  ArrayList<String> prayerTimess = new ArrayList<>();
        prayerTimess.clear();
        int size = sharedPreferences.getInt("Status_size", 0);

        for(int i=0;i<size;i++)
        {
            prayerTimess.add(sharedPreferences.getString("Status_" + i, null));

        }




        /*SOService mService = ApiUtils.getSOService();

        Call<List<SOAnswersResponse>> call = mService.getAnswers();
        try {
            List<SOAnswersResponse> responses = call.execute().body();

            nameList = responses;
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
      return prayerTimess.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

  Log.v(mContext.getClass().getSimpleName(), "position = " + position);

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.prayer_times_widget);
        rv.setTextViewText(R.id.fajr_widget, prayerTimess.get(0));
       /* rv.removeAllViews(R.id.ingerdient_list);

        for (int i = 0; i < prayerTimess.size(); i++) {
            RemoteViews ing = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_widget_item);
            ing.setTextViewText(R.id.ingredient, nameList.get(position).getIngredients().get(i).getIngredient());
            ing.setTextViewText(R.id.measure, nameList.get(position).getIngredients().get(i).getMeasure());
            ing.setTextViewText(R.id.quantity, nameList.get(position).getIngredients().get(i).getQuantity() + "");

            rv.addView(R.id.ingerdient_list, ing);
        }*/

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}

