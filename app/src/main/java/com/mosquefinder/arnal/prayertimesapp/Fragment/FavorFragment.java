package com.mosquefinder.arnal.prayertimesapp.Fragment;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.mosquefinder.arnal.prayertimesapp.FavorDetailsActivity;
import com.mosquefinder.arnal.prayertimesapp.R;
import com.mosquefinder.arnal.prayertimesapp.adapter.FavouriteCursorAdapter;
import com.mosquefinder.arnal.prayertimesapp.data.Dua;
import com.mosquefinder.arnal.prayertimesapp.database.DuaContract;
import com.mosquefinder.arnal.prayertimesapp.database.DuaContract.DuaEntry;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavorFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String LOG_TAG = FavorFragment.class.getName();

    // Identifier for the Loader
    private static final int FAVOURITE_LOADER = 3;
    FavouriteCursorAdapter mCursorAdapter;
    private static final String[] FAVOURITE_COLUMNS = {
            DuaEntry._ID,
            DuaEntry.COLUMN_DUA_ID,
            DuaEntry.COLUMN_TITLE,
            DuaEntry.COLUMN_ENGLISH,
            DuaEntry.COLUMN_REFERENCE,
            DuaEntry.COLUMN_BENEFIT,
            DuaEntry.COLUMN_AUDIO_RESOURCE_ID,
            DuaEntry.COLUMN_IMAGE_RESOURCE_ID,
    };

    // These indices are tied to FAVOURITE_COLUMNS.
    public static final int COL_ID = 0;
    public static final int COL_DUA_ID = 1;
    public static final int COL_DUA_TITLE = 2;
    public static final int COL_DUA_ENGLISH = 3;
    public static final int COL_DUA_REFEERENCE = 4;
    public static final int COL_DUA_BENEFIT = 5;
    public static final int COL_DUA_AUDIO = 6;
    public static final int COL_DUA_IMAGE = 7;

    GridView favouriteGridView;
    TextView mEmptyStateTextView;

    private int mPosition = GridView.INVALID_POSITION;
    private static final String SELECTED_KEY = "selected_position";


    /**
     * A callback interface that allows the fragment to pass data on to the activity
     */
    public interface Callback {
        void onFavouriteSelected(Uri contentUri);
    }


    public FavorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_favor, container, false);



        favouriteGridView = (GridView)rootView.findViewById(R.id.movie_activity_grid_view);
        mEmptyStateTextView = (TextView) rootView.findViewById(R.id.empty_list_view);
        Cursor cursor = null;

        mCursorAdapter = new FavouriteCursorAdapter(getContext(), cursor);

        favouriteGridView.setAdapter(mCursorAdapter);
        favouriteGridView.setEmptyView(mEmptyStateTextView);

        mCursorAdapter.setListener(new FavouriteCursorAdapter.Listener() {
            @Override
            public void onClick(int position) {

                Cursor cursor = (Cursor) mCursorAdapter.getItem(position);
                if (cursor != null) {
                    String columnId = cursor.
                            getString(cursor.getColumnIndex(DuaEntry._ID));
                    Uri contentUri = DuaEntry.buildMovieUriWithId(columnId);
                    Intent intent = new Intent(getContext(), FavorDetailsActivity.class);
                    intent.setData(contentUri);
                    startActivity(intent);
                }
                mPosition = position;
            }
        });

        // If there's instance state, mine it for useful info
        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        // re-queries for all tasks
        getActivity().getSupportLoaderManager().restartLoader(FAVOURITE_LOADER, null, this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                DuaEntry._ID,
                DuaEntry.COLUMN_DUA_ID,
                DuaEntry.COLUMN_TITLE};

        return new CursorLoader(getContext(),
                DuaEntry.CONTENT_URI,
                projection,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || !cursor.moveToFirst()) {
            mEmptyStateTextView.setText(R.string.empty_favourite_gridview_message);
        }

        Log.i(LOG_TAG, "Test Cursor before swap " + cursor);
        mCursorAdapter.swapCursor(cursor);
        Log.i(LOG_TAG, "Test Cursor Adapter" + mCursorAdapter);
        Log.i(LOG_TAG, "Test Cursor Adapter Swap" + mCursorAdapter.swapCursor(cursor));

        if (mPosition != ListView.INVALID_POSITION) {
            // If we don't need to restart the loader, and there's a desired position to restore
            // to, do so now.
            favouriteGridView.smoothScrollToPosition(mPosition);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }

}