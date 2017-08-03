package com.mosquefinder.arnal.prayertimesapp.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by arnal on 7/29/17.
 */

public class DuaContract {

    public static final String AUTHORITY = "com.mosquefinder.arnal.prayertimes";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY );

    public static final String PATH_DUA = "dua";

    public static final class DuaEntry implements BaseColumns {
        // public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MOVIES);

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_DUA).build();

        /* The MIME type of the {@link #CONTENT_URI} for a list of movies */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_DUA;
        /* The MIME type of the {@link #CONTENT_URI} for a single movie */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_DUA;

        public static final String TABLE_NAME = "dua" ;

        public static final String COLUMN_DUA_ID = "duaId";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_ENGLISH = "english";
        public static final String COLUMN_REFERENCE = "reference";
        public static final String COLUMN_BENEFIT = "benefit";
        public static final String COLUMN_AUDIO_RESOURCE_ID = "audio";
        public static final String COLUMN_IMAGE_RESOURCE_ID = "image";

        public static Uri buildMovieUriWithId(String _ID){
            return CONTENT_URI.buildUpon().appendPath(_ID)
                    .build();
        }


    }
}
