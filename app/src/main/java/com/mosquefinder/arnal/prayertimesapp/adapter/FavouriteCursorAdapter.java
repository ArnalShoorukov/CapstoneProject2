package com.mosquefinder.arnal.prayertimesapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.mosquefinder.arnal.prayertimesapp.R;
import com.mosquefinder.arnal.prayertimesapp.database.DuaContract.DuaEntry;

/**
 * Created by arnal on 7/29/17.
 */

public class FavouriteCursorAdapter extends CursorAdapter {

    private Listener listener;

    public FavouriteCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    public interface Listener {
        void onClick(int position);
    }

    public void setListener(FavouriteCursorAdapter.Listener listener) {
        this.listener = listener;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.dua_list_favor, parent, false);
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {



        TextView textViewTitle = (TextView)view.findViewById(R.id.title_cardview_favor);

        final int movieIdColumnIndex = cursor.getColumnIndex(DuaEntry.COLUMN_DUA_ID);
        int titleColumnIndex = cursor.getColumnIndex(DuaEntry.COLUMN_TITLE);

        String duaID = cursor.getString(movieIdColumnIndex);
        String title = cursor.getString(titleColumnIndex);
        textViewTitle.setText(title);



        textViewTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(cursor.getPosition());

            }
        });
    }
}