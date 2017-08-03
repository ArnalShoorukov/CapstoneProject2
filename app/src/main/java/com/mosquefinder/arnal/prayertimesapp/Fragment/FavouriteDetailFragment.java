package com.mosquefinder.arnal.prayertimesapp.Fragment;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mosquefinder.arnal.prayertimesapp.DetailDuaActivity;
import com.mosquefinder.arnal.prayertimesapp.R;
import com.mosquefinder.arnal.prayertimesapp.database.DuaContract.DuaEntry;

import static com.mosquefinder.arnal.prayertimesapp.DetailDuaActivity.setFavouriteImageText;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>  {
    ContentResolver mContentResolver;
    private static final int FAVOURITE_LOADER = 13;

    ImageView mFavouriteIcon;
    TextView mFavouriteTextView;
    LinearLayout favouriteView;

    public static final String DUA_URI = "URI";

    private Uri mUri;

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


    // @BindView(R.id.favorite_icon)
    TextView title;
    ImageView image;
    TextView english;
    TextView reference;
    TextView benefit;


    public FavouriteDetailFragment() {
        // Required empty public constructor
    }


    private MediaPlayer mediaplayer;
    private AudioManager mAudioManager;
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.
                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mediaplayer.pause();
                mediaplayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mediaplayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(FAVOURITE_LOADER, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle args = getArguments();
        if (args != null) {
            mUri = args.getParcelable(FavouriteDetailFragment.DUA_URI);
        }

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_favourite_detail, container, false);
        // Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        mContentResolver = getActivity().getContentResolver();

        title = (TextView) rootView.findViewById(R.id.title_dua);
        image = (ImageView) rootView.findViewById(R.id.arabic);
        english = (TextView) rootView.findViewById(R.id.translation_text);
        reference = (TextView) rootView.findViewById(R.id.reference_text);
        benefit = (TextView) rootView.findViewById(R.id.benefit_text);
        mFavouriteIcon = (ImageView) rootView.findViewById(R.id.favorite_icon);
        mFavouriteTextView = (TextView) rootView.findViewById(R.id.favourite_text_view);
        favouriteView = (LinearLayout) rootView.findViewById(R.id.favourite_view);



        getLoaderManager().initLoader(FAVOURITE_LOADER, null, this);

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (null != mUri) {
            return new CursorLoader(
                    getActivity(),
                    mUri,
                    FAVOURITE_COLUMNS,
                    null,
                    null,
                    null
            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            final String duaId = data.getString(COL_DUA_ID);
            final String duaTitle = data.getString(COL_DUA_TITLE);
            title.setText(duaTitle);

            final  String englishText = data.getString(COL_DUA_ENGLISH);
            english.setText(englishText);

            final int imageResource = data.getInt(COL_DUA_IMAGE);
            image.setImageResource(imageResource);

            final  String referenceText = data.getString(COL_DUA_REFEERENCE);
            reference.setText(referenceText);

            final  String benefitText = data.getString(COL_DUA_BENEFIT);
            benefit.setText(benefitText);

            final int audioResource = data.getInt(COL_DUA_AUDIO);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Request audio focus so in order to play the audio file. The app needs to play a
                    // short audio file, so we will request audio focus with a short amount of time
                    // with AUDIOFOCUS_GAIN_TRANSIENT.
                    releaseMediaPlayer();
                    int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                            AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                    if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                        // We have audio focus now.
                        // Create and setup the {@link MediaPlayer} for the audio resource associated
                        // with the current word
                        mediaplayer = MediaPlayer.create(getActivity().getApplicationContext(), audioResource);

                        // Start the audio file
                        mediaplayer.start();

                        // Setup a listener on the media player, so that we can stop and release the
                        // media player once the sound has finished playing.
                        mediaplayer.setOnCompletionListener(mCompletionListener);
                    }

                }
            });


            mFavouriteIcon.setImageResource(R.drawable.ic_fav_light);
            mFavouriteTextView.setText(R.string.favourited);
            favouriteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String favouriteText = mFavouriteTextView.getText().toString();

                    //Check whether the movie is favourited
                    if (favouriteText.equals(getString(R.string.favourited))) {
                        // Unmark as favourite and delete it from the database

                        mContentResolver.delete(
                                mUri,
                                null,
                                null
                        );

                        // Update favourite icon and text
                        setFavouriteImageText(false, mFavouriteIcon, mFavouriteTextView);
                    } else {
                        // Add as favourite and insert it into the database
                        ContentValues values = new ContentValues();
                        values.put(DuaEntry.COLUMN_DUA_ID, duaId);
                        values.put(DuaEntry.COLUMN_TITLE, duaTitle);
                        values.put(DuaEntry.COLUMN_ENGLISH, englishText);
                        values.put(DuaEntry.COLUMN_REFERENCE, referenceText);
                        values.put(DuaEntry.COLUMN_BENEFIT, benefitText);
                        values.put(DuaEntry.COLUMN_IMAGE_RESOURCE_ID, imageResource);
                        values.put(DuaEntry.COLUMN_AUDIO_RESOURCE_ID, imageResource);

                        Uri newUri = mContentResolver.insert(DuaEntry.CONTENT_URI, values);

                        // Update favourite icon and text
                        setFavouriteImageText(true, mFavouriteIcon, mFavouriteTextView);
                    }
                }
            });
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaplayer!= null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaplayer.release();
            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaplayer = null;
            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
