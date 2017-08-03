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

// Find the TextView in the list_item.xml layout with the ID version_name
        TextView title = (TextView) listItemView.findViewById(R.id.title_dua);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        title.setText(currentWord.getmTitle());

      /*  // Find the TextView in the list_item.xml layout with the ID version_name
        TextView translation = (TextView) listItemView.findViewById(R.id.translation_text);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        translation.setText(currentWord.getEnglish());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView referenceTextView = (TextView) listItemView.findViewById(R.id.reference_text);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        referenceTextView.setText(currentWord.getReference());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView benefitTextView = (TextView) listItemView.findViewById(R.id.benefit_text);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        benefitTextView.setText(currentWord.getBenefit());

        // Find the ImageView in the list_item.xml layout with the ID list_item_icon
        ImageView duaImage = (ImageView) listItemView.findViewById(R.id.arabic);

        duaImage.setImageResource(currentWord.getImageResourceID());
*/



        // Set the theme color for the list item
        View textContainer = listItemView.findViewById(R.id.text_container);
        // Find the color that the resource ID maps to
      //  int color = ContextCompat.getColor(getContext(), mColorResourceID);
        // Set the background color of the text container View
     //   textContainer.setBackgroundColor(color);




        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }
}