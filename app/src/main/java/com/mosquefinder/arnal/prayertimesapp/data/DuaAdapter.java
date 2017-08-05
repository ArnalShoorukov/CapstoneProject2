package com.mosquefinder.arnal.prayertimesapp.data;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mosquefinder.arnal.prayertimesapp.R;

import java.util.ArrayList;

/**
 * Created by arnal on 7/23/17.
 */

public class DuaAdapter extends ArrayAdapter<Dua> {
    private static final String LOG_TAG = DuaAdapter.class.getSimpleName();
    private int mColorResourceID;



    public DuaAdapter(Activity context, ArrayList<Dua> words) {

        super(context, 0, words);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.dua, parent, false);
        }
        // Get the {@link Word} object located at this position in the list
        Dua currentWord = getItem(position);

        TextView title = (TextView) listItemView.findViewById(R.id.title_dua);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        title.setText(currentWord.getmTitle());





        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }
}