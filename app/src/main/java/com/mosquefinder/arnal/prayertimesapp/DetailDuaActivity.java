package com.mosquefinder.arnal.prayertimesapp;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mosquefinder.arnal.prayertimesapp.data.Dua;
import com.mosquefinder.arnal.prayertimesapp.database.DuaContract;
import com.mosquefinder.arnal.prayertimesapp.database.DuaContract.DuaEntry;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailDuaActivity extends AppCompatActivity {


    @BindView(R.id.title_dua)
    TextView textViewTitle;

    @BindView(R.id.translation_text)
    TextView textViewEnglish;

    @BindView(R.id.benefit_text)
    TextView textViewBenefit;

    @BindView(R.id.reference_text)
    TextView textViewReference;

    @BindView(R.id.arabic)
    ImageView duaImageView;

    @BindView(R.id.favourite_view)
    LinearLayout favouriteView;

    @BindView(R.id.favorite_icon)
    ImageView mFavouriteIcon;

    @BindView(R.id.favourite_text_view)
    TextView mFavouriteTextView;


    private Dua dua;
    ContentResolver mContentResolver;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dua);
        ButterKnife.bind(this);
        mContentResolver = this.getContentResolver();
        // Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            dua = bundle.getParcelable("object");


            textViewTitle.setText(dua.getmTitle());
            textViewEnglish.setText(dua.getEnglish());
            textViewBenefit.setText(dua.getBenefit());
            textViewReference.setText(dua.getReference());

            duaImageView.setImageResource(dua.getImageResourceID());
            duaImageView.setOnClickListener(new View.OnClickListener() {
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
                        mediaplayer = MediaPlayer.create(DetailDuaActivity.this, dua.getAudioResourceId());

                        // Start the audio file
                        mediaplayer.start();

                        // Setup a listener on the media player, so that we can stop and release the
                        // media player once the sound has finished playing.
                        mediaplayer.setOnCompletionListener(mCompletionListener);
                    }

                }
            });

            String id = dua.getId();

            // Set favourite image and text after figuring out whether the movie is favourited
            setFavouriteImageText(isFavourited(id), mFavouriteIcon, mFavouriteTextView);


            favouriteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String idMovie = dua.getId();

                    String favouriteText = mFavouriteTextView.getText().toString();

                    Log.i("MainActivityFragment", favouriteText);
                    setFavouriteImageText(true, mFavouriteIcon, mFavouriteTextView);

                    //Check whether the movie is favourited
                    if (favouriteText.equals(getString(R.string.favourited))) {
                        Toast.makeText(DetailDuaActivity.this, "Removed from Favorite List", Toast.LENGTH_SHORT).show();

                        // Unmark as favourite and delete it from the database
                        mContentResolver.delete(
                                DuaEntry.CONTENT_URI,
                                DuaEntry.COLUMN_DUA_ID + "=?",
                                new String[]{idMovie}
                        );

                        // Update favourite icon and text
                        setFavouriteImageText(false, mFavouriteIcon, mFavouriteTextView);
                    }else {
                        Toast.makeText(DetailDuaActivity.this, "Added to Favorite List", Toast.LENGTH_SHORT).show();
                        addFavourite();
                        // Update favourite icon and text
                        setFavouriteImageText(true, mFavouriteIcon, mFavouriteTextView);
                    }
                }
            });
        }
    }
    private void addFavourite() {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DuaEntry.COLUMN_DUA_ID, dua.getId());
        contentValues.put(DuaEntry.COLUMN_TITLE, dua.getmTitle());
        contentValues.put(DuaEntry.COLUMN_ENGLISH, dua.getEnglish());
        contentValues.put(DuaEntry.COLUMN_REFERENCE, dua.getReference());
        contentValues.put(DuaEntry.COLUMN_BENEFIT, dua.getBenefit());
        contentValues.put(DuaEntry.COLUMN_AUDIO_RESOURCE_ID, dua.getAudioResourceId());
        contentValues.put(DuaEntry.COLUMN_IMAGE_RESOURCE_ID, dua.getImageResourceID());


        Uri uri = this.getContentResolver().insert(DuaContract.DuaEntry.CONTENT_URI, contentValues);
    }

    public static void setFavouriteImageText(boolean favourite, ImageView imageView, TextView textView) {
        if (favourite) {
            imageView.setImageResource(R.drawable.ic_fav_dark);
            textView.setText(R.string.favourited);
        } else {
            imageView.setImageResource(R.drawable.ic_fav_light);
            textView.setText(R.string.mark_as_favourite);
        }
    }

    public boolean isFavourited(String movieId) {
        String[] projection = {DuaEntry.COLUMN_DUA_ID};
        String selection = DuaEntry.COLUMN_DUA_ID + "=?";
        String[] selectionArgs = new String[]{movieId};

        Cursor cursor = mContentResolver.query(
                DuaEntry.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null
        );
        return cursor != null & cursor.moveToFirst();
    }
    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
    /**
     * Clean up the media player by releasing its resources.
     */
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
}
